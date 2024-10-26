package io.bootify.quickcheck.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClienteDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String cpf;

    @NotNull
    private Integer idade;

    @NotNull
    private LocalDate nascimento;

    @NotNull
    @Size(max = 1)
    private String sexo;

    @NotNull
    @Size(max = 255)
    private String latitude;

    @NotNull
    @Size(max = 255)
    private String longitude;

    @Size(max = 255)
    private String numeroCartaoSUS;

    private List<@Size(max = 255) String> comorbidades;

    @ClienteUsuarioUnique
    private Long usuario;
    
    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNumeroCartaoSUS() {
        return numeroCartaoSUS;
    }

    public void setNumeroCartaoSUS(String numeroCartaoSUS) {
        this.numeroCartaoSUS = numeroCartaoSUS;
    }

    public List<String> getComorbidades() {
        return comorbidades;
    }

    public void setComorbidades(List<String> comorbidades) {
        this.comorbidades = comorbidades;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

}
