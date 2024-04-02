package edu.unimagdalena.tiendaEnLinea.excepción;

public class PedidoNotFoundException extends RuntimeException{
    public PedidoNotFoundException() {
    }

    public PedidoNotFoundException(String message) {
        super(message);
    }

    public PedidoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PedidoNotFoundException(Throwable cause) {
        super(cause);
    }

    public PedidoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    



}
