package quru.qu;

import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
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
                    if (entry.getName().equals("xlsxTest.xlsx")) {
                        XLS xls = new XLS(zs);
                        assertTrue(xls.excel.getSheetAt(0).getRow(1).getCell(3).getStringCellValue().startsWith("123vCWvCaNKB2Gx9NyBfPpieb9Dl7i@6pa3JWpzfs.com"));
                    }
                }

            }
        }

    }
}