package com.resolutions.application.useCases;

import com.resolutions.application.ports.in.GetCatalogoUseCase;
import com.resolutions.application.ports.out.DataPersist;
import com.resolutions.application.ports.out.LoggerLocal;
import com.resolutions.model.Catalogo;
import com.resolutions.model.PaginatedResponse;

import java.util.Optional;

public class GetCatalogo implements GetCatalogoUseCase {

    private final DataPersist<Catalogo> dataRepository;
    private final LoggerLocal log;

    public GetCatalogo(DataPersist<Catalogo> dataRepository, LoggerLocal log) {
        this.dataRepository = dataRepository;
        this.log = log;
    }

    @Override
    public Optional<Catalogo> run(Integer id) {
        log.info("Obteniendo catálogo con id: " + id);
        Catalogo catalogo = dataRepository.get(id);
        return Optional.ofNullable(catalogo);
    }

    @Override
    public Optional<Catalogo> findByDescription(String description) {
        log.info("Buscando catálogo con descripción: " + description);
        return dataRepository.findByDescription(description);
    }

    @Override
    public PaginatedResponse<Catalogo> findAllPaginated(int page, int size) {
        log.info("Obteniendo catálogos paginados - página: " + page + ", tamaño: " + size);
        return dataRepository.findAll(page, size);
    }
}

