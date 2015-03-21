package com.safira.service.log;

import com.safira.domain.entities.Usuario;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Class in charge of creating XML data for a new created Usuario.
 */
public class UsuarioXMLWriter {
    public static Document createDocument(Usuario usuario) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Usuario")
                .addAttribute("Id", String.valueOf(usuario.getId()));
        Element nombre = root.addElement("NombreYApellido")
                .addText(usuario.getNombre() + " " + usuario.getApellido());
        Element facebookId = root.addElement("FacebookId")
                .addText(usuario.getFacebookId());
        Element email = root.addElement("Email")
                .addText(usuario.getEmail());
        return document;
    }
}