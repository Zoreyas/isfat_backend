package org.erusakov.backend.mapper;

import org.erusakov.backend.entities.user.RoleEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleMapper {

    public GrantedAuthority toGrantedAuthority(RoleEntity roleEntity) {
        return new SimpleGrantedAuthority(roleEntity.getAuthority());
    }

    public List<GrantedAuthority> toGrantedAuthorityList(List<RoleEntity> entityList) {
        return entityList
                .stream()
                .map(this::toGrantedAuthority)
                .toList();
    }

}
