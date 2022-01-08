package com.collective.powerplant.model;

import com.collective.powerplant.model.util.GeneratorList;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated
public class PowerPlant {

    private String powerPlantName;
    private String location;
    private int year;
    private GeneratorList generators;
    private int powerOutPut;
    private float powerOutputPercentage;
}
