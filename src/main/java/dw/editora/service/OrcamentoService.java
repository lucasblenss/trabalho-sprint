package dw.editora.service;

import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import dw.editora.dto.AreaClienteDTO;
import dw.editora.entity.Cliente;
import dw.editora.entity.Equipamento;
import dw.editora.repository.EquipamentoRepository;

@Service @Transactional
public class OrcamentoService {
    private final EquipamentoRepository repository;
    private final ClienteService clientes;
    private final EquipamentoService equipamentos;

    public OrcamentoService(EquipamentoRepository repository, ClienteService clientes, EquipamentoService equipamentos) {
        this.repository = repository;
        this.clientes = clientes;
        this.equipamentos = equipamentos;
    }

    @Transactional(readOnly = true)
    public AreaClienteDTO area(Long clienteId) {
        Cliente c = clientes.buscar(clienteId);
        var aparelhos = repository.findByCliente_Id(clienteId).stream()
            .map(AreaClienteDTO.Aparelho::de).toList();
        return new AreaClienteDTO(c.getNome(), aparelhos);
    }

    // usado por vocês: define o valor e deixa o orçamento pendente
    public void definirValor(Long equipamentoId, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe um valor maior que zero.");
        Equipamento e = equipamentos.buscar(equipamentoId);
        e.setValorOrcamento(valor);
        e.setStatusOrcamento("PENDENTE");
        repository.save(e);
    }

    // usado pelo cliente
    public void responder(Long clienteId, Long equipamentoId, boolean aceitar) {
        Equipamento e = equipamentos.buscar(equipamentoId);
        if (!e.getCliente().getId().equals(clienteId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipamento não encontrado.");
        if (!"PENDENTE".equals(e.getStatusOrcamento()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há orçamento pendente para este aparelho.");
        e.setStatusOrcamento(aceitar ? "ACEITO" : "REJEITADO");
        repository.save(e);
    }
}