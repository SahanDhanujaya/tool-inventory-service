package com.dhanux.toolinventoryservice.service;

import com.dhanux.toolinventoryservice.model.ToolInventory;
import com.dhanux.toolinventoryservice.response.Response;
import org.springframework.http.ResponseEntity;

/**
 * @author Dhanujaya(Dhanu)
 * @TimeStamp 16/03/2026 15:59
 * @ProjectDetails tool-inventory-service
 */
public interface ToolInventoryService {
    ToolInventory save(ToolInventory toolInventory);
}
