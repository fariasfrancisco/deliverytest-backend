package com.safira.service;

import com.safira.entities.Menu;
import com.safira.entities.Pedido;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by Francisco on 07/03/2015.
 */
public class PedidosXMLWriter {
    public Document createDocument(Pedido pedido) {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("Pedido")
                .addAttribute("Id", pedido.getIdAsString())
                .addAttribute("Fecha", pedido.getFechaAsString())
                .addAttribute("Cantidad", pedido.getCantidadAsString())
                .addAttribute("CostoTotal", pedido.getCostoTotalAsString());

        for (Menu menu : pedido.getMenus()) {
            Element element = root.addElement("Menu")
                    .addAttribute("Id", menu.getIdAsString())
                    .addAttribute("Nombre", menu.getNombre())
                    .addAttribute("Costo", menu.getCostoAsString());
            Element descripcion = element.addElement("Descripcion")
                    .addText(menu.getDescripcion());
        }

        return document;
    }
}
