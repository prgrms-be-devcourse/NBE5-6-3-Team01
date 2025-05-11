package com.grepp.synapse4.app.model.auth.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class Principal extends User {
    
    public Principal(String username, String password,
        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
//
//    public static Principal createPrincipal(SampleMember sampleMember,
//                                            List<SimpleGrantedAuthority> authorities){
//        return new Principal(sampleMember.getUserId(), sampleMember.getPassword(), authorities);
//    }
}
