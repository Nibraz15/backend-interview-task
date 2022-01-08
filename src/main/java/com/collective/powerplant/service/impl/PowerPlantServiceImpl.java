package com.collective.powerplant.service.impl;

import com.collective.powerplant.dao.PowerPlantDao;
import com.collective.powerplant.model.PowerPlant;
import com.collective.powerplant.model.database.PowerPlantTable;
import com.collective.powerplant.model.request.GetByLocationRequest;
import com.collective.powerplant.model.request.GetNHighOutputAndNLowOutPutPlantsRequest;
import com.collective.powerplant.model.request.PowerPlantBatchSaveRequest;
import com.collective.powerplant.model.response.FindByLocationResponse;
import com.collective.powerplant.model.response.GetNHighOutputAndNLowOutPutPlantsResponse;
import com.collective.powerplant.service.PowerPlantService;
import com.collective.powerplant.util.PowerPlantsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class PowerPlantServiceImpl implements PowerPlantService {

    private final PowerPlantDao powerPlantDao;

    @Override
    public PowerPlant getByName(String name){
        return PowerPlantsUtil.deserialize(powerPlantDao.findByPowerPlantName(name));
    }

    @Override
    public PowerPlant save(PowerPlant powerPlant) {
        PowerPlantTable powerPlantTable1 = PowerPlantsUtil.serialize(powerPlant);
        return PowerPlantsUtil.deserialize(powerPlantDao.save(powerPlantTable1));
    }

    @Override
    public List<PowerPlant> batchSave(PowerPlantBatchSaveRequest powerPlantBatchSaveRequest) {
        List<PowerPlantTable> powerPlantTables = powerPlantDao
                .saveAll(PowerPlantsUtil.batchSave(powerPlantBatchSaveRequest.getPowerPlants()));
        return PowerPlantsUtil.getPowerPlantResponseList(powerPlantTables);
    }

    @Override
    @CacheEvict(value="plantsByRegion")
    public FindByLocationResponse getByLocation(GetByLocationRequest request) {
        log.info("Finding PowerPlants By Location : {}",request.getLocation());
        List<PowerPlantTable> powerPlantTableList = powerPlantDao.findByLocation(request.getLocation());
        return PowerPlantsUtil.getByLocation(powerPlantTableList,request);
    }

    @Override
    @CacheEvict(value="plantsByRegion")
    public List<PowerPlant> getAll() {
        return PowerPlantsUtil.findAll(powerPlantDao.findAll());
    }

    @Override
    public boolean exists(String name) {
        return powerPlantDao.existsByPowerPlantName(name);
    }


    @Override
    public GetNHighOutputAndNLowOutPutPlantsResponse getNHighOutputAndNLowOutPutPlants(GetNHighOutputAndNLowOutPutPlantsRequest request) {

        Pageable pageWithNElementsDESC = PageRequest.of(0, request.getLimit(), Sort.by(Sort.Direction.DESC,"powerOutPut"));
        Pageable pageWithNElementsASC = PageRequest.of(0, request.getLimit(), Sort.by(Sort.Direction.ASC,"powerOutPut"));

        List<PowerPlantTable> highOutputPlants = new ArrayList<>();
        List<PowerPlantTable> lowOutputPlants = new ArrayList<>();

        if(request.isProvideHighResults()){
            highOutputPlants.addAll(powerPlantDao.findAll(pageWithNElementsDESC).toList());

            if(Objects.equals(request.getOrder(), "asc")){
                log.info("Order : {}", request.getOrder());
                Collections.reverse(highOutputPlants);
            }
        }
        if (request.isProvideLowResults()){
            lowOutputPlants.addAll(powerPlantDao.findAll(pageWithNElementsASC).toList());
            if(Objects.equals(request.getOrder(), "desc")){
                Collections.reverse(lowOutputPlants);
            }
        }

        return GetNHighOutputAndNLowOutPutPlantsResponse.builder()
                .highOutputPlants(PowerPlantsUtil.getPowerPlantResponseList(highOutputPlants))
                .lowOutputPlants(PowerPlantsUtil.getPowerPlantResponseList(lowOutputPlants))
                .build();
    }
}
