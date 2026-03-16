package backend.lostandfound.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
@RestControllerAdvice
public class MyGlobalExceptionHandler {
    @ExceptionHandler(DuplicateRegNoException.class)
    public ResponseEntity<Object> handleConflict(DuplicateRegNoException ex,WebRequest request){
        Map<String,Object> body= new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false));
        return  new ResponseEntity<>(body, HttpStatus.CONFLICT);

    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object>  handleUserNotFound(UserNotFoundException ex,WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value()); // 404
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false));

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->{
        String fieldName=error.getField();
        String errorMessage=error.getDefaultMessage();
        errors.put(fieldName,errorMessage);

    });
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value()); // 400
        body.put("errors", errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);}

    @ExceptionHandler(SamePasswordException.class)
    public ResponseEntity<Object> handleSamePassword(SamePasswordException ex,WebRequest request){
        Map<String,Object> body=new LinkedHashMap<>();
        body.put("timestamp",LocalDateTime.now());
        body.put("status",HttpStatus.UNPROCESSABLE_ENTITY.value());
        body.put("error","old and new password are same");
        body.put("message",ex.getMessage());
        body.put("path",request.getDescription(false));
        return new ResponseEntity<>(body,HttpStatus.UNPROCESSABLE_ENTITY);


    }
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<Object> handlePasswordNotMatch(PasswordNotMatchException ex,WebRequest request){

        Map<String,Object> body=new LinkedHashMap<>();
        body.put("timestamp",LocalDateTime.now());
        body.put("status",HttpStatus.UNAUTHORIZED.value());
        body.put("error","Passwords are different-Invalid User");
        body.put("message",ex.getMessage());
        body.put("path",request.getDescription(false));
        return new ResponseEntity<>(body,HttpStatus.UNAUTHORIZED);

    }



}
