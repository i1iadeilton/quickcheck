package io.bootify.quickcheck.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Usuario {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, name = "\"role\"")
    private String role;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String endereco;

    @Column
    private String imagem;

    // JsonBackReference serve para obter o objeto associado (entidade relacionada) em formato JSON
    @OneToOne(mappedBy = "usuario")
    @JsonBackReference(value = "usuario-cliente")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Cliente cliente;

    @OneToOne(mappedBy = "usuario")
    @JsonBackReference(value = "usuario-funcionario")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Funcionario funcionario;

    @OneToOne(mappedBy = "usuario")
    @JsonBackReference(value = "usuario-estabelecimento")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Estabelecimento estabelecimento;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
