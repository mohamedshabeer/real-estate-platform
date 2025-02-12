package com.ev.realestate;

import com.ev.realestate.model.Property;
import com.ev.realestate.repository.PropertyRepository;
import com.ev.realestate.service.PropertyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PropertyControllerTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    @BeforeEach
    void setUp() {
        //TODO
    }

    @Test
    public void testGetAllProperties() {
        List<Property> properties = List.of(new Property(1L, "House 1", "Nice house", "NYC", 200000.0, true, 10, 0.0));
        Mockito.when(propertyRepository.findAll()).thenReturn(properties);

        List<Property> result = propertyService.getAllProperties();
        assertEquals(1, result.size());
        assertEquals("House 1", result.getFirst().getTitle());
    }
}
