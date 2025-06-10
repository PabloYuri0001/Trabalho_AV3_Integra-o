package com.hostpet.hostpet.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hostpet.hostpet.entity.Pet;
import com.hostpet.hostpet.enums.Sexo;

import java.time.LocalDate;

public class PetsListDTO {
    private Integer id;
    private String nome;
    private Sexo sexo;
    private String racaPet;
    private String observacoes;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dtNascimento;
    private Long clienteId;
    private String nomeDono;

    private String telefoneDono;

    public PetsListDTO() {
    }

    public PetsListDTO(Pet pet) {
        this.id = pet.getId();
        this.nome = pet.getNome();
        this.sexo = pet.getSexo();
        this.racaPet = pet.getRacaPet();
        this.observacoes = pet.getObservacoes();
        this.dtNascimento = pet.getDtNascimento();
        this.clienteId = pet.getCliente().getId();
        this.nomeDono = pet.getCliente().getNome();
        this.telefoneDono = pet.getCliente().getTelefone();
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getRacaPet() {
        return racaPet;
    }

    public void setRacaPet(String racaPet) {
        this.racaPet = racaPet;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNomeDono() {
        return nomeDono;
    }

    public void setNomeDono(String nomeDono) {
        this.nomeDono = nomeDono;
    }

    public String getTelefoneDono() {return telefoneDono;}

    public void setTelefoneDono(String telefoneDono) {this.telefoneDono = telefoneDono;}
}