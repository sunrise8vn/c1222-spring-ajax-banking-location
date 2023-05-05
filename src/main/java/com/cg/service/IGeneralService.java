package com.cg.service;

import java.util.List;
import java.util.Optional;

public interface IGeneralService<T> {

    List<T> findALl();

    Optional<T> findById(Long id);

    T save(T t);

    void delete(T t);

    void deleteById(Long id);
}
