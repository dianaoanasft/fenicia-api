package com.fiapi.exception;

public class ModelSaveException extends Exception {

    public ModelSaveException(String message) {
        super(message);
    }

    public ModelSaveException(String message, Throwable cause) {
        super(message, cause);
    }

}
