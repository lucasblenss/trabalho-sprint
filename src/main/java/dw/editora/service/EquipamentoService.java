package dw.editora.service;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import dw.editora.entity.Cliente;
import dw.editora.entity.Equipamento;
import dw.editora.repository.EquipamentoRepository;
@Service @Transactional
public class EquipamentoService {
    private final EquipamentoRepository repository;
    private final ClienteService clientes;
    public EquipamentoService(EquipamentoRepository repository, ClienteService clientes) {
        this.repository = repository;
        this.clientes = clientes;
    }
    @Transactional(readOnly = true)
    public List<Equipamento> listar(String modelo) {
        return modelo == null || modelo.isBlank() ? repository.findAll()
            : repository.findByModeloContainingIgnoreCase(modelo.trim());
    }
    @Transactional(readOnly = true)
    public Equipamento buscar(Long id) {
        Validacao.id(id);
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Equipamento não encontrado."));
    }
    @Transactional(readOnly = true)
    public List<Equipamento> listarPorCliente(Long id) {
        clientes.buscar(id);
        return repository.findByCliente_Id(id);
    }
    public Equipamento salvar(Equipamento dados) {
        Cliente cliente = validar(dados);
        Equipamento equipamento = new Equipamento();
        copiar(dados, equipamento, cliente);
        return repository.save(equipamento);
    }
    public Equipamento atualizar(Long id, Equipamento dados) {
        Equipamento equipamento = buscar(id);
        Cliente cliente = validar(dados);
        copiar(dados, equipamento, cliente);
        return repository.save(equipamento);
    }
    public void deletar(Long id) { repository.delete(buscar(id)); }
    private Cliente validar(Equipamento dados) {
        if (dados == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o equipamento.");
        dados.setTipo(Validacao.obrigatorio(dados.getTipo(), "Tipo", 60));
        dados.setMarca(Validacao.obrigatorio(dados.getMarca(), "Marca", 80));
        dados.setModelo(Validacao.obrigatorio(dados.getModelo(), "Modelo", 120));
        if (dados.getNumeroSerie() != null) {
            String serie = dados.getNumeroSerie().trim();
            if (serie.length() > 120) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Número de série deve ter no máximo 120 caracteres.");
            dados.setNumeroSerie(serie.isEmpty() ? null : serie);
        }
        if (dados.getCliente() == null || dados.getCliente().getId() == null) throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST, "Informe o ID do cliente.");
        return clientes.buscar(dados.getCliente().getId());
    }
    private void copiar(Equipamento origem, Equipamento destino, Cliente cliente) {
        destino.setTipo(origem.getTipo());
        destino.setMarca(origem.getMarca());
        destino.setModelo(origem.getModelo());
        destino.setNumeroSerie(origem.getNumeroSerie());
        destino.setCliente(cliente);
    }
}
