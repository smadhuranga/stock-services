package com.stock.stockservice.controller;

import com.stock.stockservice.model.StockItem;
import com.stock.stockservice.repository.StockItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stock")
public class StockItemController {
    @Autowired
    private StockItemRepository stockItemRepository;

    @GetMapping
    public List<StockItem> getAllItems() {
        return stockItemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockItem> getItemById(@PathVariable Long id) {
        Optional<StockItem> item = stockItemRepository.findById(id);
        return item.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StockItem> createItem(@RequestBody StockItem item) {
        StockItem savedItem = stockItemRepository.save(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockItem> updateItem(@PathVariable Long id, @RequestBody StockItem itemDetails) {
        Optional<StockItem> itemOptional = stockItemRepository.findById(id);
        
        if (itemOptional.isPresent()) {
            StockItem itemToUpdate = itemOptional.get();
            itemToUpdate.setName(itemDetails.getName());
            itemToUpdate.setDescription(itemDetails.getDescription());
            itemToUpdate.setQuantity(itemDetails.getQuantity());
            itemToUpdate.setPrice(itemDetails.getPrice());
            
            StockItem updatedItem = stockItemRepository.save(itemToUpdate);
            return ResponseEntity.ok(updatedItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (stockItemRepository.existsById(id)) {
            stockItemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
