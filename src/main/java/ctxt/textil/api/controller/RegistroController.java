package ctxt.textil.api.controller;

import ctxt.textil.api.application.dto.base.*;
import ctxt.textil.api.domain.ControlPuntos.*;
import ctxt.textil.api.domain.Dimensiones.Dimensiones;
import ctxt.textil.api.domain.Dimensiones.DimensionesRepository;
import ctxt.textil.api.domain.EscalaGrises.*;
import ctxt.textil.api.domain.Especificaciones.*;
import ctxt.textil.api.domain.PAbsorcionPilling.*;
import ctxt.textil.api.application.dto.request.DatosRegistroTodo;
import ctxt.textil.api.application.dto.response.DtoRegistro;
import ctxt.textil.api.domain.Proveedor.Proveedor;
import ctxt.textil.api.domain.Proveedor.ProveedorRpty;
import ctxt.textil.api.domain.Registro.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Creacion de controlador de registro, para peticiones post,get,put,delete
 * donde se realiza crud de registros. La eliminacion de registros se realiza a traves de id de requst.
 * Cuando se crea un registro se llama a los demas repository de las demas tablas para realizar la insercion de los demas
 * datos de la request(DatosRegistroTodo), lo mismo para actualizarlos, se toman en cuenta las demas tablas.
 *
 * El id en las demas tablas a excepcion de proveedor actua como llave foranea y primaria de estas, haciendo referencia a la creacion
 * de un unico objeto de esa clase por registro creado.
 * */
@RestController
@RequestMapping("/registro")

public class RegistroController {
    //inyeccion de repositorios
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
    //Creacion de registros
    @Autowired
    private EsgRepository esgRepository;
    @Autowired
    private ProveedorRpty prov;

    //impl service
    @Autowired
    private RegistroService registroService;
    @PostMapping
    public ResponseEntity<DatosRegistro> crearRegistroCompleto(@RequestBody @Valid DatosRegistroTodo datosRegistroTodo,
                                                           HttpServletRequest request,
                                                           UriComponentsBuilder uriComponentsBuilder){
        Long Idcl = extractIdUser(request);
       // System.out.println("idcl "+Idcl + "id del prveedor: "+ datosRegistroTodo.proveedor());
        Registro datosRegistroRegistro = new Registro(datosRegistroTodo.fecha(), datosRegistroTodo.proveedor(),Idcl);
        DatosRegistro registro =  registroService.guardarRegistro(datosRegistroTodo, Idcl);

        URI url = uriComponentsBuilder.path("/registro/{id}").buildAndExpand(registro.id()).toUri();
        return ResponseEntity.created(url).body(registro);
    }

