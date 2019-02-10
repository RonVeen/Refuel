package org.ninjaware.refueling;

import org.ninjaware.refueling.model.Vehicle;
import org.ninjaware.refueling.service.VehicleService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@RequestScoped
@Path("vehicle")
@Produces(MediaType.APPLICATION_JSON)
public class VehicleResource {

    @Inject
    private VehicleService vehicleService;

    @GET
    public Response getVehicles() {
        return Response.ok(Json.createObjectBuilder().add("message", " Welcome to Refuel-Vehicle").build()).build();
    }

    @GET
    @Path("/{uuid}")
    public Response getVehicle(@PathParam("uuid") String uuid) {
        Vehicle vehicle = vehicleService.findVehicleByid(uuid);
        String json = JsonbBuilder.create().toJson(vehicle);
        return Response.ok(json).build();
    }
}
