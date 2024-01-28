package org.agitex.test.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.agitex.test.domain.Client;
import org.agitex.test.dto.ClientListDto;
import org.agitex.test.repository.ClientRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe d'implémentation.
 */
@RequiredArgsConstructor
@Component
@Slf4j
@SuppressWarnings("ALL")
public class AppClientImpl implements AppClient {
    private final ObjectMapper objectMapper;
    private final ClientRepository clientRepository;

    /**
     * Importation de fichier.
     *
     * @param file
     */
    @Override
    public void importFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aucun fichier associé.");
        }
        String fileName = file.getOriginalFilename();
        System.out.println("file name:::: " + fileName);
        List<Client> clientList = new ArrayList<>();
        if (fileName != null) {
            if (fileName.endsWith(".json")) {
                clientList = this.importJsonFile(file.getInputStream());
            } else if (fileName.endsWith(".txt")) {
                clientList = this.importTxtFile(file.getInputStream());
            } else if (fileName.endsWith(".xml")) {
                clientList = this.importXlmFile(file.getInputStream());
            } else if (fileName.endsWith(".csv")) {
                clientList = this.importCsvFile(file.getInputStream());
            } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
                clientList = this.importXlsFile(file.getInputStream());
            }
            // Enregistrement des clients
            if (clientList != null || !clientList.isEmpty()) {
                clientRepository.saveAll(clientList);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aucun nom de fichier trouvé.");
        }


    }


    /**
     * Importation d'un fichier json des client.
     *
     * @param inputStream
     * @throws IOException
     */
    private List<Client> importJsonFile(InputStream inputStream) throws IOException {
        System.out.println("::::json::::");
        List<Client> clients = objectMapper.readValue(inputStream, new TypeReference<List<Client>>() {
        });
        return clients;
    }

    private List<Client> importTxtFile(final InputStream inputStream) {
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

    private List<Client> importXlmFile(final InputStream inputStream) {
        System.out.println("::::xml::::");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ClientListDto.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ClientListDto clientListDto = (ClientListDto) jaxbUnmarshaller.unmarshal(inputStream);
            List<Client> clients = clientListDto.getClients().stream().flatMap(dto -> {
                return Stream.of(new Client(null, dto.getNom(), dto.getPrenom(), dto.getAge(),
                        dto.getSalaire(), dto.getProfession()));
            }).collect(Collectors.toList());
            System.out.println("::::clients.size()::::" + clients.size());
            return clients;
        } catch (JAXBException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private List<Client> importCsvFile(final InputStream inputStream) throws IOException {
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

    private List<Client> importXlsFile(final InputStream inputStream) throws IOException {
        System.out.println("::::xls::::");
        List<Client> clients = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(inputStream);
        // On suppose que le fichier Excel a une seule feuille
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() != 0) {
                String nom = row.getCell(0).getStringCellValue();
                String prenom = row.getCell(1).getStringCellValue();
                int age = (int) row.getCell(2).getNumericCellValue();
                String profession = row.getCell(3).getStringCellValue();
                double salaire = row.getCell(4).getNumericCellValue();
                Client client = new Client(null, nom, prenom, age, salaire, profession);
                clients.add(client);
            }
        }
        System.out.println("::::clients::::" + clients.size());
        return clients;
    }
}
