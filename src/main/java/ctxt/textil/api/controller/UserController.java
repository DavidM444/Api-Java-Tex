package ctxt.textil.api.controller;

import ctxt.textil.api.Security.Encript.EncriptKey;
import ctxt.textil.api.Usuario.DatosNewUser;
import ctxt.textil.api.Usuario.DtoSaveUser;
import ctxt.textil.api.Usuario.UserRepository;
import ctxt.textil.api.Usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/signup")
public class UserController {
    @Value("api.security.secret")
    private String apiSecret;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> registroUser(@RequestBody  DatosNewUser datosNewUser)  {
        System.out.println(datosNewUser);
        String clave = datosNewUser.clave();

        //String claveEncript = EncriptKey.Encript(clave,apiSecret);
        //System.out.println("clave "+ clave+"     "+ "apisecret "+ apiSecret +"    mensaje: "+ claveEncript);

        System.out.println("---------------");


        String claveSave = EncriptKey.BycriptKeydd(clave);

        System.out.println("Hash generado: " + claveSave);
        DtoSaveUser dtoSaveUser = new DtoSaveUser(datosNewUser.nombre(), datosNewUser.apellido(), datosNewUser.email(), claveSave);
        Usuario usuario = userRepository.save(new Usuario(dtoSaveUser));

        return ResponseEntity.ok("ruta disponible ss " + dtoSaveUser);
        }


}
