package com.collective.powerplant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Generated
public class User {


    private String user;
    private String password;
    private String token;

}
