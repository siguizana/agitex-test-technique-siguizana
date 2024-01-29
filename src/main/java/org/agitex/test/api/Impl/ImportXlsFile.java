package org.agitex.test.api.Impl;

import org.agitex.test.api.ImporteFile;
import org.agitex.test.domain.Client;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ImportXlsFile implements ImporteFile {
    @Override
    public List<Client> importFile(InputStream inputStream) throws IOException {
        System.out.println("::::xls::::");
        List<Client> clients = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(inputStream);
        // On suppose que le fichier Excel a une seule feuille
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() != 0) {
                String nom = row.getCell(0).getStringCellValue();
                String prenom = row.getCell(1).getStringCellValue();
                int age = (int) row.getCell(2).getNumericCellValue();
                String profession = row.getCell(3).getStringCellValue();
                double salaire = row.getCell(4).getNumericCellValue();
                Client client = new Client(null, nom, prenom, age, salaire, profession);
                clients.add(client);
            }
        }
        System.out.println("::::clients::::" + clients.size());
        return clients;
    }
}
