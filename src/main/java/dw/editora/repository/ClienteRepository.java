package dw.editora.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import dw.editora.entity.Cliente;
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}
