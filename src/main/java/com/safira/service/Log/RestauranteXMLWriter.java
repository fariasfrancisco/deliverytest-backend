package com.safira.service.log;

import com.safira.entities.Restaurante;
import com.safira.entities.RestauranteLogin;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by Francisco on 08/03/2015.
 */
public class RestauranteXMLWriter {
    public static Document createDocument(Restaurante restaurante, RestauranteLogin restauranteLogin) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Restaurante")
                .addAttribute("Id", String.valueOf(restaurante.getId()))
                .addAttribute("Usuario", restauranteLogin.getUsuario());
        Element nombre = root.addElement("Nombre")
                .addText(restaurante.getNombre());
        Element direccion = root.addElement("Direccion")
                .addText(restaurante.getDireccion());
        Element telefono = root.addElement("Telefono")
                .addText(restaurante.getTelefono());
        Element email = root.addElement("Email")
                .addText(restaurante.getEmail());
        return document;
    }
}