package com.nibm.smartmedicine.security;

import com.nibm.smartmedicine.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class CurrentAuth {
    /**
     * get current auth user
     *
     * @return
     */
    public static Optional<User> getUser() {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
            user = principal.getUser();
        }
        return Optional.ofNullable(user);
    }

    /**
     * check user is signing in
     *
     * @return
     */
    public static boolean check() {
        return getUser().isPresent();
    }

    public static boolean isVerified() {
        if (getUser().isPresent()) {
            return getUser().get().getEmailVerified();
        } else {
            return false;
        }
    }

    public static boolean throwNotVerified() {
        if (getUser().isPresent() && getUser().get().getEmailVerified()) {
            return true;
        } else {
            throw new AccessDeniedException("User was not verified.");
        }
    }
}
