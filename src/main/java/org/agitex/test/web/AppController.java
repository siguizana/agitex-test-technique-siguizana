package org.agitex.test.web;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.agitex.test.domain.Client;
import org.agitex.test.dto.ISalaireMoyen;
import org.agitex.test.service.AppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AppController {
    private final AppService appService;

    @GetMapping("/test")
    public String home() {
        return "Bienvenue au test";
    }

    /**
     * Importation de fichier.
     *
     * @param file
     * @return Void
     * @throws IOException
     */
    @Operation(summary = "Importation de fichier")
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Client>> importFile(@RequestParam("file") final MultipartFile file) throws IOException {
        List<Client> clients = appService.importFile(file);
        System.out.println("clientList:::: " + clients.size());
        return new ResponseEntity<>(clients, HttpStatus.CREATED);
    }

    /**
     * Liste des client
     *
     * @return List<Client>
     */
    @GetMapping("/client")
    @Operation(summary = "Liste des clients")
    public ResponseEntity<List<Client>> fetchClient() {
        return ResponseEntity.ok().body(appService.fetchClient());
    }

    /**
     * Calcul du salire moyen par profession.
     *
     * @return List<ISalaireMoyen>
     */
    @GetMapping("/salaire-moyen")
    @Operation(summary = "Salaire moyen par profession")
    public ResponseEntity<List<ISalaireMoyen>> salaireMoyenByProssion() {
        return ResponseEntity.ok().body(appService.salaireMoyenByProssion());
    }
}
