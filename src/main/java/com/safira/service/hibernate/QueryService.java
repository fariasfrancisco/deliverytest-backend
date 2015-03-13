package com.safira.service.hibernate;

/**
 * Class intended to manage all Hibernate logic and perform all required queries.
 */

import com.safira.entities.*;
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

    public static void initialize(){
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

    public Restaurante getRestauranteById(Integer id) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(Restaurante.class)
                .add(Restrictions.eq("id", id))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Restaurante) criteria.list().get(0);
    }

    public RestauranteLogin getRestauranteLoginByUsuario(String usuario) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(RestauranteLogin.class)
                .add(Restrictions.eq("usuario", usuario))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (RestauranteLogin) criteria.list().get(0);
    }

    public List getMenusByRestauranteId(Integer id) throws HibernateException {
        criteria = session.createCriteria(Menu.class)
                .add(Restrictions.eq("restaurante.id", id))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public List getMenusByPedidoId(Integer id) throws HibernateException {
        criteria = session.createCriteria(Menu.class)
                .createAlias("pedidos", "p")
                .add(Restrictions.eq("p.id", id))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public Menu getMenuById(Integer id) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(Menu.class)
                .add(Restrictions.eq("id", id))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Menu) criteria.list().get(0);
    }

    public Usuario getUsuario(String facebookId) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(Usuario.class)
                .add(Restrictions.eq("facebookId", facebookId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Usuario) criteria.list().get(0);
    }

    public Pedido getPedidoById(Integer id) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(Pedido.class)
                .add(Restrictions.eq("id", id))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Pedido) criteria.list().get(0);
    }

    public List getPedidosByRestauranteIdAndUsuarioId(Integer restauranteid, Integer usuarioId) throws HibernateException {
        criteria = session.createCriteria(Pedido.class)
                .add(Restrictions.eq("restaurante.id", restauranteid))
                .add(Restrictions.eq("usuario.id", usuarioId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public List getPedidosByRestauranteId(Integer id) throws HibernateException {
        criteria = session.createCriteria(Pedido.class)
                .add(Restrictions.eq("restaurante.id", id))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public List getPedidosByUsuarioId(Integer id) throws HibernateException {
        criteria = session.createCriteria(Pedido.class)
                .add(Restrictions.eq("usuario.id", id))
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