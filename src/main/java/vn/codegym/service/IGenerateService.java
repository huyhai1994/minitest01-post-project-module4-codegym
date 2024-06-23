package vn.codegym.service;

public interface IGenerateService<T> {
    Iterable<T> findAll();

    void save(T t);

    T findById(Long id);

    void remove(Long id);
}
