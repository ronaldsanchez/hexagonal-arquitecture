package com.resolutions.application.useCases;

import com.resolutions.application.ports.out.DataPersist;
import com.resolutions.application.ports.out.LoggerLocal;
import com.resolutions.model.Catalogo;
import com.resolutions.model.ItemCatalogo;
import com.resolutions.model.PaginatedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GetCatalogoTest {

    @Mock
    DataPersist<Catalogo> dataPersist;

    @Mock
    LoggerLocal logger;

    @InjectMocks
    GetCatalogo getCatalogo;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        getCatalogo = new GetCatalogo(dataPersist, logger);
    }

    @Test
    void run_should_return_catalogo_when_found() {
        Integer id = 1;
        Catalogo cat = new Catalogo();
        cat.setCodCat(id);
        cat.setCatDesc("Test Catalog");
        cat.setFecha(Instant.now());
        ItemCatalogo item = new ItemCatalogo(1, "item desc");
        cat.setItems(List.of(item));

        when(dataPersist.get(id)).thenReturn(cat);

        Optional<Catalogo> result = getCatalogo.run(id);

        assertTrue(result.isPresent());
        assertEquals(cat, result.get());
        assertEquals("Test Catalog", result.get().getCatDesc());
        verify(logger).info(anyString());
        verify(dataPersist).get(id);
    }

    @Test
    void run_should_return_empty_when_catalogo_not_found() {
        Integer id = 99;

        when(dataPersist.get(id)).thenReturn(null);

        Optional<Catalogo> result = getCatalogo.run(id);

        assertFalse(result.isPresent());
        verify(logger).info(anyString());
        verify(dataPersist).get(id);
    }

    @Test
    void findByDescription_should_return_catalogo_when_found() {
        String description = "Test Description";
        Catalogo cat = new Catalogo();
        cat.setCodCat(1);
        cat.setCatDesc(description);
        cat.setFecha(Instant.now());
        ItemCatalogo item = new ItemCatalogo(1, "item desc");
        cat.setItems(List.of(item));

        when(dataPersist.findByDescription(description)).thenReturn(Optional.of(cat));

        Optional<Catalogo> result = getCatalogo.findByDescription(description);

        assertTrue(result.isPresent());
        assertEquals(cat, result.get());
        assertEquals(description, result.get().getCatDesc());
        verify(logger).info(anyString());
        verify(dataPersist).findByDescription(description);
    }

    @Test
    void findByDescription_should_return_empty_when_description_not_found() {
        String description = "Non-existent Description";

        when(dataPersist.findByDescription(description)).thenReturn(Optional.empty());

        Optional<Catalogo> result = getCatalogo.findByDescription(description);

        assertFalse(result.isPresent());
        verify(logger).info(anyString());
        verify(dataPersist).findByDescription(description);
    }

    @Test
    void run_should_log_info_with_id() {
        Integer id = 5;
        when(dataPersist.get(id)).thenReturn(null);

        getCatalogo.run(id);

        verify(logger).info("Obteniendo catálogo con id: " + id);
    }

    @Test
    void findByDescription_should_log_info_with_description() {
        String description = "Test Description";
        when(dataPersist.findByDescription(description)).thenReturn(Optional.empty());

        getCatalogo.findByDescription(description);

        verify(logger).info("Buscando catálogo con descripción: " + description);
    }

    @Test
    void findAllPaginated_should_return_paginated_response() {
        int page = 0;
        int size = 10;

        Catalogo cat1 = new Catalogo();
        cat1.setCodCat(1);
        cat1.setCatDesc("Catalog 1");
        cat1.setFecha(Instant.now());

        Catalogo cat2 = new Catalogo();
        cat2.setCodCat(2);
        cat2.setCatDesc("Catalog 2");
        cat2.setFecha(Instant.now());

        List<Catalogo> catalogs = List.of(cat1, cat2);
        PaginatedResponse<Catalogo> paginatedResponse = new PaginatedResponse<>(catalogs, page, size, 20);

        when(dataPersist.findAll(page, size)).thenReturn(paginatedResponse);

        PaginatedResponse<Catalogo> result = getCatalogo.findAllPaginated(page, size);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(page, result.getPage());
        assertEquals(size, result.getSize());
        assertEquals(20, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
        verify(logger).info(anyString());
        verify(dataPersist).findAll(page, size);
    }

    @Test
    void findAllPaginated_should_log_with_page_and_size() {
        int page = 1;
        int size = 15;

        PaginatedResponse<Catalogo> paginatedResponse = new PaginatedResponse<>(List.of(), page, size, 0);
        when(dataPersist.findAll(page, size)).thenReturn(paginatedResponse);

        getCatalogo.findAllPaginated(page, size);

        verify(logger).info("Obteniendo catálogos paginados - página: " + page + ", tamaño: " + size);
    }
}

