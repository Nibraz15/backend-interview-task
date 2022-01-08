package com.collective.powerplant.model.util;

import com.collective.powerplant.model.Generator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Generated
public class GeneratorList {

    @JsonProperty("generators")
    private List<Generator> generators;
}
