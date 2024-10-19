package org.main_java.backend_springboot_aspectos.repos;

import org.main_java.backend_springboot_aspectos.domain.Seguridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguridadRepository extends JpaRepository<Seguridad, Long> {
}

