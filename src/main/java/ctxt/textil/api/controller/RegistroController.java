package ctxt.textil.api.controller;

import ctxt.textil.api.domain.ControlPuntos.CPP;
import ctxt.textil.api.domain.ControlPuntos.CPRepository;
import ctxt.textil.api.domain.ControlPuntos.DatosControlPuntos;
import ctxt.textil.api.domain.Dimensiones.DatosDimensiones;
import ctxt.textil.api.domain.Dimensiones.Dimensiones;
import ctxt.textil.api.domain.Dimensiones.DimensionesRepository;
import ctxt.textil.api.domain.EscalaGrises.DatosEscalaGrises;
import ctxt.textil.api.domain.EscalaGrises.EscalaGrises;
import ctxt.textil.api.domain.EscalaGrises.EsgRepository;
import ctxt.textil.api.domain.Especificaciones.DatosEspecificaciones;
import ctxt.textil.api.domain.Especificaciones.Especificaciones;
import ctxt.textil.api.domain.Especificaciones.EspecificacionesRepository;
import ctxt.textil.api.domain.PAbsorcionPilling.DatosPAbsorcionPilling;
import ctxt.textil.api.domain.PAbsorcionPilling.PAPRepository;
import ctxt.textil.api.domain.PAbsorcionPilling.PAbsorcionPilling;
import ctxt.textil.api.RespuestaTodo.DatosRegistroTodo;
import ctxt.textil.api.RespuestaTodo.DatosRespuestaTodo;
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
/*
Creacion de controlador de registro, para peticiones post,get,put,delete
donde se realiza crud de registros. La eliminacion de registros se realiza a traves de id de requst.
Cuando se crea un registro se llama a los demas repository de las demas tablas para realizar la insercion de los demas
datos de la request(DatosRegistroTodo), lo mismo para actualizarlos, se toman en cuenta las demas tablas.

El id en las demas tablas a excepcion de proveedor actua como llave foranea y primaria de estas, haciendo referencia a la creacion
 de un unico objeto de esa clase por registro creado.
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
    @Autowired
    private EsgRepository esgRepository;
    //Creacion de registros
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTodo> registrarRegistro(@RequestBody @Valid DatosRegistroTodo datosRegistroTodo, HttpServletRequest request, UriComponentsBuilder uriComponentsBuilder){
        System.out.println(datosRegistroTodo);
        Object Idclatt = request.getAttribute("Idcl");
        Long Idcl = ((Integer) Idclatt).longValue();
        System.out.println("Idcl "+Idcl);

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

        DatosRespuestaTodo datosRespuestaTodo =
                new DatosRespuestaTodo(registro.getReId(),registro.getReFecha(),registro.getProveedorId(),datosDimensiones,
                        datosEspecificaciones,datosEscalaGrises,datosPAbsorcionPilling,datosControlPuntos);

        URI url = uriComponentsBuilder.path("/registro/{id}").buildAndExpand(registro.getReId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTodo);
    }
    //Listado de registros
    @GetMapping
    public List<DatosListadoRegistro> listadoRegistro(){
        return registroRepository.findAll().stream().map(DatosListadoRegistro::new).toList();
    }
    //actualizacion de registros
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaRegistro> actualizarRegistro(@RequestBody @Valid DatosActualizarRegistro datosActualizarRegistro){
        System.out.println(datosActualizarRegistro);
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
        return ResponseEntity.ok(new DatosRespuestaRegistro(registro.getReId(),registro.getReFecha(),registro.getProveedorId()));
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
}
