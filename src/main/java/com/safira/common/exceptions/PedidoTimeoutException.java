package com.safira.common.exceptions;

/**
 * Created by francisco on 05/04/15.
 */
public class PedidoTimeoutException extends Exception {
    public PedidoTimeoutException() {
        super("Pedido Timeout. More than 15 minutes have passed since the request was sent");
    }

    public PedidoTimeoutException(String message) {
        super(message);
    }

    public PedidoTimeoutException(Throwable cause) {
        super(cause);
    }

    public PedidoTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
