package shopping_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import shopping_app.entity.InvoiceDetail;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long>, JpaSpecificationExecutor<InvoiceDetail> {

    List<InvoiceDetail> findByInvoiceId(Long invoiceId);

    Optional<InvoiceDetail> findByInvoiceIdAndProductVariantId(Long invoiceId, Long productVariantId);
}
