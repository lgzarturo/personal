package com.lgzarturo.api.personal.api.generic;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface CatalogController<RES, ID> {
    ResponseEntity<Page<RES>> getAll(Integer page, Integer size, SortType sortType);
    ResponseEntity<RES> get(ID id);
    ResponseEntity<Void> active(ID id);
    ResponseEntity<Void> inactive(ID id);
}
