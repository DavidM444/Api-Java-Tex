package ctxt.textil.api.controller;

import ctxt.textil.api.application.dto.base.DataUser;
import ctxt.textil.api.application.dto.base.DtoRol;
import ctxt.textil.api.application.dto.request.DatosNewUser;
import ctxt.textil.api.domain.user.useradmin.UserAdmin;
import ctxt.textil.api.domain.user.useradmin.UserAdminRepository;
import ctxt.textil.api.application.dto.request.DatosAutenticarUsuario;
import ctxt.textil.api.domain.user.usuario.Roles.RolService;
import ctxt.textil.api.domain.user.usuario.UserRepository;
import ctxt.textil.api.infraestructure.security.EncriptKey;
import ctxt.textil.api.infraestructure.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RolService rolService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAdminRepository userAdminRepository;

    public AdminController(AuthenticationManager authenticationManager, TokenService tokenService,
                           RolService rolService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.rolService = rolService;
    }

    @Transactional
    public ResponseEntity<String> registrarUserAdmin(@RequestBody @Valid DatosNewUser dtUser){
        String clave = EncriptKey.BycriptKeydd(dtUser.clave());
        DataUser dataUser = new DataUser(dtUser.nombre(),dtUser.apellido(), dtUser.email(),clave);
        UserAdmin usuario = userAdminRepository.save(new UserAdmin(dataUser));
        rolService.saveRolAuthority(new DtoRol( (usuario.getAdId()),usuario.getAuthorities().toString()));
        return ResponseEntity.ok("Usuario administrador creado con èxito");
    }


    @Transactional
    @PostMapping("/auth")
    public ResponseEntity<AdminResponse> autenticarUserAdmin(@RequestBody @Valid DatosAutenticarUsuario datosAdmin) {
        Authentication auth = new UsernamePasswordAuthenticationToken(datosAdmin.email(), datosAdmin.clave());
        var authenticationResult = authenticationManager.authenticate(auth);
        UserAdmin admin = (UserAdmin) authenticationResult.getPrincipal();
        var jwtToken = tokenService.generarAdminToken(admin);

        /* For find and return data user created:
        Integer id = tokenService.getIdClaim(jwtToken);
        UserAdmin user = userAdminRepository.findByAdId(id);
        */
        AdminResponse adminResponse = new AdminResponse(admin.getAdNombre(), admin.getAdApellido(), admin.getAdEmail(), jwtToken);
        return ResponseEntity.ok(adminResponse);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public LinkedList<UserList> getUsers() {
        List<UserList> lista2 = userRepository.findAll().stream().map(
                user -> new UserList(user.getUsId(), user.getUsNombre(), user.getUsApellido(), user.getUsEmail())).toList();
        return new LinkedList<>(lista2);
    }

    protected record UserList(Long id, String nombre, String apellido, String email) {
    }

    protected record AdminResponse(String nombre, String apellido, String email, String jwtToken) {
    }
}
