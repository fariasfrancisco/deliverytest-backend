package com.safira.service;

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

    public List GetRestaurantes() {
        criteria = session.createCriteria(Restaurante.class);
        return criteria.list();
    }

    public List GetRestaurante(Integer id) {
        criteria = session.createCriteria(Restaurante.class)
                .add(Restrictions.eq("id", id));
        return criteria.list();
    }

    public List GetRestauranteLoginByUsuario(String usuario) {
        criteria = session.createCriteria(RestauranteLogin.class)
                .add(Restrictions.eq("usuario", usuario));
        return criteria.list();
    }

    public List GetMenuByRestauranteId(Integer id) {
        criteria = session.createCriteria(Menu.class)
                .add(Restrictions.eq("restaurante.id", id));
        return criteria.list();
    }

    public List GetMenusForPedido(Integer id) {
        criteria = session.createCriteria(Menu.class)
                .createAlias("pedidos", "p")
                .add(Restrictions.eq("p.id", id));
        return criteria.list();
    }

    public List GetUsuarios() {
        criteria = session.createCriteria(Usuario.class);
        return criteria.list();
    }

    public List GetUsuario(String facebookId) {
        criteria = session.createCriteria(Usuario.class)
                .add(Restrictions.eq("facebookId", facebookId));
        return criteria.list();
    }

    public List<Usuario> GetUsuario(int id) {
        criteria = session.createCriteria(Usuario.class)
                .add(Restrictions.eq("id", id));
        return criteria.list();
    }

    public List GetPedidosByRestauranteId(Integer id) {
        criteria = session.createCriteria(Pedido.class)
                .add(Restrictions.eq("restaurante.id", id));
        return criteria.list();
    }

    public void InsertObject(Object object) {
        transaction.begin();
        session.save(object);
        transaction.commit();
    }
}