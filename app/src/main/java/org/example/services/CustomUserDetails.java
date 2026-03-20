package org.example.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.example.entities.UserInfo;
import org.example.entities.UserRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// UserDetails from spring security class - already has all methods

// Class that brings userinfo and save it in somewhere
// and there are getter, setter
public class CustomUserDetails extends UserInfo implements UserDetails{
    
    private String userName;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;
    // stores admin, user

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    // take user from db and convert it
    // model constructor
    public CustomUserDetails(UserInfo byUserName){
        this.userName = byUserName.getUsername();
        this.password = byUserName.getPassword();
        List<GrantedAuthority> auths = new ArrayList<>();

        for(UserRoles role: byUserName.getRoles()){
            auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
        // we store all roles in uppercase only
        this.authorities = auths;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername(){
        return userName;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override 
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

}


// make it compatible with Spring Security
// Spring security only understand UserDetails
// UserInfo (your DB model)
//         ↓
// CustomUserDetails
//         ↓
// Spring Security understands