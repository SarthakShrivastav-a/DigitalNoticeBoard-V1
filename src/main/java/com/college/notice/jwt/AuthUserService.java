package com.college.notice.jwt;

import com.college.notice.entity.AUser;
import com.college.notice.repository.AUserRepository;
import com.college.notice.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService implements UserDetailsService {

    @Autowired
    private AUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found "+ email));
        return User.withUsername(user.getEmail())
                .password(user.getHashedPassword())
                .roles(user.getRole())
                .build();
        //this can also be written as
        /*
        * return new User(
        *                   authUser.getEmail(),
        *                   authUser.getHashedPassword(),
        * */
    }
}
