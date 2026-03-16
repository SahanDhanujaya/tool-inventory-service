package com.dhanux.toolinventoryservice.exception;

import com.dhanux.toolinventoryservice.dto.ToolInventoryDto;
import com.dhanux.toolinventoryservice.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Dhanujaya(Dhanu)
 * @TimeStamp 16/03/2026 16:04
 * @ProjectDetails tool-inventory-service
 */

@RestControllerAdvice
public class GlobalExceptionHandler extends Exception{
    @ExceptionHandler(ToolInventoryException.class)
    public Response<ToolInventoryDto> handleToolInventoryException(ToolInventoryException ex) {
        return new Response<>(ex.getMessage(), null, HttpStatus.BAD_REQUEST);
    }

    public Response<ToolInventoryDto> handleRuntimeException(RuntimeException ex) {
        return new Response<>(ex.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
