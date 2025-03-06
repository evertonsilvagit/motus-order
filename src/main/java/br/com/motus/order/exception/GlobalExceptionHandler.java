package br.com.motus.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CantDeleteProductException.class)
  public ResponseEntity handleCantDeleteProductException(CantDeleteProductException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity handleProductNotFoundException(ProductNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(CantCreateOrderProductDoesntExistException.class)
  public ResponseEntity handleCantCreateOrderProductDoesntExistException(
      CantCreateOrderProductDoesntExistException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity handleBeanValidationErrors(MethodArgumentNotValidException ex){
    var errors = ex.getFieldErrors();
    var beanValidationErrorDTOS = errors.stream()
        .map(BeanValidationErrorDTO::new)
        .toList();

    return ResponseEntity.badRequest().body(beanValidationErrorDTOS);
  }

  private record BeanValidationErrorDTO(String field, String message){
    public BeanValidationErrorDTO(FieldError error){
      this(error.getField(), error.getDefaultMessage());
    }
  }

}
