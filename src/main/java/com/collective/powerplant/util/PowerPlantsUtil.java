package com.collective.powerplant.util;

import com.collective.powerplant.model.Generator;
import com.collective.powerplant.model.request.GetByLocationRequest;
import com.collective.powerplant.model.response.FindByLocationResponse;
import com.collective.powerplant.model.PowerPlant;
import com.collective.powerplant.model.database.PowerPlantTable;
import com.collective.powerplant.model.util.GeneratorList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@Slf4j
public class PowerPlantsUtil {

    public static PowerPlant deserialize(PowerPlantTable powerPlantTable){
        return PowerPlant.builder()
                .powerPlantName(powerPlantTable.getPowerPlantName())
                .location(powerPlantTable.getLocation())
                .year(powerPlantTable.getYear())
                .powerOutPut(powerPlantTable.getPowerOutPut())
                .generators(SerDe.deserialize(powerPlantTable.getGenerators(), GeneratorList.class))
                .build();
    }

    public static  PowerPlantTable serialize(PowerPlant powerPlant){
        return  PowerPlantTable.builder()
                .powerPlantName(powerPlant.getPowerPlantName())
                .location(powerPlant.getLocation())
                .year(powerPlant.getYear())
                .powerOutPut(getPowerOutPut(powerPlant.getGenerators()))
                .generators(SerDe.serialize(powerPlant.getGenerators()))
                .build();
    }

    public static FindByLocationResponse getByLocation(List<PowerPlantTable> powerPlantTableList, GetByLocationRequest request){
        return FindByLocationResponse.builder()
                .location(request.getLocation())
                .plantsInLocation((int) powerPlantTableList.size())
                .plantResponseList(getPowerPlantResponsePage(powerPlantTableList, request.getPage(), request.getSize()))
                .build();
    }

    public static List<PowerPlant> findAll(List<PowerPlantTable> powerPlantTableList){
        return getPowerPlantResponseList(powerPlantTableList);
    }

    public static List<PowerPlantTable> batchSave(List<PowerPlant> powerPlantRespons){
        return getPowerPlantTableList(powerPlantRespons);
    }

    public static List<PowerPlant> getPowerPlantResponseList(List<PowerPlantTable> powerPlantTableList){
        List<PowerPlant> powerPlantRespons = new ArrayList<>();

        for(PowerPlantTable plantTable: powerPlantTableList){
            powerPlantRespons.add(PowerPlantsUtil.deserialize(plantTable));
        }
        return powerPlantRespons;
    }

    public static Page<PowerPlant> getPowerPlantResponsePage(List<PowerPlantTable> powerPlantTablePage, Integer page, Integer size){

        List<PowerPlant> powerPlantRespons = new ArrayList<>();
        for(PowerPlantTable plantTable: powerPlantTablePage){
            powerPlantRespons.add(PowerPlantsUtil.deserialize(plantTable));
        }

        int total = powerPlantRespons.size();

        PageRequest pageRequest = PageRequest.of(page, size != 0 ? size: total);
        int start = toIntExact(pageRequest.getOffset());
        int end = Math.min((start + pageRequest.getPageSize()), total);
        List<PowerPlant> output = new ArrayList<>();
        if (start <= end) {
            output = powerPlantRespons.subList(start, end);
        }

        return new PageImpl<>(
                output,
                pageRequest,
                total
        );
    }

    public static List<PowerPlantTable> getPowerPlantTableList(List<PowerPlant> powerPlantRespons){
        List<PowerPlantTable> powerPlantTables = new ArrayList<>();

        for(PowerPlant plantResponse: powerPlantRespons){
            powerPlantTables.add(serialize(plantResponse));
        }
        return  powerPlantTables;
    }

    private static Integer getPowerOutPut(GeneratorList generatorList){
        int powerOutput = 0;
        for(Generator generator: generatorList.getGenerators()){
            powerOutput += generator.getGeneratorPowerGeneration();
        }
        return powerOutput;
    }
}
