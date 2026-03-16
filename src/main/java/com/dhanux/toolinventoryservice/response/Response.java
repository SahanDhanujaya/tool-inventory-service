package com.dhanux.toolinventoryservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author Dhanujaya(Dhanu)
 * @TimeStamp 16/03/2026 16:05
 * @ProjectDetails tool-inventory-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private String message;
    private T store;
    private HttpStatus status;
}