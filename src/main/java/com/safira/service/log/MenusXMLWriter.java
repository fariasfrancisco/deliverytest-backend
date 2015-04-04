package com.safira.service.log;

import com.safira.domain.entities.Menu;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.time.LocalDateTime;

/**
 * Class in charge of creating XML data for a new created Menu.
 */
public class MenusXMLWriter {

    public static Document createDocument(Menu menu) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Menu")
                .addAttribute("Id", String.valueOf(menu.getId()))
                .addAttribute("Nombre", menu.getNombre())
                .addAttribute("Costo", menu.getCosto().toString());
        Element creation = root.addElement("Creation")
                .addAttribute("TimeStamp", LocalDateTime.now().toString())
                .addText(menu.getRestaurante().getNombre());
        Element descripcion = root.addElement("Descripcion")
                .addText(menu.getDescripcion());
        return document;
    }
}