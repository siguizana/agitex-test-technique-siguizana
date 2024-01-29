package org.agitex.test.api.Impl;

import org.agitex.test.api.ImporteFile;
import org.agitex.test.domain.Client;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ImportCsvFile implements ImporteFile {
    @Override
    public List<Client> importFile(InputStream inputStream) throws IOException {
        System.out.println("::::csv::::");
        List<Client> clients = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        for (CSVRecord csvRecord : csvParser) {
            String nom = csvRecord.get(0).trim();
            String prenom = csvRecord.get(1);
            Integer age = Integer.parseInt(csvRecord.get(2).trim());
            String profession = csvRecord.get(3).trim();
            Double salaire = Double.parseDouble(csvRecord.get(4).trim());
            clients.add(Client.builder()
                    .nom(nom)
                    .prenom(prenom)
                    .age(age)
                    .profession(profession)
                    .salaire(salaire)
                    .build());
        }
        return clients;
    }
}
