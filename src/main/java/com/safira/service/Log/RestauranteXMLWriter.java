package com.safira.service.Log;

import com.safira.entities.Restaurante;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.time.LocalDateTime;

/**
 * Created by Francisco on 08/03/2015.
 */
public class RestauranteXMLWriter {
    public Document createDocument(Restaurante restaurante) {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("Restaurante")
                .addAttribute("Id", String.valueOf(restaurante.getId()))
                .addAttribute("Usuario", restaurante.getRestauranteLogin().getUsuario());

        Element nombre = root.addElement("Nombre")
                .addText(restaurante.getNombre());

        Element direccion = root.addElement("Direccion")
                .addText(restaurante.getDireccion());

        Element telefono = root.addElement("Telefono")
                .addText(restaurante.getTelefono());

        Element email = root.addElement("Email")
                .addText(restaurante.getEmail());

        Element timeStamp = root.addElement("TimeStamp")
                .addText(LocalDateTime.now().toString());

        return document;
    }
}
