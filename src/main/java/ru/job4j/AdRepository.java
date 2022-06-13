package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.model.Brand;
import ru.job4j.model.Item;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.function.Function;

@Repository
public class AdRepository {
    private final SessionFactory sf;

    public AdRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Item> adsForToday() {
        return this.tx(
                session -> session.createQuery(
                                "select p from Item p where p.created >= current_date ", Item.class)
                        .list()
        );
    }

    public List<Item> adsForPhotos() {
        return this.tx(
                session -> session.createQuery(
                                "select p from Item p where p.photo != null", Item.class)
                        .list()
        );
    }


    public List<Item> adsFromTheBrand(Brand brand) {
        return this.tx(
                session -> session.createQuery(
                        "select distinct st from Item st "
                                + "join fetch st.brand a "
                                + "where a.id = :sId", Item.class)
                        .setParameter("sId", brand.getId())
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
