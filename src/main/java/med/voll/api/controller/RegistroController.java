package med.voll.api.controller;

import med.voll.api.Registro.DatosRegistroRegistro;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registro")
public class RegistroController {
    @PostMapping
    public void registrarRegistro(@RequestBody DatosRegistroRegistro datosRegistroRegistro){
        System.out.println(datosRegistroRegistro);
    }
}
