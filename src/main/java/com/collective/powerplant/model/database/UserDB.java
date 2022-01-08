package com.collective.powerplant.model.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Generated
public class UserDB {

    @Id
    private String name;
    private String password;

}
