package ctxt.textil.api.domain.escalagrises;

public record DatosList(String valoracion, Integer calificacion) {
    public DatosList(EscalaGrises esg){
        this(esg.getEsgValoracion(), esg.getEsgCalificacion());
    }
}
