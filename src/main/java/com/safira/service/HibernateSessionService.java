package com.safira.service;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Created by Francisco on 21/02/2015.
 */
public class HibernateSessionService {
    private static SessionFactory sessionFactory;

    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            createSessionFactory();
        }
        return sessionFactory;
    }

    public static void shutDown() {
        if (!sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
