package com.safira.service.log;

import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.RestauranteLogin;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Class in charge of creating XML data for a new created Restaurante and RestauranteLogin.
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
                .addText(restaurante.getCalle() + " " + restaurante.getNumero());
        Element telefono = root.addElement("Telefono")
                .addText(restaurante.getTelefono());
        Element email = root.addElement("Email")
                .addText(restaurante.getEmail());
        return document;
    }
}