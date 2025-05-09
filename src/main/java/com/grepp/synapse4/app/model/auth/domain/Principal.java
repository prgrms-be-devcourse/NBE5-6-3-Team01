package com.grepp.synapse4.app.model.auth.domain;

import com.grepp.synapse4.app.model.samplemember.entity.SampleMember;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class Principal extends User {
    
    public Principal(String username, String password,
        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    
    public static Principal createPrincipal(SampleMember sampleMember,
                                            List<SimpleGrantedAuthority> authorities){
        return new Principal(sampleMember.getUserId(), sampleMember.getPassword(), authorities);
    }
}
