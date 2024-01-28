package org.agitex.test.service;

import lombok.RequiredArgsConstructor;
import org.agitex.test.api.AppClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AppService {
    private final AppClient appClient;

    public void importFile(MultipartFile file) throws IOException {
        appClient.importFile(file);
    }
}
