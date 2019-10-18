package com.ga.service;

import java.util.ArrayList;
import java.util.List;

import com.ga.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ga.dao.UserDao;
import com.ga.entity.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("encoder")
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtUtil jwtUtil;


    @Autowired
    private UserDao userDao;


    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    public String signup(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        if(userDao.signup(user).getUserId() != null) {
            UserDetails userDetails = loadUserByUsername(user.getUsername());

            return jwtUtil.generateToken(userDetails);
        }

        return null;
    }

    @Override
    public String login(User user) {
        User foundUser = userDao.login(user);
        if(foundUser != null &&
                foundUser.getUserId() != null &&
                bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            UserDetails userDetails = loadUserByUsername(foundUser.getUsername());

            return jwtUtil.generateToken(userDetails);
        }

        return null;
    }

    @Override
    public User update(User user, Long userId) {
        return userDao.update(user, userId);
    }

    @Override
    public User delete(Long userId) {
        return userDao.delete(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.getUserByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("Unknown user: " + username);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()),
                true, true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority(user.getUserRole().getName()));

        return authorities;
    }
}