package ctxt.textil.api.controller;

import ctxt.textil.api.domain.ControlPuntos.*;
import ctxt.textil.api.domain.Dimensiones.DatosActDimensiones;
import ctxt.textil.api.domain.Dimensiones.DatosDimensiones;
import ctxt.textil.api.domain.Dimensiones.Dimensiones;
import ctxt.textil.api.domain.Dimensiones.DimensionesRepository;
import ctxt.textil.api.domain.EscalaGrises.*;
import ctxt.textil.api.domain.Especificaciones.*;
import ctxt.textil.api.domain.PAbsorcionPilling.*;
import ctxt.textil.api.RespuestaTodo.DatosRegistroTodo;
import ctxt.textil.api.RespuestaTodo.DatosRespuestaTodo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @PostMapping
    @Transactional
    public ResponseEntity<String> registrarRegistro(@RequestBody @Valid DatosRegistroTodo datosRegistroTodo, HttpServletRequest request, UriComponentsBuilder uriComponentsBuilder){
        Object Idclatt = request.getAttribute("Idcl");
        Long Idcl = ((Integer) Idclatt).longValue();
        System.out.println("idcl "+Idcl + "id del prveedor: "+ datosRegistroTodo.proveedor());

        //creacion de registro en la tabla
        DatosRegistroRegistro datosRegistroRegistro = new DatosRegistroRegistro(datosRegistroTodo.fecha(), datosRegistroTodo.proveedor(),Idcl);
        Registro registro =  registroRepository.save(new Registro(datosRegistroRegistro));
        Long id = registro.getReId();
        System.out.println("registro: "+registro.getReFecha() + " id "+id);

        DatosDimensiones datosDimensiones = new DatosDimensiones(datosRegistroTodo.dimensiones().altura(),datosRegistroTodo.dimensiones().ancho(),id);
        Dimensiones dimensiones = dimensionesRepository.save(new Dimensiones(datosDimensiones));

        DatosEscalaGrises datosEscalaGrises = new DatosEscalaGrises(datosRegistroTodo.escalagrises().valoracion(),id);
        EscalaGrises escalaGrises = esgRepository.save(new EscalaGrises(datosEscalaGrises));

        DatosControlPuntos datosControlPuntos = new DatosControlPuntos(datosRegistroTodo.sispuntos().puntuacion(),id);
        CPP cpp = cpRepository.save(new CPP(datosControlPuntos));

        DatosPAbsorcionPilling datosPAbsorcionPilling = new DatosPAbsorcionPilling(datosRegistroTodo.abpilling().cantidad(),
                datosRegistroTodo.abpilling().tiempo(),datosRegistroTodo.abpilling().rango(),id);

        PAbsorcionPilling pAbsorcionPilling = papRepository.save( new PAbsorcionPilling(datosPAbsorcionPilling));

        DatosEspecificaciones datosEspecificaciones = new DatosEspecificaciones(datosRegistroTodo.especificaciones().rollo(),
                datosRegistroTodo.especificaciones().peso(),datosRegistroTodo.especificaciones().tipoTela(),
                datosRegistroTodo.especificaciones().color(),id);
        Especificaciones especificaciones = especificacionesRepository.save( new Especificaciones(datosEspecificaciones));


        URI url = uriComponentsBuilder.path("/registro/{id}").buildAndExpand(registro.getReId()).toUri();
        return ResponseEntity.created(url).body("Registro Creado Exitosamente");
    }

    /**
     * Actualizacion de peticion get donde se se retorna el listado de todos los registros que se encuentran orgnaizdos por su id, las consultas son ejecutadas y se relacionan
     * por id y se mapean a un solo objeto para visualizarse.
    */
    @GetMapping
    public List<DatosRespuestaTodo> listadoRegistro(){
        List<Registro> registros = registroRepository.findAll();
        return registros.stream()
                .map(registro ->{

                    Long id = registro.getReId();
                    System.out.println("id de registro :" + id);
                    Dimensiones dimensiones = dimensionesRepository.getReferenceById(id);
                    Especificaciones especificaciones = especificacionesRepository.getReferenceById(id);
                    EscalaGrises escalaid = esgRepository.getReferenceById(id);
                    CPP cpid = cpRepository.getReferenceById(id);
                    PAbsorcionPilling  papillingid =  papRepository.getReferenceById(id);
                    Long proveedor = registro.getProveedorId().longValue();
                    System.out.println("proveedor id "+proveedor);
                    Proveedor proveedor1 = prov.getReferenceById(proveedor);


                    return new DatosRespuestaTodo(
                            id,registro.getReFecha(),proveedor1.getPrNombre(),proveedor1.getPrEmpresa(),
                            new DatosActDimensiones(dimensiones.getDmAlto(),dimensiones.getDmAncho()),
                            new DatosActEspecificaciones(especificaciones.getEsRollo(),especificaciones.getEsPeso(),especificaciones.getEsColor(),especificaciones.getEsTipoTela()),
                            new DatosList(escalaid.getEsgValoracion(),escalaid.getEsgCalificacion()),
                            new DatosActPAP(papillingid.getPaCantidad(),papillingid.getPaTiempo(),papillingid.getPRango()),
                            new DatosActCP(cpid.getCpPuntuacion())
                    );

                }).toList();
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
}
