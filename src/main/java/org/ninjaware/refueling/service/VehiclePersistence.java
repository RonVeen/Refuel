package org.ninjaware.refueling.service;

import org.ninjaware.refueling.model.Vehicle;
import java.util.List;

interface VehiclePersistence {
    Vehicle findById(String uuid);
    List<Vehicle> findAll();
    Vehicle save(Vehicle vehicle);
    Long delete(String uuid);
}
