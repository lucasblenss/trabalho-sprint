package dw.editora.service;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
class ValidacaoTests {
    @Test void removeEspacos() { assertEquals("Nome", Validacao.obrigatorio(" Nome ", "Nome", 120)); }
    @Test void rejeitaVazio() { assertThrows(ResponseStatusException.class, () -> Validacao.obrigatorio(" ", "Nome", 120)); }
    @Test void rejeitaNulo() { assertThrows(ResponseStatusException.class, () -> Validacao.obrigatorio(null, "Nome", 120)); }
    @Test void rejeitaLongo() { assertThrows(ResponseStatusException.class, () -> Validacao.obrigatorio("abcd", "Nome", 3)); }
    @Test void rejeitaId() { assertThrows(ResponseStatusException.class, () -> Validacao.id(0L)); }
}
