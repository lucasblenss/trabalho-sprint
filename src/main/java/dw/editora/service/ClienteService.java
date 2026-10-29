package dw.editora.service;
import java.util.List;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import dw.editora.entity.Cliente;
import dw.editora.repository.ClienteRepository;
import dw.editora.repository.EquipamentoRepository;
@Service @Transactional
public class ClienteService {
    private final ClienteRepository repository;
    private final EquipamentoRepository equipamentos;
    public ClienteService(ClienteRepository repository, EquipamentoRepository equipamentos) {
        this.repository = repository;
        this.equipamentos = equipamentos;
    }
    @Transactional(readOnly = true)
    public List<Cliente> listar() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Cliente buscar(Long id) {
        Validacao.id(id);
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Cliente não encontrado."));
    }
    public Cliente salvar(Cliente dados) {
        validar(dados);
        if (repository.existsByEmailIgnoreCase(dados.getEmail())) throw new ResponseStatusException(
            HttpStatus.CONFLICT, "Já existe cliente com esse e-mail.");
        Cliente cliente = new Cliente();
        copiar(dados, cliente);
        return repository.save(cliente);
    }
    public Cliente atualizar(Long id, Cliente dados) {
        Cliente cliente = buscar(id);
        validar(dados);
        if (repository.existsByEmailIgnoreCaseAndIdNot(dados.getEmail(), id)) throw new ResponseStatusException(
            HttpStatus.CONFLICT, "Já existe cliente com esse e-mail.");
        copiar(dados, cliente);
        return repository.save(cliente);
    }
    public void deletar(Long id) {
        Cliente cliente = buscar(id);
        if (equipamentos.existsByCliente_Id(id)) throw new ResponseStatusException(
            HttpStatus.CONFLICT, "Cliente possui equipamentos vinculados. Remova ou transfira os equipamentos primeiro.");
        repository.delete(cliente);
    }
    private void validar(Cliente dados) {
        if (dados == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o cliente.");
        dados.setNome(Validacao.obrigatorio(dados.getNome(), "Nome", 120));
        dados.setEmail(Validacao.obrigatorio(dados.getEmail(), "E-mail", 254).toLowerCase(Locale.ROOT));
        if (!dados.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST, "E-mail inválido.");
        dados.setTelefone(Validacao.obrigatorio(dados.getTelefone(), "Telefone", 30));
    }
    private void copiar(Cliente origem, Cliente destino) {
        destino.setNome(origem.getNome());
        destino.setEmail(origem.getEmail());
        destino.setTelefone(origem.getTelefone());
    }
}
