package quru.qu;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileParsingTest {
    private ClassLoader cl = FileParsingTest.class.getClassLoader();
    @Test
    void xlsxZipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("forTest.zip")) {
            try (ZipInputStream zs = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zs.getNextEntry()) != null) {
                    if (entry.getName().equals("forTest/xlsxTest.xlsx")) {
                        XLS xls = new XLS(zs);
                        assertTrue(xls.excel.getSheetAt(0).getRow(1).getCell(3).getStringCellValue().startsWith("vCWvCaNKB2Gx9NyBfPpieb9Dl7i@6pa3JWpzfs.com"));
                    }
                }

            }
        }
    }
    @Test
    void pdfZipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("forTest.zip")) {
            try (ZipInputStream zs = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zs.getNextEntry()) != null) {
                    if (entry.getName().equals("forTest/pdfTest.pdf")) {
                        PDF pdf = new PDF(zs);
                        assertTrue(pdf.text.startsWith("Информация об учебном занятии:"));
                    }
                }
            }
        }
    }
    @Test
    void csvZipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("forTest.zip")) {
            try (ZipInputStream zs = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zs.getNextEntry()) != null) {
                    if (entry.getName().equals("forTest/csvTest.csv")) {
                        CSVReader csvReader = new CSVReader(new InputStreamReader(zs));
                        List<String[]> content = csvReader.readAll();
                        Assertions.assertArrayEquals(new String[]{"CN001", "OU002", "iivanova@service.ru", "131"}, content.get(0));
                    }
                }

            }
        }
    }
    @Test
    void jsonTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = cl.getResourceAsStream("jsonTest.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            JsonTest jsonTest = objectMapper.readValue(isr, JsonTest.class);
            Assertions.assertEquals(167953, jsonTest.id);
            Assertions.assertEquals("4b864f71-3cfd-46d0-9d64-559cc8032860", jsonTest.person_id);

        }
    }
}


