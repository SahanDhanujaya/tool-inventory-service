package com.dhanux.toolinventoryservice.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dhanujaya(Dhanu)
 * @TimeStamp 16/03/2026 16:00
 * @ProjectDetails tool-inventory-service
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer userId;
    private String title;
    private String description;
    private Double price;
    private String image;
    private String category;
    private String location;
    @ElementCollection
    private List<String> requirements;
    private Boolean availability;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
