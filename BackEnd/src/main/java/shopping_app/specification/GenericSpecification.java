package shopping_app.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * GenericSpecification
 * ---------------------
 * Dùng để build JPA Specification động từ một Filter DTO bất kỳ.
 *
 * Quy ước:
 * - String           → LIKE %value%
 * - Collection       → IN (...)
 * - FieldFrom        → >=
 * - FieldTo          → <=
 * - xxxName          → join nested field (xxx.name)
 * - xxxIds           → join nested field (xxx.id)
 *
 */
@Component
public class GenericSpecification<T> {

    /**
     * Build Specification từ filter object
     *
     * @param filter DTO filter (vd: ProductFilter)
     * @return Specification<T>
     */
    public Specification<T> build(Object filter) {
        return (root, query, cb) -> {

            // Predicate mặc định = true (1 = 1)
            Predicate predicate = cb.conjunction();

            // Không có filter → return luôn
            if (filter == null) return predicate;

            // Duyệt tất cả field trong filter
            for (Field field : filter.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                try {
                    Object value = field.get(filter);

                    // Bỏ qua nếu field null
                    if (value == null) continue;

                    String name = field.getName();

                    /* ================= RANGE FILTER ================= */

                    // check isHas + tên trường
                    if (name.startsWith("isHas") && value instanceof Boolean b) {
                        String targetField = Character.toLowerCase(name.charAt(5)) + name.substring(6); // isHasStock -> stock
                        Path<?> path;
                        try {
                            path = root.get(targetField);
                        } catch (IllegalArgumentException e) {
                            continue;
                        }

                        if (Collection.class.isAssignableFrom(path.getJavaType())) {
                            Expression<Integer> sizeExpr = cb.size(path.as(Collection.class));
                            predicate = cb.and(predicate, b ? cb.greaterThan(sizeExpr, 0) : cb.equal(sizeExpr, 0));
                        } else if (Number.class.isAssignableFrom(path.getJavaType())) {
                            Expression<? extends Number> numPath = path.as(Number.class);
                            if (b) {
                                predicate = cb.and(predicate, cb.gt(numPath, 0));
                            } else {
                                predicate = cb.and(predicate, cb.equal(numPath, 0));
                            }
                        }
                        continue;
                    }

                    // priceFrom, createdAtFrom ...
                    if (name.endsWith("From")) {
                        String realField = removeSuffix(name, "From");
                        Path<?> path = resolvePath(root, realField);

                        if (path != null) {
                            predicate = cb.and(
                                    predicate,
                                    cb.greaterThanOrEqualTo(
                                            path.as(Comparable.class),
                                            (Comparable) value
                                    )
                            );
                        }
                        continue;
                    }

                    // priceTo, createdAtTo ...
                    if (name.endsWith("To")) {
                        String realField = removeSuffix(name, "To");
                        Path<?> path = resolvePath(root, realField);

                        if (path != null) {
                            predicate = cb.and(
                                    predicate,
                                    cb.lessThanOrEqualTo(
                                            path.as(Comparable.class),
                                            (Comparable) value
                                    )
                            );
                        }
                        continue;
                    }

                    /* ================= NORMAL FILTER ================= */

                    Path<?> path = resolvePath(root, name);
                    if (path == null) continue;

                    // ===== String → LIKE (ignore case) =====
                    if (value instanceof String str) {
                        predicate = cb.and(
                                predicate,
                                cb.like(
                                        cb.lower(path.as(String.class)),
                                        "%" + str.toLowerCase() + "%"
                                )
                        );
                    }
                    // ===== Collection → IN =====
                    else if (value instanceof Collection<?> col && !col.isEmpty()) {
                        predicate = cb.and(
                                predicate,
                                path.in(col)
                        );
                    }
                    // ===== Other (Enum, Number, Boolean...) → EQUAL =====
                    else {
                        predicate = cb.and(
                                predicate,
                                cb.equal(path, value)
                        );
                    }

                } catch (Exception ignored) {
                    // ignore field không hợp lệ
                }
            }

            return predicate;
        };
    }

    /**
     * Resolve Path từ tên field
     *
     * Hỗ trợ:
     * - field thường: name
     * - nested: brand.name
     * - camelCase nested:
     *      brandName -> brand.name
     *      brandIds  -> brand.id
     */
    private Path<?> resolvePath(Root<T> root, String fieldName) {
        try {
            // Nested dạng "brand.name"
            if (fieldName.contains(".")) {
                String[] parts = fieldName.split("\\.");
                Path<?> path = root;
                for (String part : parts) {
                    path = path.get(part);
                }
                return path;
            }

            // brandName -> brand.name
            if (fieldName.endsWith("Name")) {
                String parent = fieldName.replace("Name", "");
                return root.get(parent).get("name");
            }

            // brandIds -> brand.id
            if (fieldName.endsWith("Ids")) {
                String parent = fieldName.replace("Ids", "");
                return root.get(parent).get("id");
            }

            // Field trực tiếp của entity
            return root.get(fieldName);

        } catch (IllegalArgumentException e) {
            // Entity không có field này
            return null;
        }
    }

    /**
     * Remove suffix khỏi tên field
     * vd: priceFrom -> price
     */
    private String removeSuffix(String field, String suffix) {
        return field.substring(0, field.length() - suffix.length());
    }
}