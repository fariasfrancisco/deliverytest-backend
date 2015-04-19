package com.safira.domain;

import com.safira.domain.entities.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Class intended to simplify List storage of Menu objects and ease json transmision.
 */
public class Menus {
    private List<Menu> menus = new ArrayList<>();

    public Menus() {
    }

    public Menus(List<Menu> menus) {
        this.menus = menus;
    }

    public Menu get(int i) {
        return menus.get(i);
    }

    public List<Menu> getMenus() {
        return menus;
    }
}
