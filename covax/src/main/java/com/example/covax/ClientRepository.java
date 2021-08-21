package com.example.covax;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {


    Optional<Client> findClientByName(String name);

    @Query("SELECT COUNT(v) FROM Client v WHERE v.shots = 1")
    String countFirstShot(@Param("shots") Integer shots);

    @Query("SELECT COUNT(v) FROM Client v WHERE v.shots = 2")
    String countSecondShot(@Param("shots") Integer shots);

//    @Query("SELECT v FROM Client v WHERE v.shots = 1")
//    public List<Client> clientFirstShot(@Param("shots") Integer shots);

}
