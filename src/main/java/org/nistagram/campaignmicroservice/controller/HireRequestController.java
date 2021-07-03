package org.nistagram.campaignmicroservice.controller;

import org.nistagram.campaignmicroservice.data.dto.HireRequestDto;
import org.nistagram.campaignmicroservice.exceptions.RequestAlreadyCreatedException;
import org.nistagram.campaignmicroservice.service.IHireRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/hireRequest", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class HireRequestController {

    private final IHireRequestService hireRequestService;

    public HireRequestController(IHireRequestService hireRequestService) {
        this.hireRequestService = hireRequestService;
    }

    @PostMapping(path = "/")
    public ResponseEntity<String> create(@RequestBody HireRequestDto dto) {
        try {
            hireRequestService.create(dto);
            return new ResponseEntity<>("Hire request created successfully!", HttpStatus.OK);

        }catch (RequestAlreadyCreatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Hire request couldn't be created!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<HireRequestDto>> getAll() {
        try {
            var dtos = hireRequestService.getAll();
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/accept/{id}")
    public ResponseEntity<String> accept(@PathVariable Long id) {
        try {
            hireRequestService.accept(id);
            return new ResponseEntity<>("Request accepted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/reject/{id}")
    public ResponseEntity<String> reject(@PathVariable Long id) {
        try {
            hireRequestService.reject(id);
            return new ResponseEntity<>("Request rejected!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
