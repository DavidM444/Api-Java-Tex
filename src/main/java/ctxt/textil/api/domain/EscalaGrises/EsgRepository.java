package ctxt.textil.api.domain.EscalaGrises;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EsgRepository extends JpaRepository<EscalaGrises,Long> {
    Integer countByEsgValoracion(String valoracion);
}