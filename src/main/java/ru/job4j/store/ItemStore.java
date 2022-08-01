package ru.job4j.store;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Item;

import java.util.Date;
import java.util.List;

@Repository
public class ItemStore implements Store {
    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Item add(Item item) {
        return this.tx(
                session -> {
                    item.setCreated(new Date());
                    item.setSale(true);
                    session.save(item);
                    return item;
                },
                sf
        );
    }

    public void update(int id, Item item) {
        this.tx(
                session -> {
                    item.setId(id);
                    item.setCreated(new Date());
                    session.update(item);
                    return null;
                },
                sf
        );
    }

    public void delete(int id) {
        this.tx(
                session -> {
                    Item ads = new Item();
                    ads.setId(id);
                    session.delete(ads);
                    return null;
                },
                sf
        );
    }

    public List<Item> findAll() {
        return this.tx(
                session -> session.createQuery("from Item").list(),
                sf
        );
    }

    public Item findById(int id) {
        return this.tx(
                session -> session.get(Item.class, id),
                sf
        );
    }

    public List<Item> findByBrand(int brandId) {
        return this.tx(
                session -> session.createQuery(
                                "select distinct i from Item i "
                                        + "join fetch i.brand b "
                                        + "where b.id = :sId", Item.class)
                        .setParameter("sId", brandId)
                        .list(),
                sf
        );
    }

    public List<Item> findByBody(int bodyId) {
        return this.tx(
                session -> session.createQuery("SELECT distinct i from Item i"
                                + " join fetch i.body "
                                + "where i.body.id= :cBody")
                        .setParameter("cBody", bodyId)
                        .list(),
                sf

        );
    }

    public List<Item> findByCategory(int categoryId) {
        return this.tx(
                session -> session.createQuery("SELECT distinct i from Item i"
                                + " join fetch i.category "
                                + "where i.category.id= :cCategory")
                        .setParameter("cCategory", categoryId)
                        .list(),
                sf
        );
    }
}
