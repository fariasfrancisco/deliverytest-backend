package com.safira.domain;

import com.safira.entities.Menu;

import java.util.List;

/**
 * Class intended to simplify List storage of Menu objects and ease jSon transmision.
 */
public class Menus {
    private List<Menu> menus;

    public Menus(List<Menu> menus) {
        this.menus = menus;
    }

    public Menu get(int i) {
        return menus.get(i);
    }

    public List<Menu> getMenus() {
        return menus;
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
