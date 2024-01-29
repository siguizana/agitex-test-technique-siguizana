package org.agitex.test.api;

import org.agitex.test.domain.Client;
import org.agitex.test.dto.ISalaireMoyen;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AppClient {
    /**
     * Importation de fichier.
     *
     * @param file
     */
    void importFile(MultipartFile file) throws IOException;

    /**
     * Liste des client
     *
     * @return List<Client>
     */
    List<Client> fetchClient();

    /**
     * Calcul du salire moyen par profession.
     *
     * @return List<ISalaireMoyen>
     */
    List<ISalaireMoyen> salaireMoyenByProssion();
}
