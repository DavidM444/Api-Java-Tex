package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.Dimensiones.DatosDimensiones;
import med.voll.api.Dimensiones.Dimensiones;
import med.voll.api.Dimensiones.DimensionesRepository;
import med.voll.api.RespuestaTodo.DatosRegistroTodo;
import med.voll.api.RespuestaTodo.DatosRespuestaTodo;
import med.voll.api.Registro.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/registro")
public class RegistroController {


    @Autowired
    private RegistroRepository registroRepository;
    @Autowired
    private DimensionesRepository dimensionesRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaTodo> registrarRegistro(@RequestBody @Valid DatosRegistroTodo datosRegistroTodo, UriComponentsBuilder uriComponentsBuilder){
        System.out.println("la request llego!!!!!!");
        System.out.println(datosRegistroTodo);

        DatosDimensiones datosDimensiones = new DatosDimensiones(datosRegistroTodo.dimensiones().dm_altura(),datosRegistroTodo.dimensiones().dm_ancho(),
                datosRegistroTodo.dimensiones().registro_re_id());
        Dimensiones dimensiones = dimensionesRepository.save(new Dimensiones(datosDimensiones));
        DatosRegistroRegistro datosRegistroRegistro = new DatosRegistroRegistro(datosRegistroTodo.re_fecha(), datosRegistroTodo.proveedor_pr_id());
        Registro registro =  registroRepository.save(new Registro(datosRegistroRegistro));

        DatosRespuestaTodo datosRespuestaTodo =
                new DatosRespuestaTodo(registro.getRe_id(),registro.getRe_fecha(),registro.getProveedor_pr_id(),datosDimensiones);

        URI url = uriComponentsBuilder.path("/registro/{id}").buildAndExpand(registro.getRe_id()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTodo);

    }

    @GetMapping
    public List<DatosListadoRegistro> listadoRegistro(){
        return registroRepository.findAll().stream().map(DatosListadoRegistro::new).toList();
    }


    /*
    //actualizacion de registros
    @PutMapping
    @Transactional
    public ResponseEntity actualizarRegistro(@RequestBody @Valid DatosActualizarRegistro datosActualizarRegistro){
        System.out.println(datosActualizarRegistro);
        Registro registro = registroRepository.getReferenceById(datosActualizarRegistro.re_id());
        registro.actualizarDatos(datosActualizarRegistro);

        return ResponseEntity.ok(new DatosRespuestaRegistro(registro.getRe_id(),registro.getRe_fecha(),registro.getProveedor_pr_id()));
    }*/



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
                registro.getDimensiones().getDm_altura(), registro.getDimensiones().getDm_ancho(),registro.getDimensiones().getRegistro_re_id());
        return ResponseEntity.ok(datosRegistro);

    }*/
}
