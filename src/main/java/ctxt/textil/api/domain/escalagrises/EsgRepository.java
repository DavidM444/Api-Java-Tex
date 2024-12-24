package ctxt.textil.api.domain.escalagrises;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EsgRepository extends JpaRepository<EscalaGrises,Long> {
    Integer countByEsgValoracion(String valoracion);
}