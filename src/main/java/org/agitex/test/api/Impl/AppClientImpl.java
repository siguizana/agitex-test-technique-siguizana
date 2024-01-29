package org.agitex.test.api.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.agitex.test.api.AppClient;
import org.agitex.test.domain.Client;
import org.agitex.test.dto.ISalaireMoyen;
import org.agitex.test.repository.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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
     * Liste des client
     *
     * @return List<Client>
     */
    @Override
    public List<Client> fetchClient() {
        return clientRepository.findAll();
    }

    /**
     * Calcul du salire moyen par profession.
     *
     * @return List<ISalaireMoyen>
     */
    @Override
    public List<ISalaireMoyen> salaireMoyenByProssion() {
        return clientRepository.salaireMoyen();
    }

}
