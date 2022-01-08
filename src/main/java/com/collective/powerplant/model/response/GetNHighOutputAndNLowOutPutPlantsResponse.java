package com.collective.powerplant.model.response;

import com.collective.powerplant.model.PowerPlant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Generated
public class GetNHighOutputAndNLowOutPutPlantsResponse {
    private List<PowerPlant> highOutputPlants;
    private List<PowerPlant> lowOutputPlants;
}
