package com.safira.common.exceptions;

/**
 * Created by francisco on 05/04/15.
 */
public class PedidoTimeoutException extends SafiraException {
    public PedidoTimeoutException() {
        super("The creation request for Pedido took too long.", "Please try again later.");
    }

}
