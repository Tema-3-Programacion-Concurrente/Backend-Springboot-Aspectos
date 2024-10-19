package org.main_java.backend_springboot_aspectos.repos;

import org.main_java.backend_springboot_aspectos.domain.EventoMagico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoMagicoRepository extends JpaRepository<EventoMagico, Long> {
}

