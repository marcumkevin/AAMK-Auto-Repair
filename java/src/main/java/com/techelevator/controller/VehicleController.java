package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.dao.VehicleDao;
import com.techelevator.model.User;
import com.techelevator.model.Vehicle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping("vehicle")
public class VehicleController {

    private VehicleDao vehicleDao;

    private UserDao userDao;

    public VehicleController(VehicleDao vehicleDao, UserDao userDao) {
        this.vehicleDao = vehicleDao;
        this.userDao = userDao;
    }


    public User getLoggedInUser(String username)
    {
        return this.userDao.findByUsername(username);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Vehicle> listAll() {
        return vehicleDao.getAllVehicles();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Vehicle> list(Principal principal) {

        User loggedInUser = getLoggedInUser(principal.getName());

        return vehicleDao.getVehicleByCustomerId(loggedInUser.getId());}

    @RequestMapping(method = RequestMethod.POST)
    public Vehicle create(@Valid @RequestBody Vehicle vehicle, Principal principal) {
        User loggedInUser = getLoggedInUser(principal.getName());

        int id = vehicleDao.addVehicle(loggedInUser.getId(), vehicle);
        vehicle.setVehicleId(id);
        return vehicle;
    }
    

    @RequestMapping(path = "/models/{year}/{make}", method = RequestMethod.GET)
    public String getVehicleModels(@PathVariable String year, @PathVariable String make) throws IOException, InterruptedException {
        String uri = "https://vpic.nhtsa.dot.gov/api/vehicles/getmodelsformakeyear/make/" + make + "/modelyear/" + year + "/vehicleType/car?format=json";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(UriComponentsBuilder.fromUriString(uri).build().toUri())
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
