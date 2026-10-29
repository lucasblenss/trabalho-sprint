package dw.editora.entity;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "servico")
@Getter @Setter @NoArgsConstructor
public class Servico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    @JsonIgnore
    private OrdemServico ordemServico;
}