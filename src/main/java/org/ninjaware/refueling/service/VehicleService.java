package org.ninjaware.refueling.service;

import org.ninjaware.refueling.model.Vehicle;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class VehicleService {

    @Inject
    private @MongoPersistence  VehiclePersistence persistence;

    public Vehicle findVehicleByid(String uuid) {
        var vehicle = persistence.findById(uuid);
        return vehicle;
    }


    public List<Vehicle> findAll() {
        var vehicles = persistence.findAll();
        return vehicles;
    }


    public Vehicle save(Vehicle vehicle) {
        var v = persistence.save(vehicle);
        return v;
    }


    public Long delete(String uuid) {
        var deleteCount = persistence.delete(uuid);
        return deleteCount;
    }
}
