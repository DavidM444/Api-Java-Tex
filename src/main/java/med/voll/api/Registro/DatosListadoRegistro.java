package med.voll.api.Registro;
//datos que queremos mostrar del json que recibimos, los voy a mostror todos
public record DatosListadoRegistro(String fecha, Integer proveedor_pr_id) {
    //casteo y retorn de tipo Registro
    public DatosListadoRegistro(Registro registro){
        this(registro.getRe_fecha(), registro.getProveedor_pr_id());
    }
}


