package ctxt.textil.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping()
    public String Hello(){
        return "Hello Docker \n" +
                "Retorno en prueba";
    }
}
