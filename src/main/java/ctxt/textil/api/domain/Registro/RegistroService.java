package ctxt.textil.api.domain.Registro;

import ctxt.textil.api.application.dto.request.DatosRegistroTodo;
import ctxt.textil.api.application.dto.response.DtoRegistro;
import ctxt.textil.api.domain.ControlPuntos.CPP;
import ctxt.textil.api.domain.ControlPuntos.CPRepository;
import ctxt.textil.api.domain.DerivateClass;
import ctxt.textil.api.domain.Dimensiones.Dimensiones;
import ctxt.textil.api.domain.Dimensiones.DimensionesRepository;
import ctxt.textil.api.domain.EscalaGrises.EscalaGrises;
import ctxt.textil.api.domain.EscalaGrises.EsgRepository;
import ctxt.textil.api.domain.Especificaciones.Especificaciones;
import ctxt.textil.api.domain.Especificaciones.EspecificacionesRepository;
import ctxt.textil.api.domain.PAbsorcionPilling.PAPRepository;
import ctxt.textil.api.domain.PAbsorcionPilling.PAbsorcionPilling;
import ctxt.textil.api.domain.Proveedor.ProveedorRpty;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private DimensionesRepository dimensionesRepository;
    @Autowired
    private EspecificacionesRepository especificacionesRepository;
    @Autowired
    private CPRepository cpRepository;
    @Autowired
    private PAPRepository papRepository;
    @Autowired
    private EsgRepository esgRepository;
    @Autowired
    private ProveedorRpty prov;

    @Transactional
    public DatosRegistro guardarRegistro(DatosRegistroTodo datosRegistroTodo,
                                        Long userId)
    {
        try {
            Registro registro = crearRegistroBase(datosRegistroTodo.fecha(),datosRegistroTodo.proveedor(), userId);
            crearEntidadesDerivadas(datosRegistroTodo,registro.getReId());
            return new DatosRegistro(registro);
        }catch(Exception ex){
            throw new RuntimeException("No se pudo crear el registro");
        }
    }

    private Registro crearRegistroBase(String fecha, Integer proveedor, Long userID){
        return registroRepository.save(new Registro(fecha,proveedor,userID));

    }

    private void crearEntidadesDerivadas(DatosRegistroTodo data, Long registroId){
        dimensionesRepository.save(new Dimensiones(data.dimensiones(),registroId));
        esgRepository.save(new EscalaGrises(data.escalagrises(),registroId));
        cpRepository.save(new CPP(data.sispuntos(),registroId));
        papRepository.save(new PAbsorcionPilling(data.abpilling(),registroId));
        especificacionesRepository.save(new Especificaciones(data.especificaciones(),registroId));
    }

    private <T extends DerivateClass> T setearValorRegistroId(T clase){
        return clase;
    }
}

