package org.nistagram.campaignmicroservice.service.impl;

import org.nistagram.campaignmicroservice.data.dto.EditUserDto;
import org.nistagram.campaignmicroservice.data.model.Role;
import org.nistagram.campaignmicroservice.data.model.User;
import org.nistagram.campaignmicroservice.data.repository.RoleRepository;
import org.nistagram.campaignmicroservice.data.repository.UserRepository;
import org.nistagram.campaignmicroservice.exceptions.UserDoesNotExistException;
import org.nistagram.campaignmicroservice.exceptions.UsernameAlreadyExistsException;
import org.nistagram.campaignmicroservice.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveUser(User user) throws UsernameAlreadyExistsException {
        if (isUsernameAvailable(user.getUsername())) {
            List<Role> roles = roleRepository.findByName(user.getAdministrationRole());
            user.setRoles(roles);
            userRepository.save(user);
        } else {
            throw new UsernameAlreadyExistsException();
        }
    }

    @Override
    public void updateUser(EditUserDto editUserDto) {
        User user = getCurrentlyLoggedUser();
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        if (!editUserDto.getOldUsername().equals(editUserDto.getUsername()) && !isUsernameAvailable(editUserDto.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }
        user.setUsername(editUserDto.getUsername());
        user.setProfilePrivate(editUserDto.isProfilePrivate());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    private boolean isUsernameAvailable(String username) {
        User user = userRepository.findByUsername(username);
        return user == null;
    }

    private User getCurrentlyLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
