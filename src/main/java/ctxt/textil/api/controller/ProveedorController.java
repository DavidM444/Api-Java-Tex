package ctxt.textil.api.controller;

import ctxt.textil.api.application.dto.response.DatosProvName;
import ctxt.textil.api.application.dto.request.DatosProveedor;
import ctxt.textil.api.application.dto.response.DtoResP;
import ctxt.textil.api.domain.proveedor.*;
import ctxt.textil.api.domain.base.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 *  Creaciòn de registros en la ruta respectiva y respuesta con dto personalizado
 * */

@RestController
@RequestMapping("/proveedor")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ApiResponse<DtoResP>> agregarProveedor(@RequestBody @Valid DatosProveedor dtoRgP, UriComponentsBuilder uriBuilder){

        DtoResP proveedor = proveedorService.saveProveedor(dtoRgP);

        URI location = uriBuilder.path("/api/v1/proveedores/{id}")
                .buildAndExpand(proveedor.id()).toUri();

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,"created proveedor", proveedor));
    };
    @GetMapping
    public ResponseEntity<List<DatosProvName>> listadoProveedores(){
        return ResponseEntity.ok( proveedorService.listarProveedores());
    }
}
