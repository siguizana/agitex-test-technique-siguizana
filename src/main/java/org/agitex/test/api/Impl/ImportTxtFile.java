package org.agitex.test.api.Impl;

import org.agitex.test.api.ImporteFile;
import org.agitex.test.domain.Client;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ImportTxtFile implements ImporteFile {
    @Override
    public List<Client> importFile(InputStream inputStream) {
        System.out.println("::::txt::::");
        List<Client> clients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] donnees = ligne.split(",");
                String nom = donnees[0].trim();
                String prenom = donnees[1].trim();
                Integer age = Integer.parseInt(donnees[2].trim());
                String profession = donnees[3].trim();
                Double salaire = Double.parseDouble(donnees[4].trim());
                clients.add(Client.builder()
                        .nom(nom)
                        .prenom(prenom)
                        .age(age)
                        .profession(profession)
                        .salaire(salaire)
                        .build());
            }
            System.out.println("::::clients.size()::::" + clients.size());
            return clients;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
