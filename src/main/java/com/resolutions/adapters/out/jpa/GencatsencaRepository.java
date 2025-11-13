package com.resolutions.adapters.out.jpa;

import com.resolutions.adapters.out.jpa.entidades.Gencatsenca;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class GencatsencaRepository implements PanacheRepository<Gencatsenca> {

    public Optional<Gencatsenca> findByCatDesc(String catDesc){
        return find("catDesc", catDesc).firstResultOptional();
    }

}
