package com.dhanux.toolinventoryservice.controller;

import com.dhanux.toolinventoryservice.dto.ToolInventoryDto;
import com.dhanux.toolinventoryservice.exception.ToolInventoryException;
import com.dhanux.toolinventoryservice.model.ToolInventory;
import com.dhanux.toolinventoryservice.response.Response;
import com.dhanux.toolinventoryservice.service.ToolInventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dhanujaya(Dhanu)
 * @TimeStamp 16/03/2026 15:56
 * @ProjectDetails tool-inventory-service
 */

@RestController
@RequestMapping("/api/v1/tools")
@RequiredArgsConstructor
public class ToolInventoryController {
    private final ToolInventoryService toolInventoryService;
    private final ModelMapper modelMapper;

    @PostMapping()
    public ResponseEntity<ToolInventoryDto> saveTool(@RequestBody @Valid ToolInventoryDto toolInventoryDto) {
        try {
            ToolInventory savedTool = toolInventoryService.save(modelMapper.map(toolInventoryDto, ToolInventory.class));
            if (savedTool != null) {
                return ResponseEntity.status(201).body(
                        new Response<>(
                                "Tool listed successfully",
                                modelMapper.map(savedTool, ToolInventoryDto.class),
                                HttpStatus.CREATED
                        ).getStore()
                );
            }
        } catch (ToolInventoryException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new Response<ToolInventoryDto>(
                        "Tool not listed",
                        null,
                        HttpStatus.BAD_REQUEST
                ).getStore()
        );
    }

    @GetMapping
    public String getToolList(){
        return "Tool List";
    }
}
