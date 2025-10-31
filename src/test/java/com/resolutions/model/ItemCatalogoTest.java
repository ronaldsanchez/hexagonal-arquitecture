package com.resolutions.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemCatalogoTest {

    @Test
    void record_should_hold_values() {
        ItemCatalogo item = new ItemCatalogo(5, "desc");

        assertEquals(5, item.codCor());
        assertEquals("desc", item.cirDesc());
    }
}

