package dw.editora.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import dw.editora.entity.OrdemServico;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    Optional<OrdemServico> findByNumero(String numero);
    List<OrdemServico> findByEquipamento_Cliente_Id(Long clienteId);
}