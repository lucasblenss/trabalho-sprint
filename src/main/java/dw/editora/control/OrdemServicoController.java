package dw.editora.control;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dw.editora.dto.OsPublicaDTO;
import dw.editora.entity.*;
import dw.editora.service.*;

@RestController @RequestMapping("/api/os")
public class OrdemServicoController {
    private final OrdemServicoService service;

    public OrdemServicoController(OrdemServicoService service) { this.service = service; }

    // ---------- PÚBLICO (o cliente usa) ----------
    @GetMapping("/consulta/{numero}")
    public ResponseEntity<OsPublicaDTO> consultar(@PathVariable String numero) {
        return ResponseEntity.ok(OsPublicaDTO.de(service.consultar(numero)));
    }

    // ---------- INTERNO (vocês usam) ----------
    @GetMapping
    public ResponseEntity<List<OrdemServico>> listar() { return ResponseEntity.ok(service.listar()); }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServico> buscar(@PathVariable Long id) { return ResponseEntity.ok(service.buscar(id)); }

    @PostMapping
    public ResponseEntity<OrdemServico> abrir(@RequestBody OrdemServico dados) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.abrir(dados));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemServico> atualizar(@PathVariable Long id, @RequestBody OrdemServico dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    @PostMapping("/{id}/servicos")
    public ResponseEntity<OrdemServico> adicionarServico(@PathVariable Long id, @RequestBody Servico dados) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.adicionarServico(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}