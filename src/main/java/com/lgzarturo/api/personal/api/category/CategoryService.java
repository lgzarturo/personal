package com.lgzarturo.api.personal.api.category;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<Category> getAllByStatus(Boolean isActive);
    List<Category> filterByIds(List<Long> ids);
    Page<Category> paginate(Pageable pageable);
    List<Category> all();
    Category create(Category category);
    Category read(Long id);
    Category update(Long id, Category category);
    void delete(Long id);
}
