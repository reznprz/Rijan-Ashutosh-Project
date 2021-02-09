package com.example.demo.swaggerdemo.exception;

import com.example.demo.swaggerdemo.model.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ExceptionResponse handleResourceNotFound(final ResourceNotFoundException
                                                                         resourceNotFoundException,
                                             final HttpServletRequest httpServletRequest){

        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(resourceNotFoundException.getMessage());
        exceptionResponse.callerURL(httpServletRequest.getRequestURI());

        return exceptionResponse;

    }

    public @ResponseBody ExceptionResponse handleException(final Exception exception,
                                                           final HttpServletRequest httpServletRequest){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(exception.getMessage());
        exceptionResponse.callerURL(httpServletRequest.getRequestURI());

        return exceptionResponse;
    }

}
