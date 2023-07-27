package ctxt.textil.api.domain.Registro;
//datos que queremos mostrar del json que recibimos, los voy a mostror todos
public record DatosListadoRegistro(String fecha, Integer proveedor_pr_id) {
    //casteo y retorn de tipo Registro
    public DatosListadoRegistro(Registro registro){
        this(registro.getReFecha(), registro.getProveedorId());
    }
}


