package org.agitex.test.api;

import org.agitex.test.domain.Client;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ImporteFile {
    List<Client> importFile(InputStream inputStream) throws IOException;
}
