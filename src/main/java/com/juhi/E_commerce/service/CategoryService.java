package com.juhi.E_commerce.service;

import com.juhi.E_commerce.model.Category;


import java.util.List;

public interface CategoryService {


    public Category saveCategory(Category category);
    public boolean existCategory(String name );
    public List<Category> getAllCategory();
    public Boolean deleteCategory(int id);

   public Category getCategoryById( int id);
}
