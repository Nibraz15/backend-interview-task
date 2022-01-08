package com.collective.powerplant.logic.impl;

import com.collective.powerplant.logic.CreatePowerPlant;
import com.collective.powerplant.model.PowerPlant;
import com.collective.powerplant.service.PowerPlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class CreatePowerPlantImpl implements CreatePowerPlant {

    private final PowerPlantService powerPlantService;

    @Override
    public PowerPlant create(PowerPlant powerPlant) {
        if(!powerPlantService.exists(powerPlant.getPowerPlantName())){
            return powerPlantService.save(powerPlant);
        }else {
            return update(powerPlant);
        }
    }

    @Override
    public PowerPlant update(PowerPlant powerPlant){
        PowerPlant powerPlantInDb = powerPlantService.getByName(powerPlant.getPowerPlantName());
        powerPlantInDb.setYear(powerPlant.getYear() != 0? powerPlant.getYear() : powerPlantInDb.getYear());

        if(!powerPlant.getGenerators().getGenerators().isEmpty()){
            powerPlant.getGenerators().getGenerators().addAll(powerPlant.getGenerators().getGenerators());
        }
        return powerPlantService.save(powerPlant);
    }
}
