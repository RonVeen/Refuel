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
        var vehicleList = vehicleService.findAll();
        var list = new GenericEntity<List<Vehicle>>(vehicleList) {
        };
        return Response.accepted(list).build();
    }

    @GET
    @Path("/{uuid}")
    public Response getVehicle(@PathParam("uuid") String uuid) {
        var vehicle = vehicleService.findVehicleByid(uuid);
        var json = JsonbBuilder.create().toJson(vehicle);
        return Response.ok(json).build();
    }



    @POST
    public Response addVehicle(String jsonBody) {
        var resultJson = upsertVehicle(jsonBody);
        return Response.status(201).entity(resultJson).build();
    }


    @PUT
    public Response updateVehicle(String jsonBody) {
        var resultJson = upsertVehicle(jsonBody);
        return Response.ok(resultJson).build();
    }


    @DELETE
    @Path("/{uuid}")
    public Response deleteVehicle(@PathParam("uuid") String uuid) {
        var deleteCount = vehicleService.delete(uuid);
        return (deleteCount > 0 ? Response.ok() : Response.status(Response.Status.NOT_FOUND)).build();

    }


    private String upsertVehicle(String jsonBody) {
        var vehicle = JsonbBuilder.create().fromJson(jsonBody, Vehicle.class);
        var updatedVehicle = vehicleService.save(vehicle);
        return JsonbBuilder.create().toJson(updatedVehicle);

    }


}
