package ctxt.textil.api.Registro;
//datos que queremos mostrar del json que recibimos, los voy a mostror todos
public record DatosListadoRegistro(String fecha, Integer proveedor_pr_id) {
    public DatosListadoRegistro(Registro registro){
        this(registro.getRe_fecha(), registro.getProveedor_pr_id());
    }
}


