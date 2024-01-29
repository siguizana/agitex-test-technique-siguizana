package org.agitex.test.repository;

import org.agitex.test.domain.Client;
import org.agitex.test.dto.ISalaireMoyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "select c.profession as profession, AVG(c.salaire) as salaireMoyen" +
            " from client c GROUP BY c.profession ", nativeQuery = true)
    List<ISalaireMoyen> salaireMoyen();
}
