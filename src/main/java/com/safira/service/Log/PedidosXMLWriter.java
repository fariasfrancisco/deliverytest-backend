package com.safira.service.log;

import com.safira.entities.Menu;
import com.safira.entities.Pedido;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by Francisco on 07/03/2015.
 */
public class PedidosXMLWriter {
    public static Document createDocument(Pedido pedido) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Pedido")
                .addAttribute("Id", String.valueOf(pedido.getId()))
                .addAttribute("Fecha", pedido.getFecha().toString())
                .addAttribute("Cantidad", String.valueOf(pedido.getMenus().size()))
                .addAttribute("CostoTotal", pedido.costoTotalAsString());
        for (Menu menu : pedido.getMenus()) {
            Element element = root.addElement("Menu")
                    .addAttribute("Id", String.valueOf(menu.getId()))
                    .addAttribute("Nombre", menu.getNombre())
                    .addAttribute("Costo", menu.getCosto().toString());
            Element descripcion = element.addElement("Descripcion")
                    .addText(menu.getDescripcion());
        }
        return document;
    }
}