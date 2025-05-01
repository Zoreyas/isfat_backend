package org.erusakov.backend.service;

import jakarta.validation.Valid;
import org.erusakov.backend.controller.request.user.*;
import org.erusakov.backend.controller.response.user.UserResponse;
import org.erusakov.backend.entities.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    Long create(CreateUserRequest createUserRequest);

    @Transactional
    UserResponse findById(Long id);

    @Transactional
    Page<UserResponse> findAll(Pageable pageable);

    @Transactional
    UserEntity findByLoginOrThrow(String username) throws UsernameNotFoundException;

    List<UserResponse> findUsersByOrganizationId(Long organizationId);

    void update(Long id, @Valid UpdateUserRequest updateUser);

    void updateEmail(Long id, @Valid UpdateUserEmailRequest request, String login);

    void updatePassword(Long id, @Valid UpdateUserPasswordRequest request, String login);

    void updateLogin(Long id, @Valid UpdateUserLoginRequest request, String login);

    @Transactional
    void addRole(Long id, Long roleId);

    void delete(Long id);

    @Transactional
    void removeRole(Long id, Long roleId);
}
