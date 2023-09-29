package com.inn.cafe.service;

import org.springframework.http.ResponseEntity;
import com.inn.cafe.POJO.Category;
import java.util.Map;
import java.util.List;

public interface CategoryService {

    ResponseEntity<String> addNewCategory (Map<String, String> requestMap);

    ResponseEntity<List<Category>> getAllCategory (String filterValue);

    ResponseEntity<String> updateCategory (Map<String, String> requestMap);

}
