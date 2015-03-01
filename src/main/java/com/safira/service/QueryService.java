package com.safira.service;

import com.safira.entities.Menu;
import com.safira.entities.Pedido;
import com.safira.entities.Restaurante;
import com.safira.entities.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by Francisco on 25/02/2015.
 */
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

    public List GetRestaurante(Integer id){
        criteria = session.createCriteria(Restaurante.class)
                .add(Restrictions.eq("id", id));
        return criteria.list();
    }

    public List GetMenuForRestaurante(Integer id) {
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

    public List GetUsuario(String facebookId) {
        criteria = session.createCriteria(Usuario.class)
                .add(Restrictions.eq("facebookId", facebookId));
        return criteria.list();
    }

    public List GetPedidosForRestaurante(Integer id) {
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
