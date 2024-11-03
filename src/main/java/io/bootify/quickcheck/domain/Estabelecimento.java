package io.bootify.quickcheck.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Estabelecimento {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false, length = 500)
    private String horarioFuncionamento;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean assinante;

    // JsonBackReference serve para obter o objeto associado (entidade relacionada) em formato JSON
    @OneToMany(mappedBy = "estabelecimento")
    @JsonBackReference
    private Set<Horario> horarios;

    @OneToMany(mappedBy = "estabelecimento")
    @JsonBackReference
    private Set<Funcionario> funcionarios;

    // Passar o FetchType.EAGER se quiser receber o objeto completo na resposta da API
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
