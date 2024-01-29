package org.agitex.test.api.Impl;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.agitex.test.api.ImporteFile;
import org.agitex.test.domain.Client;
import org.agitex.test.dto.ClientListDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImportXmlFile implements ImporteFile {
    @Override
    public List<Client> importFile(InputStream inputStream) {
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
}
