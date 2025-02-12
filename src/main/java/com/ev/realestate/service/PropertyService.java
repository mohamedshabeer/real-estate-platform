package com.ev.realestate.service;

import com.ev.realestate.model.Property;

import java.util.List;

public interface PropertyService {
    List<Property> getAllProperties();
    Property getPropertyById(Long id);
    Property addProperty(Property property);
    List<Property> getAvailableProperties();
    List<Property> getMostViewedProperties();
    Property applyDiscount(Long id, double discountPercentage);
    boolean deleteProperty(Long id);

}
