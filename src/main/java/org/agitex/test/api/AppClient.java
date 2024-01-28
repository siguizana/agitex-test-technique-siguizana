package org.agitex.test.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AppClient {
    /**
     * Importation de fichier.
     *
     * @param file
     */
    void importFile(MultipartFile file) throws IOException;
}
