package com.safira.service.log;

import com.safira.domain.entities.Menu;
import com.safira.domain.entities.MenuPedido;
import com.safira.domain.entities.Pedido;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.time.LocalDateTime;

/**
 * Class in charge of creating XML data for a new created Pedido.
 */
public class PedidosXMLWriter {
    public static Document createDocument(Pedido pedido) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Pedido")
                .addAttribute("UUID", pedido.getUuid())
                .addAttribute("Fecha", pedido.getFecha().toString())
                .addAttribute("Cantidad", String.valueOf(pedido.retrieveMenuList().size()))
                .addAttribute("CostoTotal", pedido.calculateTotalCost().toString());
        Element creation = root.addElement("Creation")
                .addAttribute("TimeStamp", LocalDateTime.now().toString())
                .addText(pedido.getUsuario().getUuid());
        for (MenuPedido menuPedido : pedido.getMenuPedidos()) {
            Menu menu = menuPedido.getMenu();
            Element element = root.addElement("Menu")
                    .addAttribute("Id", String.valueOf(menu.getId()))
                    .addAttribute("Nombre", menu.getNombre())
                    .addAttribute("Costo", menu.getCosto().toString())
                    .addAttribute("cantidad", menuPedido.getCantidad().toString());
            Element descripcion = element.addElement("Descripcion")
                    .addText(menu.getDescripcion());
        }
        return document;
    }
}