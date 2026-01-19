package shopping_app.util;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import shopping_app.common.PageResponse;

import java.util.List;
import java.util.function.Function;

@Component
public class PaginationUtil {

    public <T, R> PageResponse<R> toPageResponse(
            Page<T> pageData,
            Function<T, R> mapper
    ) {
        List<R> items = pageData.getContent()
                .stream()
                .map(mapper)
                .toList();

        return new PageResponse<>(
                items,
                pageData.getNumber() + 1,
                pageData.getSize(),
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );
    }
}
