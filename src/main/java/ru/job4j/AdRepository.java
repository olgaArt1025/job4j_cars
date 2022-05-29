package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.model.Model;
import ru.job4j.model.Post;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.function.Function;

@Repository
public class AdRepository {
    private final SessionFactory sf;

    public AdRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Post> adsForToday() {
        return this.tx(
                session -> session.createQuery(
                                "select p from Post p where p.created >= current_date ", Post.class)
                        .list()
        );
    }

    public List<Post> adsForPhotos() {
        return this.tx(
                session -> session.createQuery(
                                "select p from Post p where p.photo != null", Post.class)
                        .list()
        );
    }

    public List<Post> adsFromTheBrand(Model model) {
        return this.tx(
                session -> session.createQuery(
                        "select distinct st from Post st "
                                + "join fetch st.model a "
                                + "where a.id = :sId", Post.class)
                        .setParameter("sId", model.getId())
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
