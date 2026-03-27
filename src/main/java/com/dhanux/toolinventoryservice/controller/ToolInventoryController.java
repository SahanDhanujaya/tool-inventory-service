package com.dhanux.toolinventoryservice.controller;

import com.dhanux.toolinventoryservice.dto.ToolInventoryDto;
import com.dhanux.toolinventoryservice.exception.ToolInventoryException;
import com.dhanux.toolinventoryservice.model.ToolInventory;
import com.dhanux.toolinventoryservice.response.Response;
import com.dhanux.toolinventoryservice.service.ToolInventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
    @Value("${spring.servlet.multipart.max-file-size:Not Found}")
    private String maxFileSize;
    @Value("${spring.servlet.multipart.max-file-size:Not Loaded}")
    private String currentLimit;

    @GetMapping("/debug/config")
    public String debugConfig() {
        return "The current active file limit is: " + currentLimit;
    }

    @GetMapping("/check-config")
    public String check() {
        return "Current Max File Size: " + maxFileSize;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<ToolInventoryDto>> saveTool(@ModelAttribute @Valid ToolInventoryDto toolInventoryDto) {
        try {
            ToolInventory savedTool = toolInventoryService.save(toolInventoryDto);
            ToolInventoryDto savedToolDto = modelMapper.map(savedTool, ToolInventoryDto.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new Response<>("Tool listed successfully",
                            savedToolDto,
                            HttpStatus.CREATED)
            );
        } catch (Exception e) {

            return ResponseEntity.status(500).body(
                    new Response<>(e.getMessage(),
                            null,
                            HttpStatus.INTERNAL_SERVER_ERROR)

            );
        }
    }

    @GetMapping
    public ResponseEntity<Response<List<ToolInventoryDto>>> getToolList() { // Fixed Return Type
        try {
            List<ToolInventory> all = toolInventoryService.getAll();
            List<ToolInventoryDto> dtoList = all.stream()
                    .map(item -> modelMapper.map(item, ToolInventoryDto.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(
                    new Response<>("Tools fetched successfully", dtoList, HttpStatus.OK)
            );
        } catch (ToolInventoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Response<>(e.getMessage(), null, HttpStatus.BAD_REQUEST)
            );
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<ToolInventoryDto>> updateTool(
            @PathVariable("id") UUID id,
            @ModelAttribute @Valid ToolInventoryDto inventoryDto) {

        ToolInventory updatedTool = toolInventoryService.update(id, inventoryDto);
        return ResponseEntity.ok(
                new Response<>("Tool updated successfully!",
                        modelMapper.map(updatedTool, ToolInventoryDto.class),
                        HttpStatus.OK)
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<ToolInventoryDto>> deleteTool(@PathVariable UUID id) {
        try {
            toolInventoryService.delete(id);
        } catch (ToolInventoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Response<>(e.getMessage(), null, HttpStatus.BAD_REQUEST)
            );
        }
        return ResponseEntity.ok(
                new Response<>(
                        "Tool id: " + id + " deleted successfully!",
                        null,
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ToolInventoryDto>> getToolById(@PathVariable UUID id) {
        ToolInventory tool = toolInventoryService.getById(id);

        if (tool != null) {
            ToolInventoryDto dto = modelMapper.map(tool, ToolInventoryDto.class);
            return ResponseEntity.ok(
                    new Response<>("Success", dto, HttpStatus.OK)
            );
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new Response<>("Tool with ID " + id + " not found", null, HttpStatus.NOT_FOUND)
        );
    }

    @GetMapping({"/{id}/tool-image"})
    public ResponseEntity<byte[]> getToolImage(@PathVariable UUID id) {
        byte[] image = toolInventoryService.getImge(id);
        if(image == null) {
            throw new ToolInventoryException("No image found for tool with ID " + id);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<Response<List<ToolInventoryDto>>> getToolsByUserEmail(@PathVariable String email) {
        List<ToolInventory> tools = toolInventoryService.getAll().stream()
                .filter(tool -> tool.getUserEmail().equalsIgnoreCase(email))
                .toList();

        List<ToolInventoryDto> toolDtos = tools.stream()
                .map(tool -> modelMapper.map(tool, ToolInventoryDto.class))
                .toList();

        return ResponseEntity.ok(
                new Response<>("Tools fetched successfully for user: " + email, toolDtos, HttpStatus.OK)
        );
    }
}
