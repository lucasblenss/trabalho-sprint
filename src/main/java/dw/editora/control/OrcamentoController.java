package dw.editora.control;

import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dw.editora.dto.AreaClienteDTO;
import dw.editora.service.OrcamentoService;

@RestController @RequestMapping("/api")
public class OrcamentoController {
    private final OrcamentoService service;

    public OrcamentoController(OrcamentoService service) { this.service = service; }

    public record ValorDTO(BigDecimal valor) {}

    // ---- cliente ----
    @GetMapping("/area-cliente/{clienteId}")
    public ResponseEntity<AreaClienteDTO> area(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.area(clienteId));
    }

    @PostMapping("/area-cliente/{clienteId}/equipamentos/{equipId}/aceitar")
    public ResponseEntity<Void> aceitar(@PathVariable Long clienteId, @PathVariable Long equipId) {
        service.responder(clienteId, equipId, true);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/area-cliente/{clienteId}/equipamentos/{equipId}/rejeitar")
    public ResponseEntity<Void> rejeitar(@PathVariable Long clienteId, @PathVariable Long equipId) {
        service.responder(clienteId, equipId, false);
        return ResponseEntity.noContent().build();
    }

    // ---- vocês ----
    @PutMapping("/orcamentos/equipamentos/{equipId}")
    public ResponseEntity<Void> definirValor(@PathVariable Long equipId, @RequestBody ValorDTO dados) {
        service.definirValor(equipId, dados.valor());
        return ResponseEntity.noContent().build();
    }
}