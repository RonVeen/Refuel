package org.ninjaware.refueling.service;

import org.ninjaware.refueling.model.Vehicle;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class VehicleService {

    @Inject
    private VehiclePersistence persistence;

    public Vehicle findVehicleByid(String uuid) {
        Vehicle vehicle = persistence.findById(uuid);
        return vehicle;
    }


    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = persistence.findAll();
        return vehicles;
    }


    public Vehicle save(Vehicle vehicle) {
        Vehicle v = persistence.save(vehicle);
        return v;
    }


    public Long delete(String uuid) {
        Long deleteCount = persistence.delete(uuid);
        return deleteCount;
    }
}
