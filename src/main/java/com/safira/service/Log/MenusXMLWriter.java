package com.safira.service.Log;

import com.safira.entities.Menu;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.time.LocalDateTime;

/**
 * Created by Francisco on 07/03/2015.
 */
public class MenusXMLWriter {

    public Document createDocument(Menu menu) {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("Menu")
                .addAttribute("Id", String.valueOf(menu.getId()))
                .addAttribute("Nombre", menu.getNombre())
                .addAttribute("Costo", menu.getCosto().toString());

        Element descripcion = root.addElement("Descripcion")
                .addText(menu.getDescripcion());

        Element timeStamp = root.addElement("TimeStamp")
                .addText(LocalDateTime.now().toString());

        return document;
    }
}
