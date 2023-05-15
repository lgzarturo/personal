package com.lgzarturo.api.personal.api.generic;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface CatalogController<RES, REQ, ID> {
    ResponseEntity<Page<RES>> getAll(Integer price, Integer size, SortType sortType);
    ResponseEntity<RES> get(ID id);
    ResponseEntity<Void> active(ID id);
    ResponseEntity<Void> inactive(ID id);
    ResponseEntity<RES> post(REQ request);
}
