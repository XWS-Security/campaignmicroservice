package org.nistagram.campaignmicroservice.data.dto;

import java.io.Serializable;

public class UsernameNumbersDto implements Serializable {
    String username;
    Integer quantity;

    public UsernameNumbersDto() {
    }

    public UsernameNumbersDto(String username,Integer quantity) {
        this.username = username;
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
