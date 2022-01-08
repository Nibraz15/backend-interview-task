package com.collective.powerplant.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Generated
public class Generator {

    @NotEmpty
    @JsonProperty("generatorId")
    private String generatorId;
    @JsonProperty("generatorStatus")
    private String generatorStatus;
    @JsonProperty("generatorPowerGeneration")
    private int generatorPowerGeneration;

}
