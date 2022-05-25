package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Car;
import ru.job4j.model.Driver;
import ru.job4j.model.Engine;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        List<Driver> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Engine engine = Engine.of(2);
            session.save(engine);

            Driver one = Driver.of("Olga");
            session.save(one);

            Driver two = Driver.of("Lena");
            session.save(two);

            Driver three = Driver.of("Natasha");
            session.save(three);


            Car first = Car.of("ford", engine);
            first.getDrivers().add(one);
            first.getDrivers().add(two);

            Car second = Car.of("mazda", engine);
            second.getDrivers().add(three);
            second.getDrivers().add(one);

            session.persist(first);
            session.persist(second);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}


