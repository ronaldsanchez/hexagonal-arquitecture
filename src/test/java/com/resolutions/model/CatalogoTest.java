package com.resolutions.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogoTest {

    @Test
    void getters_and_setters_should_work() {
        Catalogo c = new Catalogo();
        c.setCodCat(1);
        c.setCatDesc("descripcion");
        c.setCodUsr("usr");
        Instant now = Instant.now();
        c.setFecha(now);

        assertEquals(1, c.getCodCat());
        assertEquals("descripcion", c.getCatDesc());
        assertEquals("usr", c.getCodUsr());
        assertEquals(now, c.getFecha());
    }
}

