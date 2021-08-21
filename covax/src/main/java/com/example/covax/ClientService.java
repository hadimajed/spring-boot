package com.example.covax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public void addNewClient(Client client) {
        Optional<Client> clientOptional = clientRepository.findClientByName(client.getName());
        if (clientOptional.isPresent()){
            throw new IllegalStateException("The client already exist");
        }if (client.getShots() >0){
            throw new IllegalStateException("you cannot set a shot");
        }if(client.getDate().compareTo(LocalDate.now()) < 0 || client.getDate().compareTo(LocalDate.now()) > 0){
            throw new IllegalStateException("Date should not be before or after " + LocalDate.now());
        }
        clientRepository.save(client);
    }

    public void addNewShot(Client client) {
        Optional<Client> clientOptional= clientRepository.findClientByName(client.getName());
        if (clientOptional.isPresent() && client.getShots()==0){
            client.setShots(1);
            clientRepository.save(client);
        }
    }

    public String getCountOfFirstShots() {
        String m = "Total numbers of vaccinated people with 1 shot: ";
        return m + clientRepository.countFirstShot(1) + " people.";
    }

    public String getCountOfSecondShots() {
        String m = "Total numbers of vaccinated people with 2 shot: ";
        return m + clientRepository.countSecondShot(2) + " people.";
    }

    public void nextShot(Client client) {
        LocalDate datePlus20 = client.getDate().plusDays(20);
        Optional<Client> clientOptional= clientRepository.findClientByName(client.getName());
        if (clientOptional.isPresent() && client.getShots()==1){
            client.setNextShot(datePlus20);
            clientRepository.save(client);
        }
    }

    @Transactional
    public void updateNextShot(String name, LocalDate date) {
        Client c = clientRepository.findClientByName(name).orElseThrow(()->new IllegalStateException("Person with name: " + name +" does not exist"));
        if(date.compareTo(c.getNextShot())>0){
            c.setNextShot(date);
            clientRepository.save(c);
        }else{
            throw new IllegalStateException("You are not permitted to take the second shot before "+ c.getNextShot());
        }
    }

    public String isVaccinated(String name) {
        Client c = clientRepository.findClientByName(name).orElseThrow(()->new IllegalStateException("Person with name: " + name +" does not exist"));
        String m="";
        if (c.getShots() == 1){
            m = name + " has taken the first shot and the next shot is scheduled on: " + c.getNextShot();
        }if (c.getShots() == 2){
            m = name + " has taken the second shot and now fully vaccinated";
        }
        return m;
    }

    public String totalNumber() {
        return "Total number of fully vaccinated clients  " + clientRepository.countSecondShot(2) + " and the remaining ones " + clientRepository.countFirstShot(1) ;
    }
}
