package ru.job4j.store;


import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Body;
import ru.job4j.model.Brand;
import ru.job4j.model.Category;

import java.util.List;


@Repository
public class CarStore implements Store {
    private final SessionFactory sf;

    public CarStore(SessionFactory sf) {
        this.sf = sf;
    }


    public List<Brand> findAllBrand() {
        return this.tx(
                session -> session.createQuery("from Brand").list(),
                sf
        );
    }

    public List<Body> findAllBody() {
        return this.tx(
                session -> session.createQuery("from Body").list(),
                sf
        );
    }

    public List<Category> findAllCategory() {
        return this.tx(
                session -> session.createQuery("from Category").list(),
                sf
        );
    }

    public Brand findById(int id) {
        return this.tx(
                session -> session.get(Brand.class, id),
                sf
        );
    }
}
