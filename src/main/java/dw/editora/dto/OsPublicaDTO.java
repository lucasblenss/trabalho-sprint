package dw.editora.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import dw.editora.entity.OrdemServico;

public record OsPublicaDTO(
        String numero, String status, String equipamento, String defeitoRelatado,
        BigDecimal valorOrcamento, LocalDateTime dataEntrada, List<ServicoDTO> servicos) {

    public record ServicoDTO(String descricao, BigDecimal valor) {}

    public static OsPublicaDTO de(OrdemServico os) {
        var e = os.getEquipamento();
        return new OsPublicaDTO(
            os.getNumero(), os.getStatus(),
            e.getTipo() + " " + e.getMarca() + " " + e.getModelo(),
            os.getDefeitoRelatado(), os.getValorOrcamento(), os.getDataEntrada(),
            os.getServicos().stream().map(s -> new ServicoDTO(s.getDescricao(), s.getValor())).toList());
    }
}