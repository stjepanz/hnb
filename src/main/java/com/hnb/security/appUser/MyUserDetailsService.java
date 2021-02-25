package com.hnb.security.appUser;

import com.hnb.security.dao.MyUser;
import com.hnb.security.dao.MyUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("myUDS")
public class MyUserDetailsService implements UserDetailsService {

    private final MyUserDao myUserDao;

    @Autowired
    public MyUserDetailsService(MyUserDao myUserDao) {
        this.myUserDao = myUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = myUserDao.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Username " +username+ " not present in the database."));

        return new PrincipleUser(user.get());
    }
}
