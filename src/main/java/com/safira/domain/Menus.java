package com.safira.domain;

import com.safira.entities.Menu;

import java.util.List;

/**
 * Created by Francisco on 26/02/2015.
 */
public class Menus {
    private List<Menu> menus;

    public Menus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        String s = "Menus{menus=";
        for (Menu m : menus) {
            s += m.toString();
        }
        s += '}';
        return s;
    }
}
