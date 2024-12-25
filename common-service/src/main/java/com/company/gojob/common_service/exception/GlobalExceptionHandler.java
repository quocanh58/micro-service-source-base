package com.company.gojob.common_service.exception;

import com.company.gojob.common_service.config.Trans;
import com.company.gojob.common_service.payload.response.ResponseObject;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();  // Chỉnh sửa kiểu dữ liệu

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);  // Thêm thông điệp vào danh sách
        });

        ResponseObject responseObject = new ResponseObject();
        responseObject.setData(null);  // Thay đổi data thành null
        responseObject.setMessage(errors);  // Đặt message là map lỗi

        return ResponseEntity.unprocessableEntity().body(responseObject);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseObject> handleEntityNotFoundException(EntityNotFoundException ex) {

        ResponseObject responseObject = new ResponseObject();
        responseObject.setData(null);
        responseObject.setMessage(Trans.getMessage("error.not-found"));
        responseObject.setDevMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseObject> handleGlobalException(Exception ex) {

        ResponseObject responseObject = new ResponseObject();
        responseObject.setData(null);
        responseObject.setDevMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObject);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseObject> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setData(null);

        responseObject.setMessage(
                Trans.getMessage(
                        "error.missing-request-parameter",
                        new Object[]{ex.getParameterName()}
                )
        );

        responseObject.setDevMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(responseObject);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseObject> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setData(null);

        responseObject.setMessage(
                Trans.getMessage(
                        "error.type-mismatch-parameter",
                        new Object[]{ex.getName()}
                )
        );

        responseObject.setDevMessage(
                Trans.getMessage(
                        "error.type-mismatch",
                        new Object[]{ex.getRequiredType(), ex.getValue()}
                )
        );

        return ResponseEntity.badRequest().body(responseObject);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseObject> handleIllegalArgumentException(IllegalArgumentException ex) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setData(null);
        responseObject.setMessage(Trans.getMessage("error.illegal-argument"));
        responseObject.setDevMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(responseObject);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseObject> handleNullPointerException(NullPointerException ex) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setData(null);
        responseObject.setMessage(Trans.getMessage("error.null-pointer"));
        responseObject.setDevMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObject);
    }
}
