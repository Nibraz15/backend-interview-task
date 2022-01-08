package com.collective.powerplant.model.database;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Generated
public class PowerPlantTable {

    @Id
    private String powerPlantName;
    private String location;
    private int year;
    @Column(columnDefinition="TEXT")
    private String generators;
    private int powerOutPut;


}
