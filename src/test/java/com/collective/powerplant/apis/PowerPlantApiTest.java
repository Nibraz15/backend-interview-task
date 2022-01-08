package com.collective.powerplant.apis;

import com.collective.powerplant.logic.CreatePowerPlant;
import com.collective.powerplant.logic.GetPowerPlantOutputByRegion;
import com.collective.powerplant.model.Generator;
import com.collective.powerplant.model.PowerPlant;
import com.collective.powerplant.model.database.PowerPlantTable;
import com.collective.powerplant.model.request.GetByLocationRequest;
import com.collective.powerplant.model.request.GetNHighOutputAndNLowOutPutPlantsRequest;
import com.collective.powerplant.model.request.GetPowerPlantOutputByRegionRequest;
import com.collective.powerplant.model.request.PowerPlantBatchSaveRequest;
import com.collective.powerplant.model.response.FindByLocationResponse;
import com.collective.powerplant.model.response.GetNHighOutputAndNLowOutPutPlantsResponse;
import com.collective.powerplant.model.response.GetPowerPlantOutputByRegionResponse;
import com.collective.powerplant.model.util.GeneratorList;
import com.collective.powerplant.service.impl.PowerPlantServiceImpl;
import com.collective.powerplant.util.SerDe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PowerPlantApiTest {

    @Mock
    private PowerPlantServiceImpl powerPlantService;

    @Mock
    private GetPowerPlantOutputByRegion powerPlantOutputByRegion;

    @Mock
    private CreatePowerPlant createPowerPlant;

    @InjectMocks
    PowerPlantApi powerPlantApi;


    @Test
    public void getNameTest(){
        when(powerPlantService.getByName(anyString())).thenReturn(buildPowerPlant());
        ResponseEntity<PowerPlant> response = powerPlantApi.getByName(buildPowerPlant());
        assertThat(response.getBody().getPowerPlantName()).isEqualTo("TestPlant");

    }

    @Test
    public void getAllTest(){
        List<PowerPlant> powerPlants = new ArrayList<>();
        powerPlants.add(buildPowerPlant());
        powerPlants.add(buildPowerPlant());

        when(powerPlantService.getAll()).thenReturn(powerPlants);

        ResponseEntity<List<PowerPlant>> entity = powerPlantApi.getAll();
        assertThat(entity.getBody().size()).isEqualTo(powerPlants.size());
    }

    @Test
    public void saveTest() {
        when(createPowerPlant.create(any(PowerPlant.class))).thenReturn(buildPowerPlant());
        ResponseEntity<PowerPlant> response = powerPlantApi.save(buildPowerPlant());

        assertThat(response.getBody().getPowerPlantName()).isEqualTo(buildPowerPlant().getPowerPlantName());
    }

    @Test
    public void batchSaveTest() {
        List<PowerPlant> powerPlants = new ArrayList<>();
        powerPlants.add(buildPowerPlant());
        powerPlants.add(buildPowerPlant());
        PowerPlantBatchSaveRequest request = PowerPlantBatchSaveRequest.builder().powerPlants(powerPlants).build();

        when(powerPlantService.batchSave(any(PowerPlantBatchSaveRequest.class))).thenReturn(powerPlants);
        List<PowerPlant> plant = powerPlantService.batchSave(request);

        assertThat(plant.size()).isEqualTo(powerPlants.size());
    }

    @Test
    public void getByLocationTest() {
        List<PowerPlant> powerPlant = new ArrayList<>();
        powerPlant.add(buildPowerPlant());
        powerPlant.add(buildPowerPlant());



        FindByLocationResponse findByLocationResponse = FindByLocationResponse.builder()
                .plantsInLocation(2)
                .location("test")
                .plantResponseList(new PageImpl(powerPlant)).build();

        when(powerPlantService.getByLocation(any(GetByLocationRequest.class))).thenReturn(findByLocationResponse);
        ResponseEntity<FindByLocationResponse> response = powerPlantApi.getByLocation(GetByLocationRequest.builder().location("test").build());

        assertThat(response.getBody().getLocation()).isEqualTo("test");
    }

    @Test
    public void getNSortedTest(){

        GetNHighOutputAndNLowOutPutPlantsRequest request = GetNHighOutputAndNLowOutPutPlantsRequest.builder()
                .limit(2)
                .provideHighResults(true)
                .provideLowResults(true)
                .order("desc")
                .build();

        List<PowerPlant> powerPlant = new ArrayList<>();
        powerPlant.add(buildPowerPlant());
        powerPlant.add(buildPowerPlant());

        GetNHighOutputAndNLowOutPutPlantsResponse dummyResponse = GetNHighOutputAndNLowOutPutPlantsResponse.builder()
                        .highOutputPlants(powerPlant).lowOutputPlants(powerPlant).build();


        when(powerPlantService.getNHighOutputAndNLowOutPutPlants(any(GetNHighOutputAndNLowOutPutPlantsRequest.class))).thenReturn(dummyResponse);

        ResponseEntity<GetNHighOutputAndNLowOutPutPlantsResponse> response = powerPlantApi.getNSorted(request);

        assertThat(response.getBody().getHighOutputPlants().size()).isEqualTo(powerPlant.size());
        assertThat(response.getBody().getHighOutputPlants().size()).isEqualTo(powerPlant.size());

    }

    @Test
    public void getPowerOutPutByLocationTest() {
        List<String> regions = new ArrayList<>();
        regions.add("test");
        GetPowerPlantOutputByRegionRequest request = GetPowerPlantOutputByRegionRequest.builder()
                .regions(regions).getForAllRegions(false).build();

        List<GetPowerPlantOutputByRegionResponse> responses = new ArrayList<>();
        GetPowerPlantOutputByRegionResponse response = GetPowerPlantOutputByRegionResponse.builder().region("test").build();
        responses.add(response);
        when(powerPlantOutputByRegion.getPowerPlantOutputByRegion((any(GetPowerPlantOutputByRegionRequest.class)))).thenReturn(responses);

        ResponseEntity<List<GetPowerPlantOutputByRegionResponse>> regionResponses = powerPlantApi.getPowerOutPutByLocation(request);

        assertThat(regionResponses.getBody().get(0).getRegion()).isEqualTo(responses.get(0).getRegion());
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
