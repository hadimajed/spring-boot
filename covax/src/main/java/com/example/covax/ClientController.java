package com.example.covax;


import jdk.dynalink.beans.StaticClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = {"/AllClients"})
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //get details about all clients
    @GetMapping(path = "/AllClients")
    public List<Client> getClients(){
        return clientService.getClients();
    }

    //get total number of client vaccinated with 1 shots
    @GetMapping(path = "/CountOfFirstShots")
    public String getCountOfFirstShots(){
        return clientService.getCountOfFirstShots();
    }

    //get total number of client vaccinated with 2 shots
    @GetMapping(path = "/CountOfSecondShots")
    public String getCountOfSecondShots(){
        return clientService.getCountOfSecondShots();
    }

    //add a new client, and then increment shot by 1 automatically stating that the client took his first shot
    //then next shot date (+20) will be updated
    @PostMapping(path = "/NewClient")
    public void addNewClient(@RequestBody Client client){
        clientService.addNewClient(client);
        clientService.addNewShot(client);
        clientService.nextShot(client);
    }

    //update the next shot date, the client can only update the date if it's set after the next shot date
    @PutMapping(path = "/NextShot/{name}")
    public void updateNextShot(@PathVariable("name") String name, @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)  {
        clientService.updateNextShot(name, date);
    }

    //get details about a client, if he is vaccinated 1 time, or fully vaccinated.
    @GetMapping(path = "/{name}")
    public String isVaccinated(@PathVariable("name") String name)  {
        return clientService.isVaccinated(name);
    }

    //get the total number of vaccinated people and the remaining ones
    @GetMapping(path = "/totalNumber")
    public String totalNumber(){
        return clientService.totalNumber();
    }
}


