package org.chobit.calf.spring.ext;


import org.chobit.calf.except.CalfRemoteException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("org.chobit.calf.web.api")
public class RemoteExceptionHandler {

    @ExceptionHandler(CalfRemoteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgs(CalfRemoteException except) {
        return except.getMessage();
    }
}
