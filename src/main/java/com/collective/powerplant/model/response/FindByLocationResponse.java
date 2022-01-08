package com.collective.powerplant.model.response;

import com.collective.powerplant.model.PowerPlant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Generated
public class FindByLocationResponse {

    private String location;
    public int plantsInLocation;
    private Page<PowerPlant> plantResponseList;

}
