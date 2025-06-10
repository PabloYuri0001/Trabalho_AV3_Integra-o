package com.hostpet.hostpet.repository;

import com.hostpet.hostpet.entity.Pet;
import com.hostpet.hostpet.enums.Sexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Query("SELECT p FROM Pet p WHERE p.cliente.user.id = :userId " +
            "AND (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
            "AND (:sexo IS NULL OR p.sexo = :sexo)")
    List<Pet> findPetsWithFilters(@Param("userId") Long userId,
                       @Param("nome") String nome,
                       @Param("sexo") Sexo sexo);

    
}
