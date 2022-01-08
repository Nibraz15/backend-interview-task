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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/get")
    public ResponseEntity<PowerPlant> getByName(@RequestBody PowerPlant powerPlant) {
        return  ResponseEntity.status(HttpStatus.OK).body(powerPlantService.getByName(powerPlant.getPowerPlantName()));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PowerPlant>> getAll() {
        return  ResponseEntity.status(HttpStatus.OK).body(powerPlantService.getAll());
    }

    @PostMapping("/save")
    public ResponseEntity<PowerPlant> save(@RequestBody PowerPlant powerPlant){
        return ResponseEntity.status(HttpStatus.OK).body(createPowerPlant.create(powerPlant));
    }

    @PostMapping("/batchSave")
    public ResponseEntity<List<PowerPlant>> batchSave(@RequestBody PowerPlantBatchSaveRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(powerPlantService.batchSave(request));
    }

    @GetMapping("/getByLocation")
    public ResponseEntity<FindByLocationResponse> getByLocation(@RequestBody GetByLocationRequest request) {
        return  ResponseEntity.status(HttpStatus.OK).body(powerPlantService.getByLocation(request));
    }

    @GetMapping("/getNSorted")
    public ResponseEntity<GetNHighOutputAndNLowOutPutPlantsResponse> getNSorted(@RequestBody GetNHighOutputAndNLowOutPutPlantsRequest request){
        log.info("request : {}", request);
        return ResponseEntity.status(HttpStatus.OK).body(powerPlantService.getNHighOutputAndNLowOutPutPlants(request));
    }

    @GetMapping("/getPowerOutPutByLocation")
    public ResponseEntity<List<GetPowerPlantOutputByRegionResponse>> getPowerOutPutByLocation(@RequestBody GetPowerPlantOutputByRegionRequest request) {
        return  ResponseEntity.status(HttpStatus.OK).body(powerPlantOutputByRegion.getPowerPlantOutputByRegion(request));
    }



}
