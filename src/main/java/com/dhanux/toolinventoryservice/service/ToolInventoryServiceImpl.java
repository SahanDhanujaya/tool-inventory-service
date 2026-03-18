package com.dhanux.toolinventoryservice.service;

import com.dhanux.toolinventoryservice.exception.ToolInventoryException;
import com.dhanux.toolinventoryservice.model.ToolInventory;
import com.dhanux.toolinventoryservice.repository.ToolInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<ToolInventory> getAll() {
        return toolInventoryRepository.findAll();
    }

    @Override
    public ToolInventory update(int id, ToolInventory incomingData) {
        return toolInventoryRepository.findById(id).map(existingTool -> {

            LocalDateTime originalCreationDate = existingTool.getCreatedAt();
            Integer originalOwner = existingTool.getUserId();

            existingTool.setCreatedAt(originalCreationDate);
            existingTool.setUserId(originalOwner);
            existingTool.setTitle(incomingData.getTitle());
            existingTool.setUpdatedAt(LocalDateTime.now());
            existingTool.setId(id);

            return toolInventoryRepository.save(existingTool);
        }).orElse(null);
    }

    @Override
    public void delete(int id) {
        if(toolInventoryRepository.existsById(id)) {
            toolInventoryRepository.deleteById(id);
        }     else {
            throw new ToolInventoryException("Tool id: " + id + "not found!");
        }
    }

    @Override
    public ToolInventory getById(int id) {
        Optional<ToolInventory> byId = toolInventoryRepository.findById(id);
        return byId.orElse(null);
    }
}
