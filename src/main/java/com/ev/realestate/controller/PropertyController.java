package com.ev.realestate.controller;

import com.ev.realestate.model.Property;
import com.ev.realestate.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @GetMapping("/{id}")
    public Property getPropertyById(@PathVariable Long id) {
        return propertyService.getPropertyById(id);
    }

    @PostMapping
    public ResponseEntity<Property> addProperty(@RequestBody Property property) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.addProperty(property));
    }

    @GetMapping("/available")
    public List<Property> getAvailableProperties() {
        return propertyService.getAvailableProperties();
    }

    @GetMapping("/most-viewed")
    public List<Property> getMostViewedProperties() {
        return propertyService.getMostViewedProperties();
    }

    @PatchMapping("/{id}/discount/{discountPercentage}")
    public ResponseEntity<Property> applyDiscount(@PathVariable Long id, @PathVariable double discountPercentage) {
        return ResponseEntity.ok(propertyService.applyDiscount(id, discountPercentage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProperty(@PathVariable Long id) {
        boolean deleted = propertyService.deleteProperty(id);
        if (deleted) {
            return ResponseEntity.ok("Property deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property not found");
        }
    }
}

