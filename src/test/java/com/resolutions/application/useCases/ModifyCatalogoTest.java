package com.resolutions.application.useCases;

import com.resolutions.application.ports.out.DataPersist;
import com.resolutions.application.ports.out.LoggerLocal;
import com.resolutions.model.Catalogo;
import com.resolutions.application.services.CatalogoValidator;
import com.resolutions.model.exceptions.BusinessValidationException;
import com.resolutions.model.ItemCatalogo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ModifyCatalogoTest {

    @Mock
    DataPersist<Catalogo> dataPersist;

    @Mock
    LoggerLocal logger;

    @Mock
    CatalogoValidator validator;

    @InjectMocks
    ModifyCatalogo modifyCatalogo;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        modifyCatalogo = new ModifyCatalogo(dataPersist, logger, validator);
    }

    @Test
    void modifyCatalogo_should_call_update() {
        Catalogo cat = new Catalogo();
        cat.setCodCat(1);
        cat.setCatDesc("desc");
        ItemCatalogo item = new ItemCatalogo(1, "item desc");
        cat.setItems(List.of(item));

        modifyCatalogo.run(cat);

        verify(dataPersist).update(cat);
        verify(logger).info(any());
    }

    @Test
    void modifyCatalogo_should_throw_exception_when_description_exists() {
        Catalogo catToModify = new Catalogo();
        catToModify.setCodCat(1);
        catToModify.setCatDesc("existing_desc");

        doThrow(new BusinessValidationException("Ya existe un catálogo con la misma descripción.")).when(validator).validateDescription(catToModify);

        assertThrows(BusinessValidationException.class, () -> modifyCatalogo.run(catToModify));
    }
}