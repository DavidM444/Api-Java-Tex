package ctxt.textil.api.application.dto.response;

import ctxt.textil.api.application.dto.base.DatosDimensiones;

import ctxt.textil.api.application.dto.base.DatosControlPuntos;
import ctxt.textil.api.domain.escalagrises.DatosList;
import ctxt.textil.api.application.dto.base.DatosEspecificaciones;
import ctxt.textil.api.application.dto.base.DatosPAbsorcionPilling;

//Record para la respuesta de del ResponseEntity en las peticiones
public record DtoRegistro(
        Long id,
        String fecha,
        String nombreProveedor,
        Long proveedor,
        String empresa,
         DatosDimensiones dimensiones,
         DatosEspecificaciones especificaciones,
         DatosList escalagrises,
         DatosPAbsorcionPilling abpilling,
         DatosControlPuntos sispuntos) {
};


