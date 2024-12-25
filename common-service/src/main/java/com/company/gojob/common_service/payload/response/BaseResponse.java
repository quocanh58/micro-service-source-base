package com.company.gojob.common_service.payload.response;

import com.company.gojob.common_service.utils.LoggerUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Objects;

public class BaseResponse {

    public static ResponseEntity<?> success(String message, Object data, Object extraData) {
        ResponseObject responseObject = ResponseObject.builder()
                .success(true)
                .message(Objects.isNull(message) ? "Operation completed" : message)
                .data(data)
                .extraData(extraData)
                .build();

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }


    public static ResponseEntity<?> success(String message, Object data) {
        return success(message, data, null);
    }


    public static ResponseEntity<?> success(String message) {
        return success(message, null, null);
    }


    public static ResponseEntity<?> success() {
        return success(null, null, null);
    }


    public static ResponseEntity<?> error(String message, String devMessage, HttpStatus status) {
        ResponseObject responseObject = ResponseObject.builder()
                .success(false)
                .message(Objects.isNull(message) ? "An error has occurred" : message)
                .devMessage(Objects.isNull(devMessage) ? "" : devMessage)
                .data(null)
                .build();

        return new ResponseEntity<>(responseObject, (status != null) ? status : HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public static ResponseEntity<?> error(String message, HttpStatus status) {
        return error(message, null, status);
    }


    public static ResponseEntity<?> error(String message) {
        return error(message, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public static ResponseEntity<?> error() {
        return error(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public static ResponseEntity<?> paginate(Page<?> page, String message, Object extraData) {
        try {
            PaginateResponse responseObject = PaginateResponse.builder()
                    .success(true)
                    .message(Objects.isNull(message) ? "Operation completed" : message)
                    .devMessage("")
                    .data(page.getContent())
                    .extraData(extraData)
                    .paginate(new Paginate(
                            page.getTotalElements(),
                            page.getNumber() + 1,
                            page.getSize(),
                            page.getTotalPages()
                    ))
                    .build();
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (Exception e) {
            LoggerUtil.error("Error occurred while processing pagination", e);
            PaginateResponse responseObject = PaginateResponse.builder()
                    .success(false)
                    .message("An error occurred")
                    .devMessage(e.getMessage())
                    .data(Collections.emptyList())
                    .extraData(extraData)
                    .paginate(new Paginate(0, 0, 0, 0))
                    .build();
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static ResponseEntity<?> paginate(Page<?> page, String message) {
        return paginate(page, message, null);
    }

    public static ResponseEntity<?> paginate(Page<?> page) {
        return paginate(page, null, null);
    }
}