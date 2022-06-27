package lk.ijse.HostelManagementSystem.repository.custom.impl;

import lk.ijse.HostelManagementSystem.entity.Student;
import lk.ijse.HostelManagementSystem.entity.User;
import lk.ijse.HostelManagementSystem.repository.custom.UserDAO;
import lk.ijse.HostelManagementSystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean add(User entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String username) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        User user = session.load(User.class, username);
        session.delete(user);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean exist(String email) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT email FROM User WHERE email = : email_address ";
        Query query = session.createQuery(hql);
        query.setParameter("email_address",email);
        List<String> emailAddressList = query.list();

        transaction.commit();
        session.close();

        return (emailAddressList.size()>0) ? true : false;
    }

    @Override
    public List<User> find(String username) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM User WHERE userName = : user_name ";
        Query query = session.createQuery(hql);
        query.setParameter("user_name",username);
        List<User> userList = query.list();

        transaction.commit();
        session.close();
        return userList;
    }

    @Override
    public List<User> findAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql ="FROM User ";
        Query query = session.createQuery(hql);
        List<User> userList = query.list();

        transaction.commit();
        session.close();
        return userList;
    }

}
