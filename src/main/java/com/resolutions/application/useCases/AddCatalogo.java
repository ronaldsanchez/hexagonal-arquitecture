package com.resolutions.application.useCases;

import com.resolutions.application.ports.out.LoggerLocal;
import com.resolutions.model.Catalogo;
import com.resolutions.application.ports.in.AddCatalogoUseCase;
import com.resolutions.application.ports.out.DataPersist;

import java.time.Instant;

import com.resolutions.application.services.CatalogoValidator;

public class AddCatalogo implements AddCatalogoUseCase {

    private final DataPersist<Catalogo> dataRepository;
    private final LoggerLocal log;
    private final CatalogoValidator validator;

    public AddCatalogo(DataPersist<Catalogo> dataRepository, LoggerLocal log, CatalogoValidator validator) {
        this.dataRepository = dataRepository;
        this.log = log;
        this.validator = validator;
    }

    public Integer run(Catalogo catalogo) throws Exception {
        validator.validateDescription(catalogo);
        crearRegistro(catalogo);
        return dataRepository.insert(catalogo);
    }

    private void crearRegistro(Catalogo catalogo){
        catalogo.setFecha(Instant.now());
        log.info(Instant.now().toString());
    }

}
