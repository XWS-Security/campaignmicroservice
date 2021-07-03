package org.nistagram.campaignmicroservice.service;

import org.nistagram.campaignmicroservice.data.dto.HireRequestDto;

import java.util.List;

public interface IHireRequestService {
    void create(HireRequestDto dto);

    List<HireRequestDto> getAll();

    void accept(Long id);

    void reject(Long id);
}
