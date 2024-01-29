package org.agitex.test.api.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.agitex.test.api.ImporteFile;
import org.agitex.test.domain.Client;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ImportJsonFile implements ImporteFile {
    @Override
    public List<Client> importFile(InputStream inputStream) throws IOException {
        System.out.println("::::json::::");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(inputStream, new TypeReference<List<Client>>() {
        });
    }
}
