package com.onqlave.error;

public class OnqlaveError extends Exception {
    private final ErrorCodes code;
    private final String message;
    private final Throwable originalError;

    public OnqlaveError(ErrorCodes code, String message) {
        this.code = code;
        this.message = message;
        this.originalError = null;
    }

    public OnqlaveError(ErrorCodes code, String message, Throwable originalError) {
        this.code = code;
        this.message = message;
        this.originalError = originalError;
    }

    public ErrorCodes getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getOriginalError() {
        return originalError;
    }

    @Override
    public String toString() {
        String originalErrorString = (originalError != null) ? originalError.toString() : "<nil>";
        return String.format("Message: %s, Original Error (if any): %s", message, originalErrorString);
    }
}
