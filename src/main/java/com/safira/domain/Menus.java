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

    public Menu get(int i) {
        return menus.get(i);
    }

    @Override
    public String toString() {
        String s = "Menus{";
        for (Menu m : menus) {
            s += m.toString();
        }
        s += '}';
        return s;
    }
}
