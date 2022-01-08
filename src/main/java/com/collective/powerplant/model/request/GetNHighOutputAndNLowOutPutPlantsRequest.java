package com.collective.powerplant.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Generated
public class GetNHighOutputAndNLowOutPutPlantsRequest {

    private int limit;
    private boolean provideHighResults;
    private boolean provideLowResults;
    private String order;
}
