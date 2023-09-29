package com.inn.cafe.JWT;

import com.inn.cafe.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//import static java.lang.Object.*;
import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service

public class CustomerUsersDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;
// creating a bean of this user
    private com.inn.cafe.POJO.User userDetail;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername{}",username);
        userDetail = userDao.findByEmailId(username); // to verify of the user exist on the database or not
        if(!Objects.isNull(userDetail))
            return new User(userDetail.getEmail(),userDetail.getPassword(), new ArrayList<>());
        else // we want to throw the exception
            throw new UsernameNotFoundException("user not found.");


    }
    // method to return user detail if we need in any case
    public com.inn.cafe.POJO.User getUserDetail(){


        return userDetail;
    }
}