    /**
     * Actualizacion de peticion get donde se se retorna el listado de todos los registros que se encuentran orgnaizdos por su id, las consultas son ejecutadas y se relacionan
     * por id y se mapean a un solo objeto para visualizarse.
    */
    @GetMapping
    public List<DtoRegistro> listadoRegistro(){ // return ResponseEntity
        List<Registro> registros = registroRepository.findAll();
        return registroService.getListadoRegistro().getBody();
    }
    // Actualizacion de registros
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaRegistro> actualizarRegistro(@RequestBody @Valid DatosActualizarRegistro datosActualizarRegistro){
        Registro registro = registroRepository.getReferenceById(datosActualizarRegistro.id());
        registro.actualizarDatos(datosActualizarRegistro);

        Dimensiones dimensiones = dimensionesRepository.getReferenceById(datosActualizarRegistro.id());
        dimensiones.actualizarDatos(datosActualizarRegistro.dimensiones());

        Especificaciones especificaciones = especificacionesRepository.getReferenceById(datosActualizarRegistro.id());
        especificaciones.actualizarDatos(datosActualizarRegistro.especificaciones());

        EscalaGrises escalaGrises = esgRepository.getReferenceById(datosActualizarRegistro.id());
        escalaGrises.actualizarDatos(datosActualizarRegistro.escalagrises());

        CPP cpp = cpRepository.getReferenceById(datosActualizarRegistro.id());
        cpp.actualizarDatos(datosActualizarRegistro.sispuntos());

        PAbsorcionPilling pAbsorcionPilling = papRepository.getReferenceById(datosActualizarRegistro.id());
        pAbsorcionPilling.actualizarDatos(datosActualizarRegistro.abpilling());

        return ResponseEntity.ok(new DatosRespuestaRegistro("Registro Actualizado",registro.getReId(),registro.getReFecha()));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public void  eliminarRegistro(@PathVariable Long id){
        Dimensiones dimensiones = dimensionesRepository.getReferenceById(id);
        dimensionesRepository.delete(dimensiones);

        EscalaGrises escalaGrises = esgRepository.getReferenceById(id);
        esgRepository.delete(escalaGrises);

        CPP cpp = cpRepository.getReferenceById(id);
        cpRepository.delete(cpp);

        PAbsorcionPilling pAbsorcionPilling = papRepository.getReferenceById(id);
        papRepository.delete(pAbsorcionPilling);

        Especificaciones especificaciones = especificacionesRepository.getReferenceById(id);
        especificacionesRepository.delete(especificaciones);

        Registro registro = registroRepository.getReferenceById(id);
        registroRepository.delete(registro);
    }
    /*
    //trayendo datos de un solo registro
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaRegistro> retornaDatosRegistro(@PathVariable Long id){
        Registro registro = registroRepository.getReferenceById(id);
        var datosRegistro = new DatosRespuestaRegistro(registro.getReId(),registro.getReFecha(),registro.getProveedorId(),
                registro.getDimensiones().getDmAlto(), registro.getDimensiones().getDmAncho(),registro.getDimensiones().getRegistroId());
        return ResponseEntity.ok(datosRegistro);
    }*/
    //Datos info Database

   @GetMapping("/datos")
    public ResponseEntity<DtoInfo> dataInfoRegistros(){
       Integer datosBajos = esgRepository.countByEsgValoracion("Bajo");
       Integer datosModerados = esgRepository.countByEsgValoracion("Moderada");
       Integer datosAltos = esgRepository.countByEsgValoracion("Alta");
       Integer datosExcelentes = esgRepository.countByEsgValoracion("Excelente");

       Integer cambioSevero = papRepository.countBypConsideracion("Cambio Severo");
       Integer cambioConsiderable = papRepository.countBypConsideracion("Cambio Considerable");
       Integer formacionPilling = papRepository.countBypConsideracion("Formacion Pilling");
       Integer pilling = papRepository.countBypConsideracion("Pilling");
       Integer noHayPilling = papRepository.countBypConsideracion("No Hay Pilling");
       return ResponseEntity.ok(new DtoInfo(datosBajos,datosModerados,datosAltos,datosExcelentes,
               cambioSevero,cambioConsiderable,formacionPilling,pilling,noHayPilling));
    }


    @GetMapping("/{id}")
    public ResponseEntity<DtoRegistro> registroId(@PathVariable("id") Long idParam){
       Registro registro = registroRepository.getReferenceById(idParam);
        Dimensiones dimensiones = dimensionesRepository.getReferenceById(idParam);
        Especificaciones especificaciones = especificacionesRepository.getReferenceById(idParam);
        EscalaGrises escalaid = esgRepository.getReferenceById(idParam);
        CPP cpid = cpRepository.getReferenceById(idParam);
        PAbsorcionPilling  papillingid =  papRepository.getReferenceById(idParam);
        Proveedor proveedor1 = prov.getReferenceById(registro.getProveedorId().longValue());


        return ResponseEntity.ok(new DtoRegistro(
                registro.getReId(), registro.getReFecha(),proveedor1.getPrNombre(),proveedor1.getPrId(),proveedor1.getPrEmpresa(),
                new DatosDimensiones(dimensiones.getDmAlto(),dimensiones.getDmAncho()),
                new DatosEspecificaciones(especificaciones.getEsRollo(),especificaciones.getEsPeso(),especificaciones.getEsColor(),especificaciones.getEsTipoTela()),
                new DatosList(escalaid.getEsgValoracion(),escalaid.getEsgCalificacion()),
                new DatosPAbsorcionPilling(papillingid.getPaCantidad(),papillingid.getPaTiempo(),papillingid.getPRango()),
                new DatosControlPuntos(cpid.getCpPuntuacion())
        ));


    }

    public Long extractIdUser(HttpServletRequest request){
        Object id = request.getAttribute("Idcl");
        if(id==null){
            throw new RuntimeException("Not found id. Register registro failded.");
        }
        return ((Integer)id).longValue();
    }
}
