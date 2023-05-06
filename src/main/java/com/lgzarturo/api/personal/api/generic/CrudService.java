package com.lgzarturo.api.personal.api.generic;

public interface CrudService<REQ, RES, ID> {
    RES create(REQ request);
    RES read(ID id);
    RES update(ID id, REQ request);
    void deleteById(ID id);
}
