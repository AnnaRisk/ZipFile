import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Configuration;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.parser.Parser;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.codeborne.selenide.Selenide.$;

public class ZipFiles {
    static {
        Configuration.browser = "Chrome";
    }

    ClassLoader cl = getClass().getClassLoader();
    String zipway = "src/test/resources/zip/simple-zip-file.zip";
    String namezip = "src/test/resources/zip/simple-zip-file.zip";
    String pdfName = "junit-user-guide-5.8.2.pdf";
    String xlsxName = "sample-xlsx-file.xlsx";
    String csvName = "teachers.csv";

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

}