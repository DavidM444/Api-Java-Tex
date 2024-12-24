package ctxt.textil.api.domain.base;


import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class DerivateClass {
    @NotNull
    @Id
    private Long registroId;

    protected DerivateClass(@NotNull Long registroId) {
        this.registroId = registroId;
    }

    protected void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(String.format("El campo %s no puede ser nulo", fieldName));
        }
    }
}
