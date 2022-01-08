package com.collective.powerplant.dao;

import com.collective.powerplant.model.database.PowerPlantTable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.LockModeType;
import java.util.List;

public interface PowerPlantDao extends JpaRepository<PowerPlantTable, Long> {

    List<PowerPlantTable> findByLocation(String location);
    PowerPlantTable findByPowerPlantName(String powerPlantNane);
    boolean existsByPowerPlantName(String powerPlantNane);

}
