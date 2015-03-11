package com.safira.service.hibernate;

/**
 * Created by Francisco on 25/02/2015.
 */

import com.safira.entities.*;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class QueryService {

    private Session session;
    private Transaction transaction;
    private Criteria criteria;

    public QueryService() {
        this.session = HibernateSessionService.getSessionFactory().openSession();
        this.transaction = session.getTransaction();
    }

    public List getRestaurantes() throws HibernateException {
        criteria = session.createCriteria(Restaurante.class);
        return criteria.list();
    }

    public Restaurante getRestauranteById(Integer id) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(Restaurante.class)
                .add(Restrictions.eq("id", id));
        return (Restaurante) criteria.list().get(0);
    }

    public RestauranteLogin getRestauranteLoginByUsuario(String usuario) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(RestauranteLogin.class)
                .add(Restrictions.eq("usuario", usuario));
        return (RestauranteLogin) criteria.list().get(0);
    }

    public List getMenusByRestauranteId(Integer id) throws HibernateException {
        criteria = session.createCriteria(Menu.class)
                .add(Restrictions.eq("restaurante.id", id));
        return criteria.list();
    }

    public List getMenusByPedidoId(Integer id) throws HibernateException {
        criteria = session.createCriteria(Menu.class)
                .createAlias("pedidos", "p")
                .add(Restrictions.eq("p.id", id));
        return criteria.list();
    }

    public Menu getMenuById(Integer id) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(Menu.class)
                .add(Restrictions.eq("id", id));
        return (Menu) criteria.list().get(0);
    }

    public Usuario getUsuario(String facebookId) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(Usuario.class)
                .add(Restrictions.eq("facebookId", facebookId));
        return (Usuario) criteria.list().get(0);
    }

    public Pedido getPedidoById(Integer id) throws HibernateException, IndexOutOfBoundsException {
        criteria = session.createCriteria(Pedido.class)
                .add(Restrictions.eq("id", id));
        return (Pedido) criteria.list().get(0);
    }

    public List getPedidosByRestauranteIdAndUsuarioId(Integer restauranteid, Integer usuarioId) throws HibernateException {
        criteria = session.createCriteria(Pedido.class)
                .add(Restrictions.eq("restaurante.id", restauranteid))
                .add(Restrictions.eq("usuario.id", usuarioId));
        return criteria.list();
    }

    public List getPedidosByRestauranteId(Integer id) throws HibernateException {
        criteria = session.createCriteria(Pedido.class)
                .add(Restrictions.eq("restaurante.id", id));
        return criteria.list();
    }

    public List getPedidosByUsuarioId(Integer id) throws HibernateException {
        criteria = session.createCriteria(Pedido.class)
                .add(Restrictions.eq("usuario.id", id));
        return criteria.list();
    }

    public void insertObject(Object object) {
        transaction.begin();
        session.save(object);
        transaction.commit();
    }
}