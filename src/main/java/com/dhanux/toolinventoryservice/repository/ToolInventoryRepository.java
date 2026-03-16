package com.dhanux.toolinventoryservice.repository;

import com.dhanux.toolinventoryservice.model.ToolInventory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dhanujaya(Dhanu)
 * @TimeStamp 16/03/2026 16:19
 * @ProjectDetails tool-inventory-service
 */
public interface ToolInventoryRepository extends JpaRepository<ToolInventory, Integer> {
}
