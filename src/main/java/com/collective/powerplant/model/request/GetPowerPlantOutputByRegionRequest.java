package com.collective.powerplant.model.request;

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
@Generated
public class GetPowerPlantOutputByRegionRequest {

    private List<String> regions;
    private boolean getForAllRegions;
}
