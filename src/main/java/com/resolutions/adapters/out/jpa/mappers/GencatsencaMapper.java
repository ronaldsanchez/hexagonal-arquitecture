package com.resolutions.adapters.out.jpa.mappers;

import com.resolutions.adapters.out.jpa.entidades.Gencatsenca;
import com.resolutions.model.Catalogo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper (componentModel = "cdi",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface GencatsencaMapper {
    @Mapping(target = "fechCrea", source = "fecha")
    @Mapping(target = "fechMod", source = "fecha")
    Gencatsenca toResource(Catalogo catalogo);
}
