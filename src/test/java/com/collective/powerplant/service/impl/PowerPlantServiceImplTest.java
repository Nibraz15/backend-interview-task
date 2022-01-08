package com.collective.powerplant.service.impl;

import com.collective.powerplant.dao.PowerPlantDao;
import com.collective.powerplant.model.Generator;
import com.collective.powerplant.model.PowerPlant;
import com.collective.powerplant.model.database.PowerPlantTable;
import com.collective.powerplant.model.request.GetByLocationRequest;
import com.collective.powerplant.model.request.GetNHighOutputAndNLowOutPutPlantsRequest;
import com.collective.powerplant.model.request.PowerPlantBatchSaveRequest;
import com.collective.powerplant.model.response.FindByLocationResponse;
import com.collective.powerplant.model.response.GetNHighOutputAndNLowOutPutPlantsResponse;
import com.collective.powerplant.model.util.GeneratorList;
import com.collective.powerplant.util.PowerPlantsUtil;
import com.collective.powerplant.util.SerDe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PowerPlantServiceImplTest {

    @Mock
    private PowerPlantDao powerPlantDao;

    @InjectMocks
    PowerPlantServiceImpl powerPlantService;

    @Test
    public void getNameTest(){
        PowerPlantTable powerPlantTable = buildPowerPlantTable();
        when(powerPlantDao.findByPowerPlantName(anyString())).thenReturn(powerPlantTable);
        PowerPlant powerPlant = powerPlantService.getByName(powerPlantTable.getPowerPlantName());

        assertThat(powerPlant.getPowerPlantName()).isEqualTo(powerPlantTable.getPowerPlantName());
    }

    @Test
    public void saveTest(){
        PowerPlant powerPlant = buildPowerPlant();
        PowerPlantTable powerPlantTable = buildPowerPlantTable();
        when(powerPlantDao.save(any(PowerPlantTable.class))).thenReturn(powerPlantTable);
        PowerPlant plant = powerPlantService.save(powerPlant);
        assertThat(plant.getPowerPlantName()).isEqualTo(powerPlant.getPowerPlantName());

    }

    @Test
    public void batchSaveTest(){
        List<PowerPlant> powerPlants = new ArrayList<>();
        powerPlants.add(buildPowerPlant());
        powerPlants.add(buildPowerPlant());

        PowerPlantBatchSaveRequest request = PowerPlantBatchSaveRequest.builder().powerPlants(powerPlants).build();

        when(powerPlantDao.saveAll(any())).thenReturn(PowerPlantsUtil.batchSave(request.getPowerPlants()));

        List<PowerPlant> plants = powerPlantService.batchSave(request);

        assertThat(plants.size()).isEqualTo(powerPlants.size());
    }

    @Test
    public void getByLocationTest(){
        List<PowerPlantTable> powerPlantTables = new ArrayList<>();
        powerPlantTables.add(buildPowerPlantTable());
        powerPlantTables.add(buildPowerPlantTable());

        when(powerPlantDao.findByLocation(anyString())).thenReturn(powerPlantTables);
        FindByLocationResponse response = powerPlantService.getByLocation(GetByLocationRequest.builder().location("test").build());

        assertThat(response.getLocation()).isEqualTo("test");
    }

    @Test
    public void getAllTest(){
        List<PowerPlantTable> powerPlantTables = new ArrayList<>();
        powerPlantTables.add(buildPowerPlantTable());
        powerPlantTables.add(buildPowerPlantTable());

        when(powerPlantDao.findAll()).thenReturn(powerPlantTables);

        List<PowerPlant> powerPlants = powerPlantService.getAll();

        assertThat(powerPlantTables.size()).isEqualTo(powerPlants.size());
    }

    @Test
    public void getNHighOutputAndNLowOutPutPlantsTestAsc(){

        GetNHighOutputAndNLowOutPutPlantsRequest request = GetNHighOutputAndNLowOutPutPlantsRequest.builder()
                .limit(2)
                .provideHighResults(true)
                .provideLowResults(true)
                .order("asc")
                .build();

        List<PowerPlantTable> powerPlantTables = new ArrayList<>();
        powerPlantTables.add(buildPowerPlantTable());
        powerPlantTables.add(buildPowerPlantTable());

        when(powerPlantDao.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(powerPlantTables));

        GetNHighOutputAndNLowOutPutPlantsResponse response = powerPlantService.getNHighOutputAndNLowOutPutPlants(request);

        assertThat(response.getHighOutputPlants().size()).isEqualTo(powerPlantTables.size());
        assertThat(response.getLowOutputPlants().size()).isEqualTo(powerPlantTables.size());

    }

    @Test
    public void getNHighOutputAndNLowOutPutPlantsTestDesc(){

        GetNHighOutputAndNLowOutPutPlantsRequest request = GetNHighOutputAndNLowOutPutPlantsRequest.builder()
                .limit(2)
                .provideHighResults(true)
                .provideLowResults(true)
                .order("desc")
                .build();

        List<PowerPlantTable> powerPlantTables = new ArrayList<>();
        powerPlantTables.add(buildPowerPlantTable());
        powerPlantTables.add(buildPowerPlantTable());

        when(powerPlantDao.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(powerPlantTables));

        GetNHighOutputAndNLowOutPutPlantsResponse response = powerPlantService.getNHighOutputAndNLowOutPutPlants(request);

        assertThat(response.getHighOutputPlants().size()).isEqualTo(powerPlantTables.size());
        assertThat(response.getLowOutputPlants().size()).isEqualTo(powerPlantTables.size());

    }



    private PowerPlantTable buildPowerPlantTable(){
        GeneratorList generatorList = new GeneratorList();
        Generator generator = new Generator("TestGen","TestStatus",100);
        List<Generator> generators = new ArrayList<>();
        generators.add(generator);
        generatorList.setGenerators(generators);

        return PowerPlantTable
                .builder()
                .powerPlantName("TestPlant")
                .year(2018)
                .location("test")
                .powerOutPut(100)
                .generators(SerDe.serialize(generatorList))
                .build();
    }

    private PowerPlant buildPowerPlant(){
        GeneratorList generatorList = new GeneratorList();
        Generator generator = new Generator("TestGen","TestStatus",100);
        List<Generator> generators = new ArrayList<>();
        generators.add(generator);
        generatorList.setGenerators(generators);

        return PowerPlant.builder()
                .powerPlantName("TestPlant")
                .year(2008)
                .location("test")
                .powerOutPut(100)
                .generators(generatorList)
                .build();
    }
}
