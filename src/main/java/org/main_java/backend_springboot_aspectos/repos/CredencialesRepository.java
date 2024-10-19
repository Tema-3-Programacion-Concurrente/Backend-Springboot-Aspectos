package org.main_java.backend_springboot_aspectos.repos;

import org.main_java.backend_springboot_aspectos.domain.Credenciales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CredencialesRepository extends JpaRepository<Credenciales, Long> {
    Optional<Credenciales> findByUsername(String username);
}