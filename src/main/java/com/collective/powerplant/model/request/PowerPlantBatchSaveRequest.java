package com.collective.powerplant.model.request;

import com.collective.powerplant.model.PowerPlant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Generated
public class PowerPlantBatchSaveRequest {
    private List<PowerPlant> powerPlants;
}
