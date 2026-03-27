package com.dhanux.toolinventoryservice.service;

import com.dhanux.toolinventoryservice.dto.ToolInventoryDto;
import com.dhanux.toolinventoryservice.model.ToolInventory;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author Dhanujaya(Dhanu)
 * @TimeStamp 16/03/2026 15:59
 * @ProjectDetails tool-inventory-service
 */
public interface ToolInventoryService {
    ToolInventory save(ToolInventoryDto dto) throws IOException;
    List<ToolInventory> getAll();
    ToolInventory update(UUID id, ToolInventoryDto inventoryDto);
    void delete(UUID id);
    ToolInventory getById(UUID id);
    byte[] getImge(UUID id);
}
