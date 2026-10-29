package dw.editora.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity @Table(name = "equipamento")
@Getter @Setter @NoArgsConstructor
public class Equipamento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String tipo;

    @Column(nullable = false, length = 80)
    private String marca;

    @Column(nullable = false, length = 120)
    private String modelo;

    @Column(length = 120)
    private String numeroSerie;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorOrcamento;

    // vazio = sem orçamento | PENDENTE | ACEITO | REJEITADO
    @Column(length = 20)
    private String statusOrcamento = "AGUARDANDO"; // AGUARDANDO, ACEITO ou RECUSADO
// + getter e setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
}