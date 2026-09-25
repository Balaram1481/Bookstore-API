package com.bookstore.api.Security;
import com.bookstore.api.entity.User;
import com.bookstore.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Set;
@Service 
public class CustomuserDetailsService implements UserDetailsService {
    @Autowired 
    private UserRepository userRepository;

    @Override 
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(()-> new UsernameNotFoundException("User not Found with email: "+ email));
        String roleName=user.getRole().name();
        if(!roleName.startsWith("ROLE_")){
            roleName="ROLE_"+ roleName;
        }
        Set<GrantedAuthority> authorities=Collections.singleton(
            new SimpleGrantedAuthority(roleName)
        );
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);

    }


}
