package com.safira.service.Hibernate;

/**
 * Created by Francisco on 25/02/2015.
 */

import com.safira.entities.*;
import org.hibernate.Criteria;
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

    public List getRestaurantes() {
        criteria = session.createCriteria(Restaurante.class);
        return criteria.list();
    }

    public Restaurante getRestauranteById(Integer id) {
        criteria = session.createCriteria(Restaurante.class)
                .add(Restrictions.eq("id", id));
        return (Restaurante) criteria.list().get(0);
    }

    public RestauranteLogin getRestauranteLoginByUsuario(String usuario) {
        criteria = session.createCriteria(RestauranteLogin.class)
                .add(Restrictions.eq("usuario", usuario));
        return (RestauranteLogin) criteria.list().get(0);
    }

    public List getMenusByRestauranteId(Integer id) {
        criteria = session.createCriteria(Menu.class)
                .add(Restrictions.eq("restaurante.id", id));
        return criteria.list();
    }

    public List getMenusForPedido(Integer id) {
        criteria = session.createCriteria(Menu.class)
                .createAlias("pedidos", "p")
                .add(Restrictions.eq("p.id", id));
        return criteria.list();
    }

    public Menu getMenuById(Integer id) {
        criteria = session.createCriteria(Menu.class)
                .add(Restrictions.eq("id", id));
        return (Menu) criteria.list().get(0);
    }

    public Usuario getUsuario(String facebookId) {
        criteria = session.createCriteria(Usuario.class)
                .add(Restrictions.eq("facebookId", facebookId));
        return (Usuario) criteria.list().get(0);
    }

    public List getPedidosByRestauranteId(Integer id) {
        criteria = session.createCriteria(Pedido.class)
                .add(Restrictions.eq("restaurante.id", id));
        return criteria.list();
    }

    public List getPedidosByUsuarioId(Integer id) {
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