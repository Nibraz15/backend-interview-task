package com.collective.powerplant.apis;

import com.collective.powerplant.logic.CreatePowerPlant;
import com.collective.powerplant.logic.GetPowerPlantOutputByRegion;
import com.collective.powerplant.model.request.GetByLocationRequest;
import com.collective.powerplant.model.request.GetNHighOutputAndNLowOutPutPlantsRequest;
import com.collective.powerplant.model.request.GetPowerPlantOutputByRegionRequest;
import com.collective.powerplant.model.request.PowerPlantBatchSaveRequest;
import com.collective.powerplant.model.response.FindByLocationResponse;
import com.collective.powerplant.model.response.GetNHighOutputAndNLowOutPutPlantsResponse;
import com.collective.powerplant.model.PowerPlant;
import com.collective.powerplant.model.response.GetPowerPlantOutputByRegionResponse;
import com.collective.powerplant.service.PowerPlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/powerPlant")
@RequiredArgsConstructor
@Slf4j
public class PowerPlantApi {

    private final PowerPlantService powerPlantService;
    private final GetPowerPlantOutputByRegion powerPlantOutputByRegion;
    private final CreatePowerPlant createPowerPlant;

    @GetMapping("/get/{name}")
    public PowerPlant getByName(@PathVariable("name") String name) {
        return  powerPlantService.getByName(name);
    }

    @GetMapping("/getAll")
    public List<PowerPlant> getAll() {
        return  powerPlantService.getAll();
    }

    @PostMapping("/save")
    public PowerPlant save(@RequestBody PowerPlant powerPlant){
        return createPowerPlant.create(powerPlant);
    }

    @PostMapping("/batchSave")
    public List<PowerPlant> batchSave(@RequestBody PowerPlantBatchSaveRequest request){
        return powerPlantService.batchSave(request);
    }

    @GetMapping("/getByLocation")
    public FindByLocationResponse getByLocation(@RequestBody GetByLocationRequest request) {
        return  powerPlantService.getByLocation(request);
    }

    @GetMapping("/getNSorted")
    public GetNHighOutputAndNLowOutPutPlantsResponse getNSorted(@RequestBody GetNHighOutputAndNLowOutPutPlantsRequest request){
        log.info("request : {}", request);
        return powerPlantService.getNHighOutputAndNLowOutPutPlants(request);
    }

    @GetMapping("/getPowerOutPutByLocation")
    public List<GetPowerPlantOutputByRegionResponse> getPowerOutPutByLocation(@RequestBody GetPowerPlantOutputByRegionRequest request) {
        return  powerPlantOutputByRegion.getPowerPlantOutputByRegion(request);
    }



}
