package br.com.kauebarreto.jsondiff.exception;

public abstract class DiffException extends Exception {
    public DiffException(String message) {
        super(message);
    }

    public DiffException() {
    }
}
