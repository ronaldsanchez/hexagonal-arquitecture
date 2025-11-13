package com.resolutions.adapters.in.rest;

import com.resolutions.application.ports.in.AddCatalogoUseCase;
import com.resolutions.application.ports.in.GetCatalogoUseCase;
import com.resolutions.application.ports.in.ModifyCatalogoUseCase;
import com.resolutions.model.Catalogo;
import com.resolutions.model.PaginatedResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;


@Path("/api/catalogos")
public class CatalogoResources {

    private final AddCatalogoUseCase addCatalogo;
    private final ModifyCatalogoUseCase modifyCatalogo;
    private final GetCatalogoUseCase getCatalogo;
    private final Logger logger;

    public CatalogoResources(AddCatalogoUseCase addCatalogo, ModifyCatalogoUseCase modifyCatalogo, GetCatalogoUseCase getCatalogo, Logger logger) {
        this.addCatalogo = addCatalogo;
        this.modifyCatalogo = modifyCatalogo;
        this.getCatalogo = getCatalogo;
        this.logger = logger;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public RestResponse<Void> crearCatalogo(Catalogo catalogo, @Context UriInfo uriInfo) {
        try {
            Integer id = addCatalogo.run(catalogo);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(id.toString());
            logger.debug("\nSe creó catálogo con URI" + builder.build().toString());
            return RestResponse.created(builder.build());
        } catch (Exception e) {
            logger.error("\nError al crear catálogo: " + e.getMessage());
            return RestResponse.status(RestResponse.Status.BAD_REQUEST);
        }


    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public RestResponse<Void> modificarCatalogo(@PathParam("id") Integer id, Catalogo catalogo) {
        try {
        catalogo.setCodCat(id);
        modifyCatalogo.run(catalogo);
        return RestResponse.noContent();
        } catch (Exception e) {
            logger.error("\nError al modificar catálogo: " + e.getMessage());
            return RestResponse.status(RestResponse.Status.BAD_REQUEST);
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public RestResponse<Catalogo> obtenerCatalogo(@PathParam("id") Integer id) {
        try {
            var catalogo = getCatalogo.run(id);
            if (catalogo.isPresent()) {
                logger.debug("\nCatálogo encontrado con id: " + id);
                return RestResponse.ok(catalogo.get());
            } else {
                logger.debug("\nCatálogo no encontrado con id: " + id);
                return RestResponse.status(RestResponse.Status.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("\nError al obtener catálogo: " + e.getMessage());
            return RestResponse.status(RestResponse.Status.BAD_REQUEST);
        }
    }

    @GET
    @Produces("application/json")
    public RestResponse<Catalogo> buscarCatalogoPorDescripcion(@QueryParam("descripcion") String descripcion) {
        try {
            if (descripcion == null || descripcion.trim().isEmpty()) {
                logger.debug("\nDescripción vacía en búsqueda");
                return RestResponse.status(RestResponse.Status.BAD_REQUEST);
            }
            var catalogo = getCatalogo.findByDescription(descripcion);
            if (catalogo.isPresent()) {
                logger.debug("\nCatálogo encontrado con descripción: " + descripcion);
                return RestResponse.ok(catalogo.get());
            } else {
                logger.debug("\nCatálogo no encontrado con descripción: " + descripcion);
                return RestResponse.status(RestResponse.Status.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("\nError al buscar catálogo: " + e.getMessage());
            return RestResponse.status(RestResponse.Status.BAD_REQUEST);
        }
    }

    @GET
    @Path("/paginated/list")
    @Produces("application/json")
    public RestResponse<PaginatedResponse<Catalogo>> listarCatalogosPaginado(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size) {
        try {
            int pageNumber = page != null && page > 1 ? page : 0;
            int pageSize = size != null && size > 1 ? size : 10;
            
            logger.debug("\nObteniendo catálogos paginados - página: " + pageNumber + ", tamaño: " + pageSize);
            PaginatedResponse<Catalogo> result = getCatalogo.findAllPaginated(pageNumber, pageSize);
            return RestResponse.ok(result);
        } catch (Exception e) {
            logger.error("\nError al obtener catálogos paginados: " + e.getMessage());
            return RestResponse.status(RestResponse.Status.BAD_REQUEST);
        }
    }
}
