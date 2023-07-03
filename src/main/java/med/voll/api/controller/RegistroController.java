package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.Registro.DatosListadoRegistro;
import med.voll.api.Registro.DatosRegistroRegistro;
import med.voll.api.Registro.Registro;
import med.voll.api.Registro.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
