package dw.editora.control;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> regra(ResponseStatusException erro) {
        return ResponseEntity.status(erro.getStatusCode()).body(Map.of("status", erro.getStatusCode().value(),
            "mensagem", erro.getReason() == null ? "Requisição inválida." : erro.getReason()));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> integridade(DataIntegrityViolationException erro) {
        return ResponseEntity.status(409).body(Map.of("status", 409,
            "mensagem", "Operação viola um vínculo ou um valor único no banco de dados."));
    }
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Map<String, Object>> formato(Exception erro) {
        return ResponseEntity.badRequest().body(Map.of("status", 400,
            "mensagem", "JSON ou parâmetro inválido. Confira os tipos dos campos e IDs."));
    }
}
