package com.lgzarturo.api.personal.api.category;

import com.lgzarturo.api.personal.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceJpa implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceJpa(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllByStatus(Boolean isActive) {
        return categoryRepository.findAllByIsActive(isActive);
    }

    @Override
    public List<Category> filterByIds(List<Long> ids) {
        return categoryRepository.findAllById(ids);
    }

    @Override
    public Page<Category> paginate(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> all() {
        return categoryRepository.findAll(Sort.by("name"));
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category read(Long id) {
        return getById(id);
    }

    @Override
    public Category update(Long id, Category category) {
        Category categoryInstance = getById(id);
        // TODO: Validate if this is the correct way to copy properties
        BeanUtils.copyProperties(category, categoryInstance, "id");
        return categoryRepository.save(categoryInstance);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.delete(getById(id));
    }

    private Category getById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Category not found");
        }
        return category.get();
    }
}
