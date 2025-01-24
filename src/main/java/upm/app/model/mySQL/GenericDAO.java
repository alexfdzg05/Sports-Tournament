package upm.app.model.mySQL;

import org.hibernate.Session;
import org.hibernate.Transaction;
import upm.app.model.User;

import java.util.List;

public class GenericDAO<T> {
    private final Class<T> entityType;

    public GenericDAO(Class<T> entityType) {
        this.entityType = entityType;
    }
    public void save(T entity){
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
    }
    public void update(T entity){
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
    }
    public void delete(T entity){
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
    }
    public T findByID(String id){
        Session session = HibernateUtility.getSessionFactory().openSession();
        T entity = session.get(entityType, id);
        session.close();
        return entity;
    }

    public List<T> findAll(){
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<T> entities = session.createQuery("FROM " + entityType.getSimpleName(), entityType).list();
        session.close();
        return entities;
    }
}
