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

import java.util.*;
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
    public ResponseEntity<List<ToolInventoryDto>> getToolList(){
        try {
            List<ToolInventory> all = toolInventoryService.getAll();
            return ResponseEntity.status(200).body(
                    new Response<>(
                            "Tools fetch successfully",
                            all.stream().map(
                                    item -> modelMapper.map(item, ToolInventoryDto.class)
                            ).collect(Collectors.toList()),
                            HttpStatus.ACCEPTED
                    ).getStore()
            );
        } catch (ToolInventoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Response<List<ToolInventoryDto>>(
                            e.getMessage(),
                            null,
                            HttpStatus.BAD_REQUEST
                    ).getStore()
            );
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<ToolInventoryDto>> updateTool (
            @PathVariable("id") int id,
            @RequestBody @Valid ToolInventoryDto inventoryDto)
    {
        try {
            ToolInventory toolToUpdate = modelMapper.map(inventoryDto, ToolInventory.class);
            toolToUpdate.setId(id);

            ToolInventory updatedTool = toolInventoryService.update(id, toolToUpdate);

            if (updatedTool != null) {
                return ResponseEntity.ok(
                        new Response<>(
                                "Tool id: " + id + " updated successfully!",
                                modelMapper.map(updatedTool, ToolInventoryDto.class),
                                HttpStatus.OK
                        )
                );
            }
        } catch (ToolInventoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Response<>(e.getMessage(), null, HttpStatus.BAD_REQUEST)
            );
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new Response<>("Tool id: " + id + " not found!", null, HttpStatus.NOT_FOUND)
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<ToolInventoryDto>> deleteTool(@PathVariable int id) {
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

    @GetMapping("{id}")
    public ResponseEntity<Response<ToolInventoryDto>> getToolById(@PathVariable int id) {
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
}
