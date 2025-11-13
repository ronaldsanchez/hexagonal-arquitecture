package com.resolutions.application.ports.in;

import com.resolutions.model.Catalogo;
import com.resolutions.model.PaginatedResponse;

import java.util.Optional;

public interface GetCatalogoUseCase {
    Optional<Catalogo> run(Integer id);
    Optional<Catalogo> findByDescription(String description);
    PaginatedResponse<Catalogo> findAllPaginated(int page, int size);
}
