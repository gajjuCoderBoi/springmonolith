package com.ga.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ga.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> listUsers() {
        List<User> list = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            list = session.createQuery("FROM User").getResultList();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public User signup(User user) {
        Session session = sessionFactory.getCurrentSession();
        try{
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }finally {
            session.close();
        }
        return user;
    }

    @Override
    public User Login(User user) {
        Session session = sessionFactory.getCurrentSession();
        User savedUser;
        try{
            session.getTransaction();
            savedUser = (User)session.createQuery("FROM User u WHERE u.username = '" +
                    user.getUsername() + "' AND u.password = '" +
                    user.getPassword() + "'").getSingleResult();
        }finally {
            session.close();
        }
        return savedUser;
    }
}