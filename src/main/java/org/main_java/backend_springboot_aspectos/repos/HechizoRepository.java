package org.main_java.backend_springboot_aspectos.repos;

import org.main_java.backend_springboot_aspectos.domain.hechizos.Hechizo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HechizoRepository extends JpaRepository<Hechizo, Long> {

    Optional<Hechizo> findByNombre(String nombre);
}
