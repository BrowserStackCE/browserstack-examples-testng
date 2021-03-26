package com.browserstack.test.utils;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvUtil {
    public static Iterator<String[]> readAll(String path, boolean header) throws Exception {
        CSVReader csvReader = new CSVReader(new FileReader(path));
        Iterator<String[]> it = csvReader.readAll().iterator();
        csvReader.close();
        if(header) {
            it.next();
            it.remove();
        }
        return it;
    }

    public static List<String> readSpecificColumn(String path, int column) throws Exception {
        CSVReader csvReader = new CSVReader(new FileReader(path));
        String[] nextLine;
        List<String> it = new ArrayList<>();
        while ((nextLine = csvReader.readNext()) != null) {
            it.add(nextLine[column]);
        }
        csvReader.close();
        return it;
    }
}
