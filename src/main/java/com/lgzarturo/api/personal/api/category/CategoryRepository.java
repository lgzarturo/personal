package com.lgzarturo.api.personal.api.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIsActive(Boolean isActive);
    List<Category> findAllByIsActiveTrue();
    /*
    @Query("select c from Category c join fetch c.jobs where c.id = :categoryId")
    fun findAllJobsByCategoryId(@Param("categoryId") categoryId: Long): Optional<Category>
     */
}
