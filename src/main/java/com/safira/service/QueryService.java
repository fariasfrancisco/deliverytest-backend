package com.safira.service;

import com.safira.entities.Menu;
import com.safira.entities.Restaurante;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by Francisco on 25/02/2015.
 */
public class QueryService {

    private Session session;
    private Criteria criteria;

    public QueryService() {
        this.session = HibernateSessionService.getSessionFactory().openSession();;
    }

    public List GetRestaurants() {
        criteria = session.createCriteria(Restaurante.class);
        return criteria.list();
    }

    public List GetMenuForRestaurant(Integer id) {
        criteria = session.createCriteria(Menu.class)
                .add(Restrictions.eq("restaurante.id", id));
        return criteria.list();
    }
}
