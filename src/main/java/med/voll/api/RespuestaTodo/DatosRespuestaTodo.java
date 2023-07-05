package med.voll.api.RespuestaTodo;

import med.voll.api.Dimensiones.DatosDimensiones;

public record DatosRespuestaTodo(Long reId, String reFecha, Integer proveedorPrId,
                                 DatosDimensiones datosDimensiones) {
}
