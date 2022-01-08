package com.collective.powerplant.logic.impl;

import com.collective.powerplant.model.Generator;
import com.collective.powerplant.model.PowerPlant;
import com.collective.powerplant.model.util.GeneratorList;
import com.collective.powerplant.service.impl.PowerPlantServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreatePowerPlantImplTest {

    @Mock
    private PowerPlantServiceImpl powerPlantService;

    @InjectMocks
    CreatePowerPlantImpl createPowerPlant;

    @Test
    public void createPowerPlantTest(){
        PowerPlant powerPlant = buildPowerPlant();
        when(powerPlantService.exists(anyString())).thenReturn(false);
        when(powerPlantService.save(any(PowerPlant.class))).thenReturn(powerPlant);

        PowerPlant response = createPowerPlant.create(powerPlant);

        assertThat(response.getPowerPlantName()).isEqualTo(powerPlant.getPowerPlantName());

    }

    @Test
    public void updatePowerPlantTest(){
        PowerPlant powerPlant = buildPowerPlant();
        when(powerPlantService.exists(anyString())).thenReturn(true);
        when(powerPlantService.getByName(anyString())).thenReturn(powerPlant);
        when(powerPlantService.save(any(PowerPlant.class))).thenReturn(powerPlant);

        PowerPlant response = createPowerPlant.create(powerPlant);

        assertThat(response.getPowerPlantName()).isEqualTo(powerPlant.getPowerPlantName());

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
