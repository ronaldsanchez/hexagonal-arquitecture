package com.resolutions.adapters.in.rest;

import com.resolutions.application.ports.in.AddCatalogoUseCase;
import com.resolutions.model.Catalogo;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CatalogoResourcesTest {

    @Mock
    AddCatalogoUseCase addCatalogo;

    @Mock
    Logger logger;

    @Mock
    UriInfo uriInfo;

    CatalogoResources resources;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        resources = new CatalogoResources(addCatalogo, logger);
    }

    @Test
    void crearCatalogo_should_return_created_response() throws Exception {
        Catalogo cat = new Catalogo();
        when(addCatalogo.run(any())).thenReturn(7);

        UriBuilder builder = UriBuilder.fromPath("/api/catalogos");
        when(uriInfo.getAbsolutePathBuilder()).thenReturn(builder);

        var response = resources.crearCatalogo(cat, uriInfo);

        assertEquals(201, response.getStatus());
        assertTrue(response.getLocation().toString().contains("/api/catalogos/7"));
        verify(logger).debug(contains("Se creó catálogo"));
    }
}

