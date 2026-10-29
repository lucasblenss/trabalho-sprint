package dw.editora.service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import dw.editora.entity.Equipamento;
import dw.editora.entity.OrdemServico;
import dw.editora.entity.Servico;
import dw.editora.repository.OrdemServicoRepository;

@Service @Transactional
public class OrdemServicoService {
    private static final Set<String> STATUS = Set.of(
        "AGUARDANDO_ORCAMENTO", "ORCAMENTO_ENVIADO", "EM_REPARO", "PRONTO", "ENTREGUE");

    private final OrdemServicoRepository repository;
    private final EquipamentoService equipamentos;

    public OrdemServicoService(OrdemServicoRepository repository, EquipamentoService equipamentos) {
        this.repository = repository;
        this.equipamentos = equipamentos;
    }

    @Transactional(readOnly = true)
    public List<OrdemServico> listar() { return repository.findAll(); }

    @Transactional(readOnly = true)
    public OrdemServico buscar(Long id) {
        Validacao.id(id);
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Ordem de serviço não encontrada."));
    }

    @Transactional(readOnly = true)
    public OrdemServico consultar(String numero) {
        if (numero == null || numero.isBlank()) throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST, "Informe o número da ordem de serviço.");
        return repository.findByNumero(numero.trim().toUpperCase()).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Número não encontrado."));
    }

    public OrdemServico abrir(OrdemServico dados) {
        if (dados == null || dados.getEquipamento() == null || dados.getEquipamento().getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o ID do equipamento.");
        Equipamento equipamento = equipamentos.buscar(dados.getEquipamento().getId());

        OrdemServico os = new OrdemServico();
        os.setEquipamento(equipamento);
        os.setDefeitoRelatado(limitar(dados.getDefeitoRelatado(), 500, "Defeito relatado"));
        os.setNumero("TMP-" + UUID.randomUUID());   // provisório, troca logo abaixo
        os = repository.save(os);
        os.setNumero(String.format("OS-%d-%04d", Year.now().getValue(), os.getId()));
        return repository.save(os);
    }

    public OrdemServico atualizar(Long id, OrdemServico dados) {
        OrdemServico os = buscar(id);
        if (dados == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe os dados.");
        if (dados.getStatus() != null) {
            String status = dados.getStatus().trim().toUpperCase();
            if (!STATUS.contains(status)) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Status inválido. Use: " + STATUS);
            os.setStatus(status);
        }
        if (dados.getValorOrcamento() != null) {
            if (dados.getValorOrcamento().compareTo(BigDecimal.ZERO) < 0) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "O orçamento não pode ser negativo.");
            os.setValorOrcamento(dados.getValorOrcamento());
        }
        if (dados.getDefeitoRelatado() != null)
            os.setDefeitoRelatado(limitar(dados.getDefeitoRelatado(), 500, "Defeito relatado"));
        return repository.save(os);
    }

    public OrdemServico adicionarServico(Long id, Servico dados) {
        OrdemServico os = buscar(id);
        if (dados == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o serviço.");
        if (dados.getValor() == null || dados.getValor().compareTo(BigDecimal.ZERO) < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe um valor válido.");
        Servico s = new Servico();
        s.setDescricao(Validacao.obrigatorio(dados.getDescricao(), "Descrição", 200));
        s.setValor(dados.getValor());
        s.setOrdemServico(os);
        os.getServicos().add(s);
        return repository.save(os);
    }

    public void deletar(Long id) { repository.delete(buscar(id)); }

    private String limitar(String texto, int max, String campo) {
        if (texto == null || texto.isBlank()) return null;
        String t = texto.trim();
        if (t.length() > max) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            campo + " deve ter no máximo " + max + " caracteres.");
        return t;
    }
}