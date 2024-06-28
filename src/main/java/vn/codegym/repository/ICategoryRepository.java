package vn.codegym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.codegym.model.category.Category;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "CALL delete_category_by_id(:id)")
    void deleteCategoryById(@Param("id") Long id);
}
