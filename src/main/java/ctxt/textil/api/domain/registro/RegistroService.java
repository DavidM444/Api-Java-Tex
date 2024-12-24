package ctxt.textil.api.domain.registro;

import ctxt.textil.api.application.dto.base.*;
import ctxt.textil.api.application.dto.request.DatosActualizarRegistro;
import ctxt.textil.api.application.dto.request.DatosRegistroTodo;
import ctxt.textil.api.application.dto.response.DatosRegistro;
import ctxt.textil.api.application.dto.response.DatosRespuestaRegistro;
import ctxt.textil.api.application.dto.response.DtoRegistro;
import ctxt.textil.api.domain.controlpuntos.CPP;
import ctxt.textil.api.domain.controlpuntos.CPRepository;
import ctxt.textil.api.domain.dimensiones.Dimensiones;
import ctxt.textil.api.domain.dimensiones.DimensionesRepository;
import ctxt.textil.api.domain.escalagrises.DatosList;
import ctxt.textil.api.domain.escalagrises.EscalaGrises;
import ctxt.textil.api.domain.escalagrises.EsgRepository;
import ctxt.textil.api.application.dto.response.DtoInfo;
import ctxt.textil.api.domain.especificaciones.Especificaciones;
import ctxt.textil.api.domain.especificaciones.EspecificacionesRepository;
import ctxt.textil.api.domain.pabsorcionpilling.PAPRepository;
import ctxt.textil.api.domain.pabsorcionpilling.PAbsorcionPilling;
import ctxt.textil.api.domain.proveedor.ProveedorRpty;
import ctxt.textil.api.infraestructure.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Transactional
    public DatosRespuestaRegistro actualizarRegistro(DatosActualizarRegistro registro){
        Map<String, Object> entities = obtenerMapEntity(registro.id());
        actualizarEntidades(entities,registro);
        return new DatosRespuestaRegistro(
                "registro actualizado", registro.id(),registro.fecha()
        );
    }

    public void eliminar(Long id){
        deleteRegistroId(dimensionesRepository,id);
        deleteRegistroId(esgRepository,id);
        deleteRegistroId(cpRepository,id);
        deleteRegistroId(papRepository,id);
        deleteRegistroId(esgRepository,id);
        //finally delete non depend entity
        deleteRegistroId(registroRepository,id);

    }
    public DtoRegistro obtenerRegistroPorId(Long id){
        Registro registro = setearValorRegistroId(registroRepository,id);
        return createRegistroDto(registro);
    }

    public DtoInfo obtenerDatosEstado(){
        Integer datosBajos = esgRepository.countByEsgValoracion("Bajo");
        Integer datosModerados = esgRepository.countByEsgValoracion("Moderada");
        Integer datosAltos = esgRepository.countByEsgValoracion("Alta");
        Integer datosExcelentes = esgRepository.countByEsgValoracion("Excelente");

        Integer cambioSevero = papRepository.countBypConsideracion("Cambio Severo");
        Integer cambioConsiderable = papRepository.countBypConsideracion("Cambio Considerable");
        Integer formacionPilling = papRepository.countBypConsideracion("Formacion Pilling");
        Integer pilling = papRepository.countBypConsideracion("Pilling");
        Integer noHayPilling = papRepository.countBypConsideracion("No Hay Pilling");

        return new DtoInfo(datosBajos,datosModerados,datosAltos,datosExcelentes,
                cambioSevero,cambioConsiderable,formacionPilling,pilling,noHayPilling);
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
        //todo: extraer la obtencion de los objetos por referencebyid a un metodo que retorne un map
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
    private <T> void deleteRegistroId(JpaRepository<T, Long> repository, Long id){
        repository.deleteById(id);
    }


    private Map<String, Object> obtenerMapEntity(Long regId){
        Map<String, Object> entityMap = new HashMap<>();
        try{
            entityMap.put("registro", setearValorRegistroId(registroRepository,regId));
            entityMap.put("dimensiones", setearValorRegistroId(dimensionesRepository,regId));
            entityMap.put("especificaciones", setearValorRegistroId(esgRepository,regId));
            entityMap.put("escalaGrises", setearValorRegistroId(esgRepository,regId));
            entityMap.put("cpp", setearValorRegistroId(cpRepository,regId));
            entityMap.put("pap", setearValorRegistroId(papRepository,regId));
        }catch (Exception ex){
            throw new RuntimeException("no se pudo obtener entidades a actualizar");
        }
        return entityMap;
    }
    private void actualizarEntidades(Map<String,Object> entities, DatosActualizarRegistro datos){
        try {
            // Update each entity
            ((Registro) entities.get("registro"))
                    .actualizarDatos(datos);

            ((Dimensiones) entities.get("dimensiones"))
                    .actualizarDatos(datos.dimensiones());

            ((Especificaciones) entities.get("especificaciones"))
                    .actualizarDatos(datos.especificaciones());

            ((EscalaGrises) entities.get("escalaGrises"))
                    .actualizarDatos(datos.escalagrises());

            ((CPP) entities.get("cpp"))
                    .actualizarDatos(datos.sispuntos());

            ((PAbsorcionPilling) entities.get("pap"))
                    .actualizarDatos(datos.abpilling());

        } catch (ValidationException e) {
            throw new ValidationException("Error de validacion: "+ e.getMessage());
        }catch (Exception ex){
            throw new RuntimeException("Error actualizando registro"+ ex.getMessage(),ex);
        }
    }
}

