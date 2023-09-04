package com.lgzarturo.api.personal.api.category;

import com.lgzarturo.api.personal.exceptions.ResourceValidationException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    ResponseEntity<List<Category>> list() {
        return ResponseEntity.ok(categoryService.all());
    }

    @PostMapping
    ResponseEntity<Category> create(@RequestBody @Valid Category category, BindingResult result) {
        validatePayload(result);
        return ResponseEntity.ok(categoryService.create(category));
    }

    @GetMapping("/{id}")
    ResponseEntity<Category> read(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.read(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<Category> update(@PathVariable Long id, @RequestBody @Valid Category category, BindingResult result) {
        validatePayload(result);
        return ResponseEntity.ok(categoryService.update(id, category));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/active")
    ResponseEntity<List<Category>> active() {
        return ResponseEntity.ok(categoryService.getAllByStatus(true));
    }

    @GetMapping("/inactive")
    ResponseEntity<List<Category>> inactive() {
        return ResponseEntity.ok(categoryService.getAllByStatus(false));
    }

    @GetMapping("/paginate")
    ResponseEntity<Page<Category>> paginate(Pageable pageable) {
        return ResponseEntity.ok(categoryService.paginate(pageable));
    }

    @GetMapping("/filter")
    ResponseEntity<List<Category>> filter(@RequestBody @Valid List<Long> ids) {
        return ResponseEntity.ok(categoryService.filterByIds(ids));
    }

    private void validatePayload(BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            throw new ResourceValidationException("Invalid payload");
        }
    }
}
