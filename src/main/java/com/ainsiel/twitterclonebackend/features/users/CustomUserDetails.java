package com.ainsiel.twitterclonebackend.features.users;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {
    public String getName();
    public Integer getId();
    public String getRole();
}
