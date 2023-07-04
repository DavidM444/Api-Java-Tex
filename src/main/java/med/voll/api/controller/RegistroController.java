package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.Registro.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registro")
public class RegistroController {


    @Autowired
    private RegistroRepository registroRepository;
    @PostMapping
    public void registrarRegistro(@RequestBody @Valid DatosRegistroRegistro datosRegistroRegistro){
        System.out.println("la request llego!!!!!!");
        System.out.println(datosRegistroRegistro);
        registroRepository.save(new Registro(datosRegistroRegistro));
    }

    @GetMapping
    public List<DatosListadoRegistro> listadoRegistro(){
        return registroRepository.findAll().stream().map(DatosListadoRegistro::new).toList();
    }

    //actualizacion de registros
    @PutMapping
    @Transactional
    public ResponseEntity actualizarRegistro(@RequestBody @Valid DatosActualizarRegistro datosActualizarRegistro){
        System.out.println(datosActualizarRegistro);
        Registro registro = registroRepository.getReferenceById(datosActualizarRegistro.re_id());
        registro.actualizarDatos(datosActualizarRegistro);

        return ResponseEntity.ok(new DatosRespuestaRegistro(registro.getRe_id(),registro.getRe_fecha(),registro.getProveedor_pr_id()));
    }



    //Delete de registro de la DB. No es logico porque no se preserva el registro
    @DeleteMapping("/{id}")
    @Transactional
    public void  eliminarRegistro(@PathVariable Long id){
        Registro registro = registroRepository.getReferenceById(id);
        registroRepository.delete(registro);
    }

    //trayendo datos de un solo registro
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaRegistro> retornaDatosRegistro(@PathVariable Long id){
        Registro registro = registroRepository.getReferenceById(id);
        var datosRegistro = new DatosRespuestaRegistro(registro.getRe_id(),registro.getRe_fecha(),registro.getProveedor_pr_id());
        return ResponseEntity.ok(datosRegistro);
    }
}
