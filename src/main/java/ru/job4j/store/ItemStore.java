package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Item;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Repository
public class ItemStore {
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
                }
        );
    }

    public void update(int id, Item item) {
        this.tx(
                session -> {
                    item.setId(id);
                    item.setCreated(new Date());
                    session.update(item);
                    return null;
                }
        );
    }

    public void delete(int id) {
        this.tx(
                session -> {
                    Item ads = new Item();
                    ads.setId(id);
                    session.delete(ads);
                    return null;
                }
        );
    }

    public List<Item> findAll() {
        return this.tx(
                session -> {
                    final List result = session.createQuery("from Item").list();
                    return result;
                }
        );
    }

    public Item findById(int id) {
        return this.tx(
                session -> {
                    final Item result = session.get(Item.class, id);
                    return result;
                }
        );
    }

    public List<Item> findByBrand(int brandId) {
        return this.tx(
                session -> session.createQuery(
                                "select distinct i from Item i "
                                        + "join fetch i.brand b "
                                        + "where b.id = :sId", Item.class)
                        .setParameter("sId", brandId)
                        .list()
        );
    }

    public List<Item> findByBody(int bodyId) {
        return this.tx(
                session -> session.createQuery("SELECT distinct i from Item i"
                                + " join fetch i.body "
                                + "where i.body.id= :cBody")
                        .setParameter("cBody", bodyId)
                        .list()

        );
    }

    public List<Item> findByCategory(int categoryId) {
        return this.tx(
                session -> session.createQuery("SELECT distinct i from Item i"
                                + " join fetch i.category "
                                + "where i.category.id= :cCategory")
                        .setParameter("cCategory", categoryId)
                        .list()

        );
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
