package com.hostpet.hostpet.repository;

import com.hostpet.hostpet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    
    // Método para buscar um usuário pelo seu email
    UserDetails findByEmail(String email);


}
