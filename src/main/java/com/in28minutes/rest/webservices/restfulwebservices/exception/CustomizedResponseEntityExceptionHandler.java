package com.in28minutes.rest.webservices.restfulwebservices.exception;

import com.in28minutes.rest.webservices.restfulwebservices.user.UserNotFoundException;
import java.net.http.HttpHeaders;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler
  extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorDetails> handleAllExceptions(
    Exception ex,
    WebRequest request
  ) {
    ErrorDetails errorDetails = new ErrorDetails(
      LocalDate.now(),
      ex.getMessage(),
      request.getDescription(false)
    );
    return new ResponseEntity<ErrorDetails>(
      errorDetails,
      HttpStatus.INTERNAL_SERVER_ERROR
    );
  }

  @ExceptionHandler(UserNotFoundException.class)
  public final ResponseEntity<ErrorDetails> handleUserNotFoundException(
    Exception ex,
    WebRequest request
  ) {
    ErrorDetails errorDetails = new ErrorDetails(
      LocalDate.now(),
      ex.getMessage(),
      request.getDescription(false)
    );
    return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex,
    org.springframework.http.HttpHeaders headers,
    HttpStatusCode status,
    WebRequest request
  ) {
    ErrorDetails errorDetails = new ErrorDetails(
      LocalDate.now(),
      "Total errors: " + ex.getErrorCount() + ", First error: " + ex.getFieldError().getDefaultMessage(),
      request.getDescription(false)
    );
    return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
  }
}
