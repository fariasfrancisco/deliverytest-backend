package com.safira.service.log;

import com.safira.domain.entities.Direccion;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.time.LocalDateTime;

/**
 * Created by francisco on 06/04/15.
 */
public class DireccionXMLWriter {
    public static Document createDocument(Direccion direccion) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Direccion")
                .addAttribute("UUID", direccion.getIdentifier())
                .addText(direccion.getCalle())
                .addText(direccion.getNumero())
                .addText(direccion.getPiso())
                .addText(direccion.getDepartamento());
        Element creation = root.addElement("Creation")
                .addAttribute("TimeStamp", LocalDateTime.now().toString())
                .addText(direccion.getUsuario().getIdentifier());
        return document;
    }
}
