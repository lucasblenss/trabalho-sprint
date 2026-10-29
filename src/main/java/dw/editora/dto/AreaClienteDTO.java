package dw.editora.dto;

import java.math.BigDecimal;
import java.util.List;
import dw.editora.entity.Equipamento;

public record AreaClienteDTO(String nome, List<Aparelho> aparelhos) {

    public record Aparelho(Long id, String descricao, BigDecimal valorOrcamento, String statusOrcamento) {
        public static Aparelho de(Equipamento e) {
            return new Aparelho(e.getId(),
                e.getTipo() + " " + e.getMarca() + " " + e.getModelo(),
                e.getValorOrcamento(), e.getStatusOrcamento());
        }
    }
}