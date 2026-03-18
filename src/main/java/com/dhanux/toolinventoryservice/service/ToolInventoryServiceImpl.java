package com.dhanux.toolinventoryservice.service;

import com.dhanux.toolinventoryservice.model.ToolInventory;
import com.dhanux.toolinventoryservice.repository.ToolInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
