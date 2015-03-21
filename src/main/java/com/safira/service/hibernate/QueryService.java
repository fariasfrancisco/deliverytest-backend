package com.safira.service.hibernate;

/**
 * Class intended to manage all Hibernate logic and perform all required queries.
 */

import com.safira.domain.entities.*;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class QueryService {

    private static QueryService queryService;

    private Session session;
    private Transaction transaction;
    private Criteria criteria;

    private QueryService() {
    }

    public static QueryService getQueryService() {
        if (queryService == null) {
            queryService = new QueryService();
            queryService.session = HibernateSessionService.getSessionFactory().openSession();
            queryService.transaction = queryService.session.getTransaction();
            return queryService;
        }
        queryService.session = HibernateSessionService.getSessionFactory().openSession();
        queryService.transaction = queryService.session.getTransaction();
        return queryService;
    }

    public static void initialize() {
        HibernateSessionService.createSessionFactory();
        HibernateSessionService.getSessionFactory().openSession();
    }

    public void closeSession() {
        session.close();
    }

    public List<Restaurante> getRestaurantes() throws HibernateException {
        criteria = session.createCriteria(Restaurante.class);
        return criteria.list();
    }

    public Restaurante getRestauranteByUuid(String uuid) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(Restaurante.class)
                .add(Restrictions.eq("uuid", uuid))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Restaurante) criteria.list().get(0);
    }

    public RestauranteLogin getRestauranteLoginByUsuario(String usuario) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(RestauranteLogin.class)
                .add(Restrictions.eq("usuario", usuario))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (RestauranteLogin) criteria.list().get(0);
    }

    public List getMenusByRestauranteUuid(String uuid) throws HibernateException {
        criteria = session.createCriteria(Menu.class)
                .add(Restrictions.eq("restaurante.uuid", uuid))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public List getMenusByPedidoUuid(String uuid) throws HibernateException {
        criteria = session.createCriteria(Menu.class)
                .createAlias("pedidos", "p")
                .add(Restrictions.eq("p.uuid", uuid))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public Menu getMenuByUuid(String uuid) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(Menu.class)
                .add(Restrictions.eq("uuid", uuid))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Menu) criteria.list().get(0);
    }

    public Usuario getUsuarioByUuid(String uuid) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(Usuario.class)
                .add(Restrictions.eq("uuid", uuid))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Usuario) criteria.list().get(0);
    }

    public Pedido getPedidoByUuid(String uuid) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(Pedido.class)
                .add(Restrictions.eq("uuid", uuid))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Pedido) criteria.list().get(0);
    }

    public List getPedidosByRestauranteAndUsuario(String restauranteUUID, String usuarioUUID) throws HibernateException {
        criteria = session.createCriteria(Pedido.class)
                .add(Restrictions.eq("restaurante.uuid", restauranteUUID))
                .add(Restrictions.eq("usuario.uuid", usuarioUUID))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public List getPedidosByRestaurante(String uuid) throws HibernateException {
        criteria = session.createCriteria(Pedido.class)
                .add(Restrictions.eq("restaurante.uuid", uuid))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public List getPedidosByUsuario(String uuid) throws HibernateException {
        criteria = session.createCriteria(Pedido.class)
                .add(Restrictions.eq("usuario.uuid", uuid))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public void insertObject(Object object) {
        transaction.begin();
        session.save(object);
        transaction.commit();
    }

    public void insertObjects(List<Object> objects) {
        transaction.begin();
        for (Object object : objects) {
            session.save(object);
        }
        transaction.commit();
    }

    private static class HibernateSessionService {
        private static SessionFactory sessionFactory;

        private static SessionFactory createSessionFactory() {
            Configuration configuration = new Configuration().configure();
            StandardServiceRegistryBuilder builder =
                    new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
            return sessionFactory;
        }

        public static SessionFactory getSessionFactory() {
            if (sessionFactory == null) {
                createSessionFactory();
            }
            if (sessionFactory.isClosed()) {
                sessionFactory.openSession();
            }
            return sessionFactory;
        }

        public static void shutDown() {
            if (!sessionFactory.isClosed()) {
                sessionFactory.close();
            }
        }
    }
}
