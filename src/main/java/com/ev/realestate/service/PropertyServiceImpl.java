package com.ev.realestate.service;

import com.ev.realestate.exception.ResourceNotFoundException;
import com.ev.realestate.model.Property;
import com.ev.realestate.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Property getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
        property.setViews(property.getViews() + 1);
        propertyRepository.save(property);
        return property;
    }

    public Property addProperty(Property property) {
        return propertyRepository.save(property);
    }

    public List<Property> getAvailableProperties() {
        return propertyRepository.findByIsAvailableTrue();
    }

    public List<Property> getMostViewedProperties() {
        return propertyRepository.findTop5ByOrderByViewsDesc();
    }

    public Property applyDiscount(Long id, double discountPercentage) {
        Property property = getPropertyById(id);
        property.setDiscount(property.getPrice() * discountPercentage / 100);
        return propertyRepository.save(property);
    }

    @Override
    public boolean deleteProperty(Long id) {
        if (propertyRepository.existsById(id)) {
            propertyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
