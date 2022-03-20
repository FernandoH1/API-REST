package com.example.demo.models;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "usuario")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String nombre;
    private String email;
    private Integer prioridad;

    public UsuarioModel(String nombre) {
        this.nombre = nombre;
    }

    public UsuarioModel() {}

    public UsuarioModel(Long id, String nombre, String email, Integer prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.prioridad = prioridad;
    }

    public void setPrioridad(Integer prioridad){
        this.prioridad = prioridad;
    }

    public Integer getPrioridad(){
        return prioridad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    
}