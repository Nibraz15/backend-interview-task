package com.collective.powerplant.service;

import com.collective.powerplant.model.request.GetByLocationRequest;
import com.collective.powerplant.model.request.GetNHighOutputAndNLowOutPutPlantsRequest;
import com.collective.powerplant.model.request.PowerPlantBatchSaveRequest;
import com.collective.powerplant.model.response.FindByLocationResponse;
import com.collective.powerplant.model.response.GetNHighOutputAndNLowOutPutPlantsResponse;
import com.collective.powerplant.model.PowerPlant;

import javax.persistence.LockModeType;
import java.util.List;

public interface PowerPlantService {

    PowerPlant getByName(String name);
    PowerPlant save(PowerPlant powerPlant);
    List<PowerPlant> batchSave(PowerPlantBatchSaveRequest powerPlantBatchSaveRequest);
    FindByLocationResponse getByLocation(GetByLocationRequest request);
    List<PowerPlant> getAll();
    boolean exists(String name);
    GetNHighOutputAndNLowOutPutPlantsResponse getNHighOutputAndNLowOutPutPlants(GetNHighOutputAndNLowOutPutPlantsRequest request);
}
