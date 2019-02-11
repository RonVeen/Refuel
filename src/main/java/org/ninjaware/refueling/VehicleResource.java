package org.ninjaware.refueling;

import org.ninjaware.refueling.model.Vehicle;
import org.ninjaware.refueling.service.VehicleService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@RequestScoped
@Path("vehicle")
@Produces(MediaType.APPLICATION_JSON)
public class VehicleResource {

    @Inject
    private VehicleService vehicleService;

    @GET
    public Response getVehicles() {
        List<Vehicle> vehicleList = vehicleService.findAll();
        GenericEntity<List<Vehicle>> list = new GenericEntity<List<Vehicle>>(vehicleList) {
        };
        return Response.ok(list).build();
    }

    @GET
    @Path("/{uuid}")
    public Response getVehicle(@PathParam("uuid") String uuid) {
        Vehicle vehicle = vehicleService.findVehicleByid(uuid);
        String json = JsonbBuilder.create().toJson(vehicle);
        return Response.ok(json).build();
    }



    @POST
    public Response addVehicle(String jsonBody) {
        String resultJson = upsertVehicle(jsonBody);
        return Response.status(201).entity(resultJson).build();
    }


    @PUT
    public Response updateVehicle(String jsonBody) {
        String resultJson = upsertVehicle(jsonBody);
        return Response.ok(resultJson).build();
    }


    @DELETE
    @Path("/{uuid}")
    public Response deleteVehicle(@PathParam("uuid") String uuid) {
        Long deleteCount = vehicleService.delete(uuid);
        return (deleteCount > 0 ? Response.ok() : Response.status(Response.Status.NOT_FOUND)).build();

    }


    private String upsertVehicle(String jsonBody) {
        Vehicle vehicle = JsonbBuilder.create().fromJson(jsonBody, Vehicle.class);
        Vehicle updatedVehicle = vehicleService.save(vehicle);
        return JsonbBuilder.create().toJson(updatedVehicle);

    }


}
