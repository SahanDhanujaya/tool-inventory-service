package com.dhanux.toolinventoryservice.dto;

import com.dhanux.toolinventoryservice.validation.ValidImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    private String id;
    private String userEmail;
    private String title;
    private String description;
    private Double price;
    private MultipartFile image;
    private String category;
    private String location;
    private List<String> requirements;
    private Boolean availability;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
