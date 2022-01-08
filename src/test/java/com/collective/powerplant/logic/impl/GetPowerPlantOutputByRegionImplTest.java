package com.collective.powerplant.logic.impl;

import com.collective.powerplant.model.Generator;
import com.collective.powerplant.model.PowerPlant;
import com.collective.powerplant.model.request.GetByLocationRequest;
import com.collective.powerplant.model.request.GetPowerPlantOutputByRegionRequest;
import com.collective.powerplant.model.response.FindByLocationResponse;
import com.collective.powerplant.model.response.GetPowerPlantOutputByRegionResponse;
import com.collective.powerplant.model.util.GeneratorList;
import com.collective.powerplant.service.impl.PowerPlantServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetPowerPlantOutputByRegionImplTest {

    @Mock
    private PowerPlantServiceImpl powerPlantService;

    @InjectMocks
    GetPowerPlantOutputByRegionImpl getPowerPlantOutputByRegion;

    @Test
    public void getPowerPlantOutputByRegionForNRegion(){
        List<String> regions = new ArrayList<>();
        regions.add("test1");
        GetPowerPlantOutputByRegionRequest request = GetPowerPlantOutputByRegionRequest.builder()
                .getForAllRegions(false)
                .regions(regions)
                .build();

        List<PowerPlant> powerPlants = new ArrayList<>();
        powerPlants.add(buildPowerPlant("TestPlant1", "test1"));
        powerPlants.add(buildPowerPlant("TestPlant2", "test1"));
        powerPlants.add(buildPowerPlant("TestPlant3", "test2"));
        powerPlants.add(buildPowerPlant("TestPlant4", "test3"));

        FindByLocationResponse response = FindByLocationResponse.builder()
                .location("test1")
                .plantResponseList(new PageImpl<>(powerPlants.subList(0,2)))
                .plantsInLocation(2).build();

        when(powerPlantService.getByLocation(any(GetByLocationRequest.class))).thenReturn(response);

        List<GetPowerPlantOutputByRegionResponse> getPowerPlantOutputByRegionResponses = getPowerPlantOutputByRegion.getPowerPlantOutputByRegion(request);

        assertThat(getPowerPlantOutputByRegionResponses.size()).isEqualTo(1);
        assertThat(getPowerPlantOutputByRegionResponses.get(0).getRegionOutput()).isEqualTo(200L);

    }

    @Test
    public void getPowerPlantOutputByRegionForAll(){
        List<String> regions = new ArrayList<>();
        regions.add("test1");
        GetPowerPlantOutputByRegionRequest request = GetPowerPlantOutputByRegionRequest.builder()
                .getForAllRegions(true)
                .regions(regions)
                .build();

        List<PowerPlant> powerPlants = new ArrayList<>();
        powerPlants.add(buildPowerPlant("TestPlant1", "test1"));
        powerPlants.add(buildPowerPlant("TestPlant2", "test1"));
        powerPlants.add(buildPowerPlant("TestPlant3", "test2"));
        powerPlants.add(buildPowerPlant("TestPlant4", "test3"));


        when(powerPlantService.getAll()).thenReturn(powerPlants);

        List<GetPowerPlantOutputByRegionResponse> getPowerPlantOutputByRegionResponses = getPowerPlantOutputByRegion.getPowerPlantOutputByRegion(request);

        assertThat(getPowerPlantOutputByRegionResponses.size()).isEqualTo(3);

    }



    private PowerPlant buildPowerPlant(String name, String region){
        GeneratorList generatorList = new GeneratorList();
        Generator generator = new Generator("TestGen","TestStatus",100);
        List<Generator> generators = new ArrayList<>();
        generators.add(generator);
        generatorList.setGenerators(generators);

        return PowerPlant.builder()
                .powerPlantName(name)
                .year(2008)
                .location(region)
                .powerOutPut(100)
                .generators(generatorList)
                .build();
    }
}
