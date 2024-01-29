package org.agitex.test.service;

import org.agitex.test.api.AppClient;
import org.agitex.test.api.Impl.ImportCsvFile;
import org.agitex.test.api.Impl.ImportJsonFile;
import org.agitex.test.api.Impl.ImportTxtFile;
import org.agitex.test.api.Impl.ImportXlsFile;
import org.agitex.test.api.Impl.ImportXmlFile;
import org.agitex.test.api.ImporteFile;
import org.agitex.test.domain.Client;
import org.agitex.test.repository.ClientRepository;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agitex.test.dto.ISalaireMoyen;
import org.springframework.web.server.ResponseStatusException;

@Service
@SuppressWarnings("ALL")
public class AppService {
    private final Map<String, ImporteFile> importers = new HashMap<>();
    private final ClientRepository clientRepository;
    private final AppClient appClient;

    public AppService(ClientRepository clientRepository, AppClient appClient) {
        this.clientRepository = clientRepository;
        this.appClient = appClient;
        importers.put("json", new ImportJsonFile());
        importers.put("txt", new ImportTxtFile());
        importers.put("xml", new ImportXmlFile());
        importers.put("csv", new ImportCsvFile());
        importers.put("xls", new ImportXlsFile());
        importers.put("xlsx", new ImportXlsFile());
    }

    /**
     * Service d'importation des fichier.
     *
     * @param file
     * @return List<Client>
     * @throws IOException
     */
    public List<Client> importFile(MultipartFile file) throws IOException {
        List<Client> clientList = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        System.out.println("file name:::: " + fileName);
        if (fileName != null) {
            String extension = getExtension(fileName);
            System.out.println("extension:::: " + extension);
            ImporteFile fileImporter = importers.get(extension);
            if (fileImporter != null) {
                clientList = fileImporter.importFile(file.getInputStream());
                // Enregistrement des clients
                if (clientList != null && !clientList.isEmpty()) {
                    clientList = clientRepository.saveAll(clientList);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Type de fichier non pris en charge.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aucun nom de fichier trouvé.");
        }
        return clientList;
    }

    private String getExtension(String fileName) {
        return FileNameUtils.getExtension(fileName);
    }

    /**
     * Liste des client
     *
     * @return List<Client>
     */
    public List<Client> fetchClient() {
        return appClient.fetchClient();
    }

    /**
     * Calcul du salire moyen par profession.
     *
     * @return List<ISalaireMoyen>
     */
    public List<ISalaireMoyen> salaireMoyenByProssion() {
        return appClient.salaireMoyenByProssion();
    }
}
