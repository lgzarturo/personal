package com.lgzarturo.api.personal.api.generic;

import org.springframework.http.ResponseEntity;

public interface CrudController<RES, REQ, ID> {
    ResponseEntity<RES> post(REQ request);
    ResponseEntity<RES> get(ID id);
    ResponseEntity<RES> put(ID id, REQ request);
    ResponseEntity<Void> delete(ID id);
}
