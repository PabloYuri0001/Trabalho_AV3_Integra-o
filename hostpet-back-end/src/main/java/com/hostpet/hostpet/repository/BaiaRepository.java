package com.hostpet.hostpet.repository;

import com.hostpet.hostpet.entity.Baia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaiaRepository extends JpaRepository<Baia, Integer> {
    long countByUserIdAndStatus(Long userId, String status);
    List<Baia> findByUserId(Long userId);

}
