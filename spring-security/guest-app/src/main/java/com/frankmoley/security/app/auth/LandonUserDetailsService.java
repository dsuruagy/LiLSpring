package com.frankmoley.security.app.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LandonUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthGroupRepository authGroupRepository;

    public LandonUserDetailsService(UserRepository userRepository, AuthGroupRepository authGroupRepository) {
        super();
        this.userRepository = userRepository;
        this.authGroupRepository = authGroupRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        List<AuthGroup> authGroups = authGroupRepository.findByUsername(s);

        if(user == null) {
            throw new UsernameNotFoundException("username not found");
        }

        return new LandonUserPrincipal(user, authGroups);
    }
}
