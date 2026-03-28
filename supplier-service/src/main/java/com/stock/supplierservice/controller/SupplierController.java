package com.stock.supplierservice.controller;

import com.stock.supplierservice.model.Supplier;
import com.stock.supplierservice.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    @Autowired
    private SupplierRepository supplierRepository;
    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable String id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        return supplier.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        Supplier savedSupplier = supplierRepository.save(supplier);
        return new ResponseEntity<>(savedSupplier, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable String id, @RequestBody Supplier supplierDetails) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        
        if (optionalSupplier.isPresent()) {
            Supplier existingSupplier = optionalSupplier.get();
            existingSupplier.setName(supplierDetails.getName());
            existingSupplier.setContactEmail(supplierDetails.getContactEmail());
            existingSupplier.setPhone(supplierDetails.getPhone());
            existingSupplier.setAddress(supplierDetails.getAddress());
            
            Supplier updatedSupplier = supplierRepository.save(existingSupplier);
            return ResponseEntity.ok(updatedSupplier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable String id) {
        if (supplierRepository.existsById(id)) {
            supplierRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
