package com.schoolinformer.repository;

import com.schoolinformer.model.entity.PUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PUserRepository extends JpaRepository<PUser, Long> {
//    @Override
//    @NotNull
//    @Query(value = "SELECT DISTINCT pus FROM PUser pus JOIN FETCH pus.addresses")
//    List<PUser> findAll();
}
