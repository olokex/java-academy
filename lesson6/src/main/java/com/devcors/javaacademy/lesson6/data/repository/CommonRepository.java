package com.devcors.javaacademy.lesson6.data.repository;

import java.util.List;
import java.util.Optional;

public interface CommonRepository<ENTITY, ID> {

    List<ENTITY> findAll();

    Optional<ENTITY> findById(ID id);
}
