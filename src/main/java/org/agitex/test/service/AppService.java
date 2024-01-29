package org.agitex.test.service;

import lombok.RequiredArgsConstructor;
import org.agitex.test.api.AppClient;
import org.agitex.test.domain.Client;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import org.agitex.test.dto.ISalaireMoyen;

@Service
@RequiredArgsConstructor
public class AppService {
    private final AppClient appClient;

    public void importFile(MultipartFile file) throws IOException {
        appClient.importFile(file);
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
