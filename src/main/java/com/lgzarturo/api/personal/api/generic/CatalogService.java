package com.lgzarturo.api.personal.api.generic;


import org.springframework.data.domain.Page;

public interface CatalogService<RES, ID> {
    Page<RES> getAll(Integer page, Integer size, SortType sortType);
    RES get(ID id);
    void active(ID id);
    void inactive(ID id);
}
