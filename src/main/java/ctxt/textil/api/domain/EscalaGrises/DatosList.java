package ctxt.textil.api.domain.EscalaGrises;

public record DatosList(String valoracion, Integer calificacion) {
    public DatosList(EscalaGrises esg){
        this(esg.getEsgValoracion(), esg.getEsgCalificacion());
    }
}
