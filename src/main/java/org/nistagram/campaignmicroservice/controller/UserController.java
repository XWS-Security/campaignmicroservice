package org.nistagram.campaignmicroservice.controller;

import org.modelmapper.ModelMapper;
import org.nistagram.campaignmicroservice.data.dto.EditUserDto;
import org.nistagram.campaignmicroservice.data.dto.ResponseDto;
import org.nistagram.campaignmicroservice.data.dto.UserDto;
import org.nistagram.campaignmicroservice.data.model.User;
import org.nistagram.campaignmicroservice.exceptions.UserDoesNotExistException;
import org.nistagram.campaignmicroservice.exceptions.UsernameAlreadyExistsException;
import org.nistagram.campaignmicroservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDto> createUser(@RequestBody @Valid UserDto userDto) {
        try {
            userService.saveUser(modelMapper.map(userDto, User.class));
            return new ResponseEntity<>(new ResponseDto(true, ""), HttpStatus.OK);
        } catch (UsernameAlreadyExistsException e) {
            return new ResponseEntity<>(new ResponseDto(false, e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(false, "Something went wrong."), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody @Valid UserDto userDto) {
        try {
            userService.deleteUser(modelMapper.map(userDto, User.class));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    public ResponseEntity<ResponseDto> updateUser(@RequestBody @Valid EditUserDto editUserDto) {
        try {
            userService.updateUser(editUserDto);
            return new ResponseEntity<>(new ResponseDto(true, ""), HttpStatus.OK);
        } catch (UsernameAlreadyExistsException | UserDoesNotExistException e) {
            return new ResponseEntity<>(new ResponseDto(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(false, "Something went wrong."), HttpStatus.OK);
        }
    }
}
