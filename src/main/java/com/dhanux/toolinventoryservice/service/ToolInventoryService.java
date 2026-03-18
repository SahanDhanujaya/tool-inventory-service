package com.dhanux.toolinventoryservice.service;

import com.dhanux.toolinventoryservice.dto.ToolInventoryDto;
import com.dhanux.toolinventoryservice.model.ToolInventory;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author Dhanujaya(Dhanu)
 * @TimeStamp 16/03/2026 15:59
 * @ProjectDetails tool-inventory-service
 */
public interface ToolInventoryService {
    ToolInventory save(ToolInventory toolInventory);
    List<ToolInventory> getAll();
    ToolInventory update(int id, ToolInventory inventoryDto);
    void delete(int id);
    ToolInventory getById(int id);
}
