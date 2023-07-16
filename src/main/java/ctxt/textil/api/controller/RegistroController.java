package ctxt.textil.api.controller;

import ctxt.textil.api.ControlPuntos.CPP;
import ctxt.textil.api.ControlPuntos.CPRepository;
import ctxt.textil.api.ControlPuntos.DatosControlPuntos;
import ctxt.textil.api.Dimensiones.DatosDimensiones;
import ctxt.textil.api.Dimensiones.Dimensiones;
import ctxt.textil.api.Dimensiones.DimensionesRepository;
import ctxt.textil.api.EscalaGrises.DatosEscalaGrises;
import ctxt.textil.api.EscalaGrises.EscalaGrises;
import ctxt.textil.api.EscalaGrises.EsgRepository;
import ctxt.textil.api.Especificaciones.DatosEspecificaciones;
import ctxt.textil.api.Especificaciones.Especificaciones;
import ctxt.textil.api.Especificaciones.EspecificacionesRepository;
import ctxt.textil.api.PAbsorcionPilling.DatosPAbsorcionPilling;
import ctxt.textil.api.PAbsorcionPilling.PAPRepository;
import ctxt.textil.api.PAbsorcionPilling.PAbsorcionPilling;
import ctxt.textil.api.Registro.*;
import ctxt.textil.api.RespuestaTodo.DatosRegistroTodo;
import ctxt.textil.api.RespuestaTodo.DatosRespuestaTodo;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public ResponseEntity<DatosRespuestaTodo> registrarRegistro(@RequestBody @Valid DatosRegistroTodo datosRegistroTodo, UriComponentsBuilder uriComponentsBuilder){
        System.out.println(datosRegistroTodo);
        //creacion de registro en la tabla
        DatosRegistroRegistro datosRegistroRegistro = new DatosRegistroRegistro(datosRegistroTodo.re_fecha(), datosRegistroTodo.proveedor_pr_id());
        Registro registro =  registroRepository.save(new Registro(datosRegistroRegistro));
        Long id = registro.getRe_id();
        System.out.println("id "+id);

        DatosDimensiones datosDimensiones = new DatosDimensiones(datosRegistroTodo.dimensiones().dm_altura(),datosRegistroTodo.dimensiones().dm_ancho(),id);
        Dimensiones dimensiones = dimensionesRepository.save(new Dimensiones(datosDimensiones));

        DatosEscalaGrises datosEscalaGrises = new DatosEscalaGrises(datosRegistroTodo.escalagrises().esg_valoracion(),id);
        EscalaGrises escalaGrises = esgRepository.save(new EscalaGrises(datosEscalaGrises));

        DatosControlPuntos datosControlPuntos = new DatosControlPuntos(datosRegistroTodo.sispuntos().cp_puntuacion(),id);
        CPP cpp = cpRepository.save(new CPP(datosControlPuntos));

        DatosPAbsorcionPilling datosPAbsorcionPilling = new DatosPAbsorcionPilling(datosRegistroTodo.abpilling().pa_cantidad(),
                datosRegistroTodo.abpilling().pa_tiempo(),datosRegistroTodo.abpilling().p_rango(),id);
        PAbsorcionPilling pAbsorcionPilling = papRepository.save( new PAbsorcionPilling(datosPAbsorcionPilling));

        DatosEspecificaciones datosEspecificaciones = new DatosEspecificaciones(datosRegistroTodo.especificaciones().es_rollo(),
                datosRegistroTodo.especificaciones().es_peso(),datosRegistroTodo.especificaciones().es_tipoTela(),
                datosRegistroTodo.especificaciones().es_color(),id);
        Especificaciones especificaciones = especificacionesRepository.save( new Especificaciones(datosEspecificaciones));


        DatosRespuestaTodo datosRespuestaTodo =
                new DatosRespuestaTodo(registro.getRe_id(),registro.getRe_fecha(),registro.getProveedor_pr_id(),datosDimensiones);
        URI url = uriComponentsBuilder.path("/registro/{id}").buildAndExpand(registro.getRe_id()).toUri();
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
        Registro registro = registroRepository.getReferenceById(datosActualizarRegistro.re_id());
        registro.actualizarDatos(datosActualizarRegistro);
        Dimensiones dimensiones = dimensionesRepository.getReferenceById(datosActualizarRegistro.re_id());
        dimensiones.actualizarDatos(datosActualizarRegistro.datosActDimensiones());
        Especificaciones especificaciones = especificacionesRepository.getReferenceById(datosActualizarRegistro.re_id());
        especificaciones.actualizarDatos(datosActualizarRegistro.datosActEspecificaciones());
        EscalaGrises escalaGrises = esgRepository.getReferenceById(datosActualizarRegistro.re_id());
        escalaGrises.actualizarDatos(datosActualizarRegistro.datosActEscg());
        CPP cpp = cpRepository.getReferenceById(datosActualizarRegistro.re_id());
        cpp.actualizarDatos(datosActualizarRegistro.datosActCPP());
        PAbsorcionPilling pAbsorcionPilling = papRepository.getReferenceById(datosActualizarRegistro.re_id());
        pAbsorcionPilling.actualizarDatos(datosActualizarRegistro.datosActPAP());
        return ResponseEntity.ok(new DatosRespuestaRegistro(registro.getRe_id(),registro.getRe_fecha(),registro.getProveedor_pr_id()));
    }
    //Delete de registro de la DB. No es logico porque no se preserva el registro
    @DeleteMapping("/{id}")
    @Transactional
    public void  eliminarRegistro(@PathVariable Long id){
        Registro registro = registroRepository.getReferenceById(id);
        registroRepository.delete(registro);
    }
    /*
    //trayendo datos de un solo registro
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaRegistro> retornaDatosRegistro(@PathVariable Long id){
        Registro registro = registroRepository.getReferenceById(id);
        var datosRegistro = new DatosRespuestaRegistro(registro.getRe_id(),registro.getRe_fecha(),registro.getProveedor_pr_id(),
                registro.getDimensiones().getDm_alto(), registro.getDimensiones().getDm_ancho(),registro.getDimensiones().getRegistro_re_id());
        return ResponseEntity.ok(datosRegistro);
    }*/
}
