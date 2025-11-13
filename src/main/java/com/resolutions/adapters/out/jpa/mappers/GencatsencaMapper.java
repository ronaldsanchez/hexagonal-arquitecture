package com.resolutions.adapters.out.jpa.mappers;

import com.resolutions.adapters.out.jpa.entidades.Gencatsenca;
import com.resolutions.adapters.out.jpa.entidades.Gencatsdeta;
import com.resolutions.adapters.out.jpa.entidades.GencatsdetaPK;
import com.resolutions.model.Catalogo;
import com.resolutions.model.ItemCatalogo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GencatsencaMapper {
    @Mapping(target = "fechCrea", source = "fecha")
    @Mapping(target = "fechMod", source = "fecha")
    @Mapping(target = "items", source = "items")
    Gencatsenca toResource(Catalogo catalogo);

    @Mapping(target = "fechMod", source = "fecha")
    @Mapping(target = "fechCrea", ignore = true)
    void updateFromDto(Catalogo catalogo, @MappingTarget Gencatsenca gencatsenca);

    @Mapping(target = "fecha", source = "fechMod")
    @Mapping(target = "items", source = "items")
    Catalogo toDomain(Gencatsenca gencatsenca);

    @Mapping(target = "gencatsdetaPK.codCor", source = "codCor")
    @Mapping(target = "gencatsdetaPK.codCat", ignore = true)
    @Mapping(target = "corDesc", source = "corDesc")
    @Mapping(target = "gencatsenca", ignore = true)
    @Mapping(target = "camposAudit", ignore = true)
    Gencatsdeta toDetailResource(ItemCatalogo itemCatalogo);

    @Mapping(target = "codCor", source = "gencatsdetaPK.codCor")
    @Mapping(target = "corDesc", source = "corDesc")
    ItemCatalogo toDetailDomain(Gencatsdeta gencatsdeta);

    default GencatsdetaPK map(ItemCatalogo itemCatalogo) {
        if (itemCatalogo == null) {
            return null;
        }
        GencatsdetaPK pk = new GencatsdetaPK();
        pk.setCodCor(itemCatalogo.codCor());
        return pk;
    }

    @AfterMapping
    default void linkItems(@MappingTarget Gencatsenca gencatsenca) {
        if (gencatsenca.getItems() != null) {
            gencatsenca.getItems().forEach(item -> item.setGencatsenca(gencatsenca));
        }
    }


}
