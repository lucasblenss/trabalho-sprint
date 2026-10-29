package dw.editora.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import dw.editora.entity.Equipamento;
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    List<Equipamento> findByCliente_Id(Long clienteId);
    List<Equipamento> findByModeloContainingIgnoreCase(String modelo);
    boolean existsByCliente_Id(Long clienteId);
}
