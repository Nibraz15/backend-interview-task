package com.collective.powerplant.logic;

import com.collective.powerplant.model.request.GetPowerPlantOutputByRegionRequest;
import com.collective.powerplant.model.response.GetPowerPlantOutputByRegionResponse;

import java.util.List;

public interface GetPowerPlantOutputByRegion {

    List<GetPowerPlantOutputByRegionResponse> getPowerPlantOutputByRegion(GetPowerPlantOutputByRegionRequest request);
    GetPowerPlantOutputByRegionResponse getPowerPlantOutputByRegion(String region);
    List<GetPowerPlantOutputByRegionResponse> getPowerPlantOutputForAllRegion();
}
