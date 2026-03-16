package com.dhanux.toolinventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dhanujaya(Dhanu)
 * @TimeStamp 16/03/2026 15:59
 * @ProjectDetails tool-inventory-service
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ToolInventoryDto {
    private Integer id;
    private Integer userId;
    private String title;
    private String description;
    private Double price;
    private String image;
    private String category;
    private String location;
    private List<String> requirements;
    private Boolean availability;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
