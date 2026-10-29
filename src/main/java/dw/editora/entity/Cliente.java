package dw.editora.entity;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
@Entity @Table(name = "cliente")
@Getter @Setter @NoArgsConstructor
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String nome;
    @Column(nullable = false, unique = true, length = 254)
    private String email;
    @Column(nullable = false, length = 30)
    private String telefone;
    @OneToMany(mappedBy = "cliente") @JsonIgnore
    private List<Equipamento> equipamentos = new ArrayList<>();
}
