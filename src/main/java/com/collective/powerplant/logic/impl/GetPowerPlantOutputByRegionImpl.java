package com.collective.powerplant.logic.impl;

import com.collective.powerplant.logic.GetPowerPlantOutputByRegion;
import com.collective.powerplant.model.PowerPlant;
import com.collective.powerplant.model.request.GetByLocationRequest;
import com.collective.powerplant.model.request.GetPowerPlantOutputByRegionRequest;
import com.collective.powerplant.model.response.FindByLocationResponse;
import com.collective.powerplant.model.response.GetPowerPlantOutputByRegionResponse;
import com.collective.powerplant.service.PowerPlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class GetPowerPlantOutputByRegionImpl implements GetPowerPlantOutputByRegion {

    private final PowerPlantService powerPlantService;


    @Override
    public List<GetPowerPlantOutputByRegionResponse> getPowerPlantOutputByRegion(GetPowerPlantOutputByRegionRequest request) {
        List<GetPowerPlantOutputByRegionResponse> getPowerPlantOutputByRegionResponses = new ArrayList<>();

        if(request.isGetForAllRegions()){
            return getPowerPlantOutputForAllRegion();
        }else {
            for(String region:request.getRegions()){
                getPowerPlantOutputByRegionResponses.add(getPowerPlantOutputByRegion(region));
            }
        }
        return getPowerPlantOutputByRegionResponses;
    }

    @Override
    public List<GetPowerPlantOutputByRegionResponse> getPowerPlantOutputForAllRegion(){
        List<PowerPlant> powerPlants = powerPlantService.getAll();
        Hashtable<String, List<PowerPlant>> powerPlantMap = mapPlantsToRegion(powerPlants);
        List<GetPowerPlantOutputByRegionResponse> regionResponses= new ArrayList<>();

        for(String region: powerPlantMap.keySet()){
            long regionOutput = getRegionOutPut(powerPlantMap.get(region));
            setPowerOutPut(powerPlantMap.get(region), regionOutput);

            regionResponses.add(GetPowerPlantOutputByRegionResponse.builder()
                    .region(region)
                    .regionOutput(regionOutput)
                    .powerPlants(powerPlantMap.get(region))
                    .build());
        }

        return regionResponses;
    }

    @Override
    public GetPowerPlantOutputByRegionResponse getPowerPlantOutputByRegion(String region) {

        FindByLocationResponse findByLocationResponse = powerPlantService.getByLocation(
                GetByLocationRequest.builder()
                        .location(region)
                        .build());

        List<PowerPlant> powerPlants = findByLocationResponse.getPlantResponseList().toList();
        long regionOutput = getRegionOutPut(powerPlants);
        setPowerOutPut(powerPlants, regionOutput);

        return  GetPowerPlantOutputByRegionResponse.builder()
                .region(region)
                .regionOutput(regionOutput)
                .powerPlants(powerPlants)
                .build();
    }

    private void setPowerOutPut(List<PowerPlant> powerPlants, long regionOutput){

        for (PowerPlant powerPlant: powerPlants){
            powerPlant.setPowerOutputPercentage(0);
            if (regionOutput != 0){
                powerPlant.setPowerOutputPercentage(((float) powerPlant.getPowerOutPut()/regionOutput)*100);
            }
            powerPlant.setGenerators(null);
        }
    }

    private long getRegionOutPut(List<PowerPlant> powerPlants){
        int regionOutput = 0;
        for(PowerPlant powerPlant: powerPlants){
            regionOutput += powerPlant.getPowerOutPut();
        }
        return  regionOutput;
    }

    private Hashtable<String, List<PowerPlant>> mapPlantsToRegion(List<PowerPlant> powerPlants){
        Hashtable<String, List<PowerPlant>> powerPlantMap = new Hashtable<>();

        for(PowerPlant powerPlant:powerPlants){
            if(powerPlantMap.containsKey(powerPlant.getLocation())){
                powerPlantMap.get(powerPlant.getLocation()).add(powerPlant);
            }else {
                List<PowerPlant> temp = new ArrayList<>();
                temp.add(powerPlant);
                powerPlantMap.put(powerPlant.getLocation(),temp);
            }
        }
        return powerPlantMap;
    }
}
