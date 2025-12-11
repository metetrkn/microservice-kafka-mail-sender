package com.mete.eshop.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mete.eshop.model.Category;
import com.mete.eshop.model.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    CategoryService(CategoryRepository rep) {
        super();
        this.repository = rep;
    }

    public List<Category> getAll(){
        var l = new ArrayList<Category>();
        for(Category r : repository.findAll())
        {
            l.add(r);
        }
        return l;
    }

    public Category get(Integer id){
        return repository.findById(id).get();
    }

    public void save(Category product1) {
        repository.save(product1);
    }
}

