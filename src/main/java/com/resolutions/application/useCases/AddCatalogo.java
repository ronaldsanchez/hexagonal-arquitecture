package com.resolutions.application.useCases;

import com.resolutions.application.ports.out.LoggerLocal;
import com.resolutions.model.Catalogo;
import com.resolutions.application.ports.in.AddCatalogoUseCase;
import com.resolutions.application.ports.out.DataPersist;

import java.time.Instant;

public class AddCatalogo implements AddCatalogoUseCase {

    private final DataPersist<Catalogo> dataRepository;
    private final LoggerLocal log;

    public AddCatalogo(DataPersist<Catalogo> dataRepository, LoggerLocal log) {
        this.dataRepository = dataRepository;
        this.log = log;
    }

    public Integer run(Catalogo catalogo) throws Exception {
        validarDatos(catalogo);
        crearRegistro(catalogo);
        return dataRepository.insert(catalogo);
    }

    private void validarDatos(Catalogo catalogo) {

    }

    private void crearRegistro(Catalogo catalogo){
        catalogo.setFecha(Instant.now());
        log.info(Instant.now().toString());
    }

}
