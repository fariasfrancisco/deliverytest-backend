package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.safira.domain.entity.ModelEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by francisco on 24/03/15.
 */
@Entity
public class Favoritos extends ModelEntity {

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Menu_Favoritos", joinColumns = {
            @JoinColumn(name = "favoritos_id", nullable = false, updatable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "menu_id", nullable = false, updatable = false)
    })
    private Set<Menu> menus = new HashSet<>();

    @JsonManagedReference
    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Usuario usuario;

    public Favoritos() {
        this(UUID.randomUUID());
    }

    public Favoritos(UUID uuid) {
        super(uuid);
    }

    public Favoritos(Builder builder) {
        super(UUID.randomUUID());
        this.menus = builder.menus;
        for (Menu menu : menus) {
            if (!menu.getFavoritos().contains(this)) menu.getFavoritos().add(this);
        }
        this.usuario = builder.usuario;
        if (usuario.getFavoritos() == this) usuario.setFavoritos(this);
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
        for (Menu menu : menus) {
            if (!menu.getFavoritos().contains(this)) menu.getFavoritos().add(this);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario.getFavoritos() == this) usuario.setFavoritos(this);
    }

    private static class Builder {
        private Set<Menu> menus = new HashSet<>();
        private Usuario usuario;

        public Builder withMenus(Set<Menu> menus) {
            this.menus = menus;
            return this;
        }

        public Builder withUsuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Favoritos build() {
            return new Favoritos(this);
        }
    }
}
