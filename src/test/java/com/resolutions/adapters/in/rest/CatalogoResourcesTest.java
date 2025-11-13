package com.resolutions.adapters.in.rest;

import com.resolutions.application.ports.in.AddCatalogoUseCase;
import com.resolutions.application.ports.in.GetCatalogoUseCase;
import com.resolutions.application.ports.in.ModifyCatalogoUseCase;
import com.resolutions.model.Catalogo;
import com.resolutions.model.PaginatedResponse;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

public class CatalogoResourcesTest {

    @Mock
    AddCatalogoUseCase addCatalogo;

    @Mock
    ModifyCatalogoUseCase modifyCatalogo;

    @Mock
    GetCatalogoUseCase getCatalogo;

    @Mock
    Logger logger;

    @Mock
    UriInfo uriInfo;

    CatalogoResources resources;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        resources = new CatalogoResources(addCatalogo, modifyCatalogo, getCatalogo, logger);
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

    @Test
    void modificarCatalogo_should_return_noContent_response() throws Exception {
        Catalogo cat = new Catalogo();

        var response = resources.modificarCatalogo(1, cat);

        assertEquals(204, response.getStatus());
        verify(modifyCatalogo).run(cat);
    }

    @Test
    void obtenerCatalogo_should_return_ok_when_found() {
        Integer id = 1;
        Catalogo cat = new Catalogo();
        cat.setCodCat(id);
        cat.setCatDesc("Test Catalog");
        cat.setFecha(Instant.now());

        when(getCatalogo.run(id)).thenReturn(Optional.of(cat));

        var response = resources.obtenerCatalogo(id);

        assertEquals(200, response.getStatus());
        assertEquals(cat, response.getEntity());
        verify(getCatalogo).run(id);
        verify(logger).debug(contains("Catálogo encontrado"));
    }

    @Test
    void obtenerCatalogo_should_return_notFound_when_not_found() {
        Integer id = 99;

        when(getCatalogo.run(id)).thenReturn(Optional.empty());

        var response = resources.obtenerCatalogo(id);

        assertEquals(404, response.getStatus());
        verify(getCatalogo).run(id);
        verify(logger).debug(contains("Catálogo no encontrado"));
    }

    @Test
    void buscarCatalogoPorDescripcion_should_return_ok_when_found() {
        String descripcion = "Test Description";
        Catalogo cat = new Catalogo();
        cat.setCodCat(1);
        cat.setCatDesc(descripcion);
        cat.setFecha(Instant.now());

        when(getCatalogo.findByDescription(descripcion)).thenReturn(Optional.of(cat));

        var response = resources.buscarCatalogoPorDescripcion(descripcion);

        assertEquals(200, response.getStatus());
        assertEquals(cat, response.getEntity());
        verify(getCatalogo).findByDescription(descripcion);
        verify(logger).debug(contains("Catálogo encontrado"));
    }

    @Test
    void buscarCatalogoPorDescripcion_should_return_notFound_when_not_found() {
        String descripcion = "Non-existent";

        when(getCatalogo.findByDescription(descripcion)).thenReturn(Optional.empty());

        var response = resources.buscarCatalogoPorDescripcion(descripcion);

        assertEquals(404, response.getStatus());
        verify(getCatalogo).findByDescription(descripcion);
    }

    @Test
    void buscarCatalogoPorDescripcion_should_return_badRequest_when_empty_description() {
        var response = resources.buscarCatalogoPorDescripcion("");

        assertEquals(400, response.getStatus());
        verify(logger).debug(contains("Descripción vacía"));
    }

    @Test
    void buscarCatalogoPorDescripcion_should_return_badRequest_when_null_description() {
        var response = resources.buscarCatalogoPorDescripcion(null);

        assertEquals(400, response.getStatus());
        verify(logger).debug(contains("Descripción vacía"));
    }

    @Test
    void listarCatalogosPaginado_should_return_paginated_response() {
        Catalogo cat1 = new Catalogo();
        cat1.setCodCat(1);
        cat1.setCatDesc("Catalog 1");
        cat1.setFecha(Instant.now());

        Catalogo cat2 = new Catalogo();
        cat2.setCodCat(2);
        cat2.setCatDesc("Catalog 2");
        cat2.setFecha(Instant.now());

        PaginatedResponse<Catalogo> paginatedResponse = new PaginatedResponse<>(
                List.of(cat1, cat2), 0, 10, 25
        );

        when(getCatalogo.findAllPaginated(0, 10)).thenReturn(paginatedResponse);

        var response = resources.listarCatalogosPaginado(null, null);

        assertEquals(200, response.getStatus());
        PaginatedResponse<Catalogo> result = response.getEntity();
        assertEquals(2, result.getContent().size());
        assertEquals(0, result.getPage());
        assertEquals(10, result.getSize());
        assertEquals(25, result.getTotalElements());
        verify(getCatalogo).findAllPaginated(0, 10);
    }

    @Test
    void listarCatalogosPaginado_should_use_custom_page_and_size() {
        PaginatedResponse<Catalogo> paginatedResponse = new PaginatedResponse<>(
                List.of(), 1, 15, 0
        );

        when(getCatalogo.findAllPaginated(1, 15)).thenReturn(paginatedResponse);

        var response = resources.listarCatalogosPaginado(1, 15);

        assertEquals(200, response.getStatus());
        verify(getCatalogo).findAllPaginated(1, 15);
    }

    @Test
    void listarCatalogosPaginado_should_default_size_to_10() {
        PaginatedResponse<Catalogo> paginatedResponse = new PaginatedResponse<>(
                List.of(), 0, 10, 0
        );

        when(getCatalogo.findAllPaginated(0, 10)).thenReturn(paginatedResponse);

        var response = resources.listarCatalogosPaginado(null, null);

        assertEquals(200, response.getStatus());
        verify(getCatalogo).findAllPaginated(0, 10);
    }

    @Test
    void listarCatalogosPaginado_should_handle_exception() {
        when(getCatalogo.findAllPaginated(0, 10)).thenThrow(new RuntimeException("Database error"));

        var response = resources.listarCatalogosPaginado(null, null);

        assertEquals(400, response.getStatus());
        verify(logger).error(contains("Error al obtener catálogos paginados"));
    }
}

