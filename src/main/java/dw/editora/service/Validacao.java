package dw.editora.service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
final class Validacao {
    private Validacao() {}
    static String obrigatorio(String valor, String campo, int limite) {
        if (valor == null || valor.isBlank()) throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST, campo + " é obrigatório.");
        String texto = valor.trim();
        if (texto.length() > limite) throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST, campo + " deve ter no máximo " + limite + " caracteres.");
        return texto;
    }
    static void id(Long id) {
        if (id == null || id <= 0) throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST, "O ID deve ser positivo.");
    }
}
