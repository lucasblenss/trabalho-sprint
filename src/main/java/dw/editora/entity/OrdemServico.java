package dw.editora.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "ordem_servico")
@Getter @Setter @NoArgsConstructor
public class OrdemServico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // O "CPF próprio": número que o cliente usa pra consultar
    @Column(nullable = false, unique = true, length = 20)
    private String numero;

    @ManyToOne(optional = false)
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @Column(length = 500)
    private String defeitoRelatado;

    // AGUARDANDO_ORCAMENTO, ORCAMENTO_ENVIADO, EM_REPARO, PRONTO, ENTREGUE
    @Column(nullable = false, length = 30)
    private String status = "AGUARDANDO_ORCAMENTO";

    @Column(precision = 10, scale = 2)
    private BigDecimal valorOrcamento;

    private LocalDateTime dataEntrada = LocalDateTime.now();

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Servico> servicos = new ArrayList<>();
}