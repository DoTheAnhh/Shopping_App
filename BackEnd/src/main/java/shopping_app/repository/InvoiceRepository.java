package shopping_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import shopping_app.entity.Invoice;
import shopping_app.entity.User;
import shopping_app.entity.enums.InvoiceStatus;

import java.util.Optional;

@Repository
public interface InvoiceRepository  extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {

    Optional<Invoice> findByUserAndStatus(User user, InvoiceStatus status);
}
