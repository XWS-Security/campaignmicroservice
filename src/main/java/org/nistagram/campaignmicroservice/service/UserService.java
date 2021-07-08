package org.nistagram.campaignmicroservice.service;

import org.nistagram.campaignmicroservice.data.dto.CreateTokenOwnerDto;
import org.nistagram.campaignmicroservice.data.dto.EditUserDto;
import org.nistagram.campaignmicroservice.data.model.User;

public interface UserService {
    void saveUser(User user);

    void updateUser(EditUserDto editUserDto);

    void deleteUser(User user);

    void saveTokenOwner(CreateTokenOwnerDto tokenOwnerDto);
}
