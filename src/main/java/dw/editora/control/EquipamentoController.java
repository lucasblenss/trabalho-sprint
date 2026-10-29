package dw.editora.control;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dw.editora.entity.*;
import dw.editora.service.*;
@RestController @RequestMapping("/api/equipamentos")
public class EquipamentoController {
    private final EquipamentoService service;

    public EquipamentoController(EquipamentoService service) { this.service = service;  }
    @GetMapping
    public ResponseEntity<List<Equipamento>> listar(@RequestParam(required = false) String modelo) {
        return ResponseEntity.ok(service.listar(modelo));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Equipamento> buscar(@PathVariable Long id) { return ResponseEntity.ok(service.buscar(id)); }
    @PostMapping
    public ResponseEntity<Equipamento> salvar(@RequestBody Equipamento dados) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dados));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Equipamento> atualizar(@PathVariable Long id, @RequestBody Equipamento dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
