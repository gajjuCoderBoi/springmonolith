package com.ga.dao;

import java.util.List;

import com.ga.entity.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ga.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    UserRoleDao userRoleDao;

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
        String roleName = user.getUserRole().getName();

        UserRole userRole = userRoleDao.getRole(roleName);

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            user.setUserRole(userRole);
            session.save(user);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public User login(User user) {
        Session session = sessionFactory.getCurrentSession();
        User savedUser;
        try {
            session.getTransaction();
            savedUser = (User) session.createQuery("FROM User u WHERE u.username = '" +
                    user.getUsername() + "'").getSingleResult();
        } finally {
            session.close();
        }
        return savedUser;
    }

    @Override
    public User update(User user, Long userId) {
        Session session = sessionFactory.getCurrentSession();
        User saveduser;
        try {
            saveduser = session.get(User.class, userId);
            saveduser.setPassword(user.getPassword());
            session.update(saveduser);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return saveduser;
    }

    @Override
    public User delete(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        User deletedUser;
        try {
            deletedUser = session.get(User.class, userId);
            session.delete(deletedUser);
            session.getTransaction().commit();

        } finally {
            session.close();
        }
        return deletedUser;
    }

    @Override
    public User getUserByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        User user;
        try {
            session.beginTransaction();
            user = (User)session.createQuery("FROM User u WHERE u.username = '" +
                    username + "'").uniqueResult();
        } finally {
            session.close();
        }
        return user;
    }
}