package com.resolutions.application.services;

import com.resolutions.application.ports.out.DataPersist;
import com.resolutions.model.Catalogo;
import com.resolutions.model.exceptions.BusinessValidationException;

public class CatalogoValidator {

    private final DataPersist<Catalogo> dataRepository;

    public CatalogoValidator(DataPersist<Catalogo> dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void validateDescription(Catalogo catalogo) {
        dataRepository.findByDescription(catalogo.getCatDesc()).ifPresent(existing -> {
            if (catalogo.getCodCat() == null) { // Add operation
                throw new BusinessValidationException("Ya existe un catálogo con la misma descripción.");
            } else { // Modify operation
                if (!existing.getCodCat().equals(catalogo.getCodCat())) {
                    throw new BusinessValidationException("Ya existe un catálogo con la misma descripción.");
                }
            }
        });
    }
}
