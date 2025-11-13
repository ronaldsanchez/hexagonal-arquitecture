package com.resolutions.adapters;

import com.resolutions.application.ports.out.LoggerLocal;
import com.resolutions.model.Catalogo;
import com.resolutions.application.ports.in.AddCatalogoUseCase;
import com.resolutions.application.ports.in.GetCatalogoUseCase;
import com.resolutions.application.ports.out.DataPersist;
import com.resolutions.application.useCases.AddCatalogo;
import com.resolutions.application.useCases.GetCatalogo;
import com.resolutions.application.useCases.ModifyCatalogo;
import com.resolutions.application.ports.in.ModifyCatalogoUseCase;
import com.resolutions.application.services.CatalogoValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;


@ApplicationScoped
public class QuarkusAppConfig {

    @Inject
    Instance< DataPersist<Catalogo> > catRepository;
    @Inject
    Instance< LoggerLocal > log;

    @Produces
    @ApplicationScoped
    public AddCatalogoUseCase getAddCatalogo() {
        return new AddCatalogo(catRepository.get(),log.get(), getCatalogoValidator());
    }

    @Produces
    @ApplicationScoped
    public ModifyCatalogoUseCase getModifyCatalogo() {
        return new ModifyCatalogo(catRepository.get(),log.get(), getCatalogoValidator());
    }

    @Produces
    @ApplicationScoped
    public GetCatalogoUseCase getGetCatalogo() {
        return new GetCatalogo(catRepository.get(), log.get());
    }

    @Produces
    @ApplicationScoped
    public CatalogoValidator getCatalogoValidator() {
        return new CatalogoValidator(catRepository.get());
    }


}
