package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSession();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        try (var session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.createNativeQuery("""
                CREATE TABLE IF NOT EXISTS users 
                (id BIGINT PRIMARY KEY AUTO_INCREMENT, 
                name VARCHAR (10), 
                 lastName VARCHAR (20), 
                age TINYINT)
                """
            ).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана!");
        }catch (HibernateException e) {
            e.printStackTrace();
            if(session != null)
                session.beginTransaction().rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {

        try (var session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
                session.getTransaction().commit();
                System.out.println("Таблица удалена");
            } catch (HibernateException e) {
                e.printStackTrace();
                if (session != null)
                    session.beginTransaction().rollback();
            }
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (var session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.save(new User(name, lastName, age));
                session.getTransaction().commit();
                System.out.println("User сохранен");
            } catch (HibernateException e) {
                e.printStackTrace();
                if (session != null) {
                    session.beginTransaction().rollback();
                }
            }
        }
    }
    @Override
    public void removeUserById(long id) {

        try (var session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.delete(session.get(User.class, id));
                session.getTransaction().commit();
                System.out.println("User удалён");
            } catch (HibernateException e) {
                e.printStackTrace();
                if (session != null)
                    session.beginTransaction().rollback();
            }
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        try (var session = sessionFactory.openSession()) {
            try {
                return session.createQuery("from User ").getResultList();
            } catch (HibernateException e) {
                e.printStackTrace();
                if (session != null) {
                    session.beginTransaction().rollback();
                }
            }

        }
        return userList;
    }
    @Override
    public void cleanUsersTable() {

        try (var session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.createNativeQuery("TRUNCATE TABLE mybd.users;").executeUpdate();
                session.getTransaction().commit();
                System.out.println("Таблица очищена");
            } catch (HibernateException e) {
                e.printStackTrace();
                if (session != null) {
                    session.beginTransaction().rollback();
                }
            }
        }
    }
}