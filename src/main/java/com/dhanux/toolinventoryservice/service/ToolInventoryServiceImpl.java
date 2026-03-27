package com.dhanux.toolinventoryservice.service;

import com.dhanux.toolinventoryservice.dto.ToolInventoryDto;
import com.dhanux.toolinventoryservice.exception.ToolInventoryException;
import com.dhanux.toolinventoryservice.model.ToolInventory;
import com.dhanux.toolinventoryservice.repository.ToolInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Dhanujaya(Dhanu)
 * @TimeStamp 16/03/2026 15:59
 * @ProjectDetails tool-inventory-service
 */

@Service
@RequiredArgsConstructor
public class ToolInventoryServiceImpl implements ToolInventoryService{
    private final ToolInventoryRepository toolInventoryRepository;
    @Value("${app.storage.path:uploads/tools}")
    private String toolStoragePath;
    private final ModelMapper modelMapper;

    @Override
    public ToolInventory  save(ToolInventoryDto dto) throws IOException {
        // 1. Map DTO to Entity (Image will be null initially)
        ToolInventory tool = modelMapper.map(dto, ToolInventory.class);

        // 2. Handle File Upload
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + dto.getImage().getOriginalFilename();
            Path path = Paths.get(toolStoragePath).resolve(fileName);

            Files.createDirectories(path.getParent());
            Files.copy(dto.getImage().getInputStream(), path);

            tool.setImage(fileName); // Save filename to entity
        }

        tool.setCreatedAt(LocalDateTime.now());
        tool.setUpdatedAt(LocalDateTime.now());
        return toolInventoryRepository.save(tool);
    }

    @Override
    public List<ToolInventory> getAll() {
        return toolInventoryRepository.findAll();
    }

    @Override
    public ToolInventory update(UUID id, ToolInventoryDto incomingDto) {
        return toolInventoryRepository.findById(id).map(existingTool -> {

            // 1. Handle New Image Upload (if provided)
            if (incomingDto.getImage() != null && !incomingDto.getImage().isEmpty()) {
                try {
                    // Delete the old file first if it exists
                    if (existingTool.getImage() != null) {
                        Path oldPath = Paths.get(toolStoragePath).resolve(existingTool.getImage());
                        Files.deleteIfExists(oldPath);
                    }

                    // Save the new file
                    String newFileName = UUID.randomUUID().toString() + "_" + incomingDto.getImage().getOriginalFilename();
                    Path newPath = Paths.get(toolStoragePath).resolve(newFileName);
                    Files.copy(incomingDto.getImage().getInputStream(), newPath);

                    // Update the filename in the entity
                    existingTool.setImage(newFileName);
                } catch (IOException e) {
                    throw new ToolInventoryException("Failed to update tool image: " + e.getMessage());
                }
            }

            // 2. Map all other fields from DTO to the existing Entity
            // We skip 'id', 'createdAt', and 'image' (already handled) to keep them stable
            modelMapper.typeMap(ToolInventoryDto.class, ToolInventory.class)
                    .addMappings(mapper -> {
                        mapper.skip(ToolInventory::setId);
                        mapper.skip(ToolInventory::setCreatedAt);
                        mapper.skip(ToolInventory::setImage);
                    });

            modelMapper.map(incomingDto, existingTool);

            // 3. Set standard metadata
            existingTool.setUpdatedAt(LocalDateTime.now());

            return toolInventoryRepository.save(existingTool);
        }).orElseThrow(() -> new ToolInventoryException("Tool id: " + id + " not found!"));
    }

    @Override
    public void delete(UUID id) {
        if(toolInventoryRepository.existsById(id)) {
            toolInventoryRepository.deleteById(id);
        }     else {
            throw new ToolInventoryException("Tool id: " + id + "not found!");
        }
    }

    @Override
    public ToolInventory getById(UUID id) {
        Optional<ToolInventory> byId = toolInventoryRepository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public byte[] getImge(UUID id) {
        ToolInventory tool = toolInventoryRepository.findById(id)
                .orElseThrow(() -> new ToolInventoryException("Tool with ID " + id + " not found"));

        if (tool.getImage() == null) {
            throw new ToolInventoryException("No image found for tool with ID " + id);
        }

        Path imagePath = Paths.get(toolStoragePath).resolve(tool.getImage());
        try {
            return Files.readAllBytes(imagePath);
        } catch (IOException e) {
            throw new ToolInventoryException("Failed to read image for tool with ID " + id + ": " + e.getMessage());
        }
    }

}
