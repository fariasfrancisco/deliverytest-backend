package com.safira.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Francisco on 26/02/2015.
 */

@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {
    private int id;
    private String calle;
    private String numero;
    private String piso;
    private String puerta;
    private String telefono;
    private LocalDate fecha;
    private Usuario usuario;
    private Set<Menu> menus;

    public Pedido(){}
    public Pedido(String calle, String numero, String piso, String puerta, String telefono, LocalDate fecha) {
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
        this.puerta = puerta;
        this.telefono = telefono;
        this.fecha = fecha;
    }

    public Pedido(String calle, String numero, String piso, String puerta, String telefono, LocalDate fecha, Usuario usuario, Set<Menu> menus) {
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
        this.puerta = puerta;
        this.telefono = telefono;
        this.fecha = fecha;
        this.usuario = usuario;
        this.menus = menus;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "PedidoId", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "calle", nullable = false)
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    @Column(name = "numero", nullable = false)
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Column(name = "piso", nullable = true)
    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    @Column(name = "puerta", nullable = true)
    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    @Column(name = "telefono", nullable = false)
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Column(name = "fecha", nullable = false)
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "UsuarioId", nullable = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }
}
