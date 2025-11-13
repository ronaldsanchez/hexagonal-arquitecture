package com.resolutions.application.useCases;

import com.resolutions.application.ports.in.ModifyCatalogoUseCase;
import com.resolutions.application.ports.out.DataPersist;
import com.resolutions.model.Catalogo;
import com.resolutions.application.ports.out.LoggerLocal;
import com.resolutions.application.services.CatalogoValidator;
import com.resolutions.model.exceptions.BusinessValidationException;

import java.time.Instant;

public class ModifyCatalogo implements ModifyCatalogoUseCase {

    private final DataPersist<Catalogo> dataRepository;
    private final LoggerLocal log;
    private final CatalogoValidator validator;

    public ModifyCatalogo(DataPersist<Catalogo> dataRepository, LoggerLocal log, CatalogoValidator validator) {
        this.dataRepository = dataRepository;
        this.log = log;
        this.validator = validator;
    }

    @Override
    public void run(Catalogo catalogo) throws BusinessValidationException {
        validator.validateDescription(catalogo);
        catalogo.setFecha(Instant.now());
        log.info("Modificando catalogo: " + catalogo.getCodCat());
        dataRepository.update(catalogo);
    }
}
