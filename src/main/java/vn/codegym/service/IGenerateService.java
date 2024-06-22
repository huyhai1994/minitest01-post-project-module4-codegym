package vn.codegym.service;

import org.springframework.data.domain.Sort;

import java.util.List;

public interface IGenerateService<T> {
    List<T> findAll(Sort id);

    void save(T t);

    T findById(Long id);

    void remove(Long id);
}
