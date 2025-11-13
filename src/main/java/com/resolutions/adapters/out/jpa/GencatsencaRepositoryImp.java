package com.resolutions.adapters.out.jpa;

import com.resolutions.adapters.out.jpa.entidades.Gencatsenca;
import com.resolutions.adapters.out.jpa.mappers.GencatsencaMapper;
import com.resolutions.model.Catalogo;
import com.resolutions.model.PaginatedResponse;
import com.resolutions.application.ports.out.DataPersist;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class GencatsencaRepositoryImp implements DataPersist<Catalogo> {
    @Inject
    GencatsencaRepository gencatsencaRepo;

    @Inject
    GencatsencaMapper gencatsencaMapper;

    @Override
    public Integer insert(Catalogo dto) {
        Gencatsenca gencatsenca = gencatsencaMapper.toResource(dto);
        gencatsencaRepo.persist(gencatsenca);
        return gencatsenca.getCodCat();
    }

    @Override
    public void update(Catalogo dto) {
    
    }

    @Override
    public void delete(Catalogo dto) {

    }

    @Override
    public Catalogo get(Integer cod) {
        return new Catalogo();
    }

    @Override
    public Optional<Catalogo> findByDescription(String description) {
        return gencatsencaRepo.findByCatDesc(description)
                .map(gencatsencaMapper::toDomain);
    }

    @Override
    public PaginatedResponse<Catalogo> findAll(int page, int size) {
        long total = gencatsencaRepo.count();
        List<Gencatsenca> items = gencatsencaRepo.findAll()
                .page(page, size)
                .list();
        
        List<Catalogo> catalogs = items.stream()
                .map(gencatsencaMapper::toDomain)
                .collect(Collectors.toList());
        
        return new PaginatedResponse<>(catalogs, page, size, total);
    }
}
