package org.sber.simonov.dit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class StorageException extends RuntimeException {
    public StorageException(Throwable cause) {
        super(cause);
    }
}