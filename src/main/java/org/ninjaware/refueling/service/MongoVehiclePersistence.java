package org.ninjaware.refueling.service;

import com.mongodb.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Key;
import dev.morphia.Morphia;
import dev.morphia.query.Query;
import org.ninjaware.refueling.model.Vehicle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@MongoPersistence
public class MongoVehiclePersistence implements VehiclePersistence {


    private Morphia morphia;
    private Datastore datastore;

    @PostConstruct
    public void setup() {
        morphia = new Morphia();
        morphia.mapPackage("org.ninjaware.refueling.model");
        MongoClient mongoClient = new MongoClient();
        datastore = morphia.createDatastore(mongoClient, "vehicles");

    }

    @Override
    public Vehicle findById(String uuid) {
        Query<Vehicle> query = datastore.createQuery(Vehicle.class)
                                        .field("uuid").equal(uuid);
        Vehicle vehicle = query.get();
        return vehicle;
    }

    @Override
    public List<Vehicle> findAll() {
        return datastore.createQuery(Vehicle.class).asList();
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        if (vehicle.getUuid() == null) {
            vehicle.setUuid(UUID.randomUUID().toString());
            vehicle.setCreatedAt(LocalDateTime.now());
            datastore.save(vehicle);
        } else {
            var ops = datastore.createUpdateOperations(Vehicle.class)
                    .set("registration", vehicle.getRegistration())
                    .set("description", vehicle.getDescription())
                    .set("updatedAt", LocalDateTime.now())
                    .set("active", vehicle.isActive());
            datastore.updateFirst(datastore.createQuery(Vehicle.class)
                    .field("uuid").equal(vehicle.getUuid()), ops, false);
        }
        return vehicle;
    }

    @Override
    public Long delete(String uuid) {
        datastore.delete(Vehicle.class, uuid);
        return 0l;
    }
}
