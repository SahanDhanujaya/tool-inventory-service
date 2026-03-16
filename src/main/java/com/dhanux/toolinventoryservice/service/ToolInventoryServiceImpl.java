package com.dhanux.toolinventoryservice.service;

import com.dhanux.toolinventoryservice.exception.GlobalExceptionHandler;
import com.dhanux.toolinventoryservice.exception.ToolInventoryException;
import com.dhanux.toolinventoryservice.model.ToolInventory;
import com.dhanux.toolinventoryservice.repository.ToolInventoryRepository;
import com.dhanux.toolinventoryservice.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.time.LocalDateTime;

/**
 * @author Dhanujaya(Dhanu)
 * @TimeStamp 16/03/2026 15:59
 * @ProjectDetails tool-inventory-service
 */

@Service
@RequiredArgsConstructor
public class ToolInventoryServiceImpl implements ToolInventoryService{
    private final ToolInventoryRepository toolInventoryRepository;

    @Override
    public ToolInventory  save(ToolInventory toolInventory) {
        toolInventory.setCreatedAt(LocalDateTime.now());
        toolInventory.setUpdatedAt(LocalDateTime.now());
        return toolInventoryRepository.save(toolInventory);
    }
}
