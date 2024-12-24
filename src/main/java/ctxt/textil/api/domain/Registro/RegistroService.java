package ctxt.textil.api.domain.Registro;

import ctxt.textil.api.application.dto.base.*;
import ctxt.textil.api.application.dto.request.DatosRegistroTodo;
import ctxt.textil.api.application.dto.response.DtoRegistro;
import ctxt.textil.api.domain.ControlPuntos.CPP;
import ctxt.textil.api.domain.ControlPuntos.CPRepository;
import ctxt.textil.api.domain.Dimensiones.Dimensiones;
import ctxt.textil.api.domain.Dimensiones.DimensionesRepository;
import ctxt.textil.api.domain.EscalaGrises.DatosList;
import ctxt.textil.api.domain.EscalaGrises.EscalaGrises;
import ctxt.textil.api.domain.EscalaGrises.EsgRepository;
import ctxt.textil.api.domain.Especificaciones.Especificaciones;
import ctxt.textil.api.domain.Especificaciones.EspecificacionesRepository;
import ctxt.textil.api.domain.PAbsorcionPilling.PAPRepository;
import ctxt.textil.api.domain.PAbsorcionPilling.PAbsorcionPilling;
import ctxt.textil.api.domain.Proveedor.ProveedorRpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public DatosRegistro guardarRegistro(DatosRegistroTodo datosRegistroTodo, Long userId) {
        try {
            Registro registro = crearRegistroBase(datosRegistroTodo.fecha(),datosRegistroTodo.proveedor(), userId);
            crearEntidadesDerivadas(datosRegistroTodo,registro.getReId());
            return new DatosRegistro(registro);
        }catch(Exception ex){
            throw new RuntimeException("No se pudo crear el registro");
        }
    }

    public ResponseEntity<List<DtoRegistro>> getListadoRegistro(){
        List<DtoRegistro> response = responseDto(registroRepository.findAll());
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    private List<DtoRegistro> responseDto (List<Registro> registros){
        return registros.stream().map(this::createRegistroDto).collect(Collectors.toList());
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

    private DtoRegistro createRegistroDto(Registro reg){
        Long id = reg.getReId();
        Dimensiones dt = dimensionesRepository.getReferenceById(id);
        Especificaciones esp = especificacionesRepository.getReferenceById(id);
        EscalaGrises esg = esgRepository.getReferenceById(id);
        PAbsorcionPilling pap = papRepository.getReferenceById(id);
        CPP cpp = cpRepository.getReferenceById(id);

        return new DtoRegistro(id,reg.getReFecha(),
                setearValorRegistroId(prov,id).getPrNombre(),
                setearValorRegistroId(prov,id).getPrId(),
                setearValorRegistroId(prov,id).getPrEmpresa(),
                new DatosDimensiones(dt),
                new DatosEspecificaciones(esp),
                new DatosList(esg),
                new DatosPAbsorcionPilling(
                        pap.getPaCantidad(),pap.getPaTiempo(), pap.getPRango()),
                new DatosControlPuntos(cpp.getCpPuntuacion()));
    }

    private <T> T setearValorRegistroId(JpaRepository<T, Long> clase, Long id){
        return clase.getReferenceById(id);
    }
}

