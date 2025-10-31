package com.resolutions.application.useCases;

import com.resolutions.application.ports.out.DataPersist;
import com.resolutions.application.ports.out.LoggerLocal;
import com.resolutions.model.Catalogo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AddCatalogoTest {

    @Mock
    DataPersist<Catalogo> dataPersist;

    @Mock
    LoggerLocal logger;

    @InjectMocks
    AddCatalogo addCatalogo;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void run_should_set_fecha_and_insert_return_id() throws Exception {
        Catalogo cat = new Catalogo();
        cat.setCatDesc("desc");

        when(dataPersist.insert(any())).thenReturn(42);

        Integer id = addCatalogo.run(cat);

        assertEquals(42, id);
        assertNotNull(cat.getFecha(), "La fecha debe haberse seteado");
        verify(logger, atLeastOnce()).info(anyString());
        verify(dataPersist).insert(cat);
    }
}

