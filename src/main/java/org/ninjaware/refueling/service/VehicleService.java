package org.ninjaware.refueling.service;

import org.ninjaware.refueling.model.Vehicle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.UUID;

@ApplicationScoped
public class VehicleService {

    public Vehicle findVehicleByid(String uuid) {
        Vehicle vehicle = new Vehicle();
        vehicle.setUuid(uuid);
        vehicle.setRegistration(UUID.randomUUID().toString().substring(0, 4));
        vehicle.setDescription("Vehicle " + vehicle.getRegistration());
        vehicle.setActive(true);
        return vehicle;
    }
}
