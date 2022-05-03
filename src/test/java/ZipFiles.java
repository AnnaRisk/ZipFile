import com.codeborne.pdftest.PDF;
import com.codeborne.pdftest.matchers.ContainsExactText;
import com.codeborne.selenide.Configuration;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipFiles {
    static {
        Configuration.browser = "Chrome";
    }

    ClassLoader cl = ZipFiles.class.getClassLoader();
    String zipway = "src/test/resources/zip/simple-zip-file.zip";
    String namezip = "zip/simple-zip-file.zip";


    @Test
    void zipParsingTest() throws Exception {

        try (
                ZipInputStream zin = new ZipInputStream(new FileInputStream(zipway))) {

            ZipEntry entry;
            String name;
            long size;

            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName(); // получим название файла
                size = entry.getSize();  // получим его размер в байтах
                System.out.printf("File name: %s \t File size: %d \n", name, size);

            }
        } catch (
                Exception ex) {

            System.out.println(ex.getMessage());
        }
    }

    @Test
    void zipCheckFile() throws Exception {
        try (InputStream is = cl.getResourceAsStream(namezip);
             ZipInputStream zip = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().equals("junit-user-guide-5.8.2.pdf")) {

                    PDF pdf = new PDF(zip);
                    MatcherAssert.assertThat(pdf, new ContainsExactText("JUnit 5 User Guide"));
                    System.out.printf("Проверка PDF успешно");
                    System.out.println();
                } else if (entry.getName().equals("teachers.csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zip));
                    List<String[]> content = reader.readAll();
                    org.assertj.core.api.Assertions.assertThat(content).contains(
                            new String[]{"Name", "Surname"},
                            new String[]{"Anna", "Rain"},
                            new String[]{"Masha", "Makarova"}
                    );
                    System.out.printf("Проверка CSV успешно");
                    System.out.println();

                } else if (entry.getName().equals("sample-xlsx-file.xls")) {

                    XLS xls = new XLS(zip);
                    assertThat(xls.excel
                            .getSheetAt(0)
                            .getRow(3)
                            .getCell(1)
                            .getStringCellValue()).contains("Carla");
                }
                System.out.printf("Проверка XLS успешно");
                System.out.println();
            }
        }
    }
}



