package com.safira.service;

import com.safira.entities.Menu;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by Francisco on 07/03/2015.
 */
public class MenusXMLWriter {

    public Document createDocument(Menu menu) {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("Menu")
                .addAttribute("Id", menu.getIdAsString())
                .addAttribute("Nombre", menu.getNombre())
                .addAttribute("Costo", menu.getCostoAsString());

        Element descripcion = root.addElement("Descripcion")
                .addText(menu.getDescripcion());

        return document;
    }
}
