package com.safira.service.log;

import com.safira.domain.entities.Menu;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.time.LocalDateTime;

public class MenusXMLWriter {

    public static Document createDocument(Menu menu) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Menu")
                .addAttribute("UUID", menu.getIdentifier())
                .addAttribute("Nombre", menu.getNombre())
                .addAttribute("Costo", menu.getCosto().toString());
        Element creation = root.addElement("Creation")
                .addAttribute("TimeStamp", LocalDateTime.now().toString())
                .addText(menu.getRestaurante().getIdentifier());
        Element descripcion = root.addElement("Descripcion")
                .addText(menu.getDescripcion());
        return document;
    }
}