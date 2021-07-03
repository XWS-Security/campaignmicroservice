package org.nistagram.campaignmicroservice.exceptions;

public class RequestAlreadyCreatedException extends RuntimeException{
    public RequestAlreadyCreatedException(){
        super("Requset is already sent!");
    }
}
