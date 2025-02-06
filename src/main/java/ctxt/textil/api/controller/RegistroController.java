package ctxt.textil.api.controller;

import ctxt.textil.api.application.dto.request.DatosActualizarRegistro;
import ctxt.textil.api.application.dto.response.DatosRegistro;
import ctxt.textil.api.application.dto.response.DatosRespuestaRegistro;
import ctxt.textil.api.application.dto.request.DatosRegistroTodo;
import ctxt.textil.api.application.dto.response.DtoRegistro;
import ctxt.textil.api.application.dto.response.DtoInfo;
import ctxt.textil.api.domain.registro.*;
import ctxt.textil.api.infraestructure.exception.UserIdClaimNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private RegistroRepository registroRepository;
    @Autowired
    private RegistroService registroService;

    @PostMapping
    public ResponseEntity<DatosRegistro> crearRegistroCompleto(@RequestBody @Valid DatosRegistroTodo datosRegistroTodo,
                                                               HttpServletRequest request,
                                                               UriComponentsBuilder uriComponentsBuilder){

        Long Idcl = extractIdUser(request);
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
        DatosRespuestaRegistro response=  registroService.actualizarRegistro(datosActualizarRegistro);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public void  eliminarRegistro(@PathVariable Long id){
        registroService.eliminar(id);
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
       DtoInfo dtoInfo = registroService.obtenerDatosEstado();
       return ResponseEntity.ok(dtoInfo);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DtoRegistro> registroId(@PathVariable("id") Long idParam){
       DtoRegistro dto = registroService.obtenerRegistroPorId(idParam);
       return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }

    public Long extractIdUser(HttpServletRequest request) {
        Object id = request.getAttribute("Idcl");
        if(id==null){
            throw new UserIdClaimNotFoundException();
        }
        return ((Integer)id).longValue();
    }
}
