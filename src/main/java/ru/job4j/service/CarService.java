package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Body;
import ru.job4j.model.Brand;
import ru.job4j.model.Category;
import ru.job4j.store.CarStore;

import java.util.Collection;

@Service
public class CarService {
    private final CarStore store;

    public CarService(CarStore store) {
        this.store = store;
    }


    public Collection<Brand> findAllBrand() {
        return store.findAllBrand();
    }

    public Collection<Body> findAllBody() {
        return store.findAllBody();
    }

    public Collection<Category> findAllCategory() {
        return store.findAllCategory();
    }

    public Brand findById(Integer id) {
        return store.findById(id);
    }

}
