package dw.editora.control;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dw.editora.entity.*;
import dw.editora.service.*;
@RestController @RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService service;
    private final EquipamentoService equipamentos;
    public ClienteController(ClienteService service, EquipamentoService equipamentos) { this.service = service; this.equipamentos = equipamentos; }
    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(service.listar());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long id) { return ResponseEntity.ok(service.buscar(id)); }
    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente dados) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dados));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/equipamentos")
    public ResponseEntity<List<Equipamento>> equipamentos(@PathVariable Long id) {
        return ResponseEntity.ok(equipamentos.listarPorCliente(id));
    }
}
