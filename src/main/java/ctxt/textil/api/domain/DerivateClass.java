package ctxt.textil.api.domain;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DerivateClass {
    @NotNull
    @Id
    private Long registroId;

    public DerivateClass(@NotNull Long registroId) {
        this.registroId = registroId;
    }
}
