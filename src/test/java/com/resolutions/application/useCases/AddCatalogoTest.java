package com.resolutions.application.useCases;

import com.resolutions.application.ports.out.DataPersist;
import com.resolutions.application.ports.out.LoggerLocal;
import com.resolutions.model.Catalogo;
import com.resolutions.model.ItemCatalogo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.resolutions.application.services.CatalogoValidator;
import com.resolutions.model.exceptions.BusinessValidationException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AddCatalogoTest {

    @Mock
    DataPersist<Catalogo> dataPersist;

    @Mock
    LoggerLocal logger;

    @Mock
    CatalogoValidator validator;

    @InjectMocks
    AddCatalogo addCatalogo;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        addCatalogo = new AddCatalogo(dataPersist, logger, validator);
    }

    @Test
    void run_should_set_fecha_and_insert_return_id() throws Exception {
        Catalogo cat = new Catalogo();
        cat.setCatDesc("desc");
        ItemCatalogo item = new ItemCatalogo(1, "item desc");
        cat.setItems(List.of(item));

        when(dataPersist.insert(any())).thenReturn(42);

        Integer id = addCatalogo.run(cat);

        assertEquals(42, id);
        assertNotNull(cat.getFecha(), "La fecha debe haberse seteado");
        verify(logger, atLeastOnce()).info(anyString());
        verify(dataPersist).insert(cat);
    }

    @Test
    void run_should_throw_exception_when_description_exists() {
        Catalogo cat = new Catalogo();
        cat.setCatDesc("existing_desc");

        doThrow(new BusinessValidationException("Ya existe un catálogo con la misma descripción.")).when(validator).validateDescription(cat);

        assertThrows(BusinessValidationException.class, () -> addCatalogo.run(cat));
    }
}