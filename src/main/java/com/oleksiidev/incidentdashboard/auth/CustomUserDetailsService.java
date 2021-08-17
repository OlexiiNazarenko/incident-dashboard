package com.oleksiidev.incidentdashboard.auth;

import com.oleksiidev.incidentdashboard.exceptions.ResourceNotFoundException;
import com.oleksiidev.incidentdashboard.model.User;
import com.oleksiidev.incidentdashboard.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    @Transactional
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        return CustomUserDetails.fromUserEntityToCustomUserDetails(user);
    }


    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userService.findUserById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}
