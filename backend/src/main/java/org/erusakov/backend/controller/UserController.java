package org.erusakov.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.erusakov.backend.controller.request.user.*;
import org.erusakov.backend.controller.response.user.UserResponse;
import org.erusakov.backend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return userService.create(createUserRequest);
    }

    @GetMapping
    public Page<UserResponse> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/login/{login}")
    public UserResponse findById(@PathVariable String login) {
        return userService.findByLogin(login);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest updateUser) {
        userService.update(id, updateUser);
    }

    @PatchMapping("/{id}/roles/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addRole(@PathVariable Long id, @PathVariable Long roleId) {
        userService.addRole(id, roleId);
    }

    @PatchMapping("/{id}/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmail(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserEmailRequest request,
            Principal principal
    ) {
        userService.updateEmail(id, request, principal.getName());
    }

    @PatchMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserPasswordRequest request,
            Principal principal
    ) {
        userService.updatePassword(id, request, principal.getName());
    }

    @PatchMapping("/{id}/login")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLogin(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserLoginRequest request,
            Principal principal
    ) {
        userService.updateLogin(id, request, principal.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @DeleteMapping("/{id}/roles/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRole(@PathVariable Long id, @PathVariable Long roleId) {
        userService.removeRole(id, roleId);
    }

}
