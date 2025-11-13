package com.resolutions.application.ports.in;

import com.resolutions.model.Catalogo;

public interface ModifyCatalogoUseCase {
    void run(Catalogo catalogo) throws Exception;
}
