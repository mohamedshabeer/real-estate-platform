package com.ev.realestate.repository;

import com.ev.realestate.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByIsAvailableTrue();
    List<Property> findTop5ByOrderByViewsDesc();
}

