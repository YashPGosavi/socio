package com.socio.socio.util;

import java.util.List;

public class CsvUtil {
    // Converts a list of objects to CSV format
    public static String usersToCsv(List<?> data) {
        StringBuilder sb = new StringBuilder();

        data.forEach(row -> sb.append(row.toString()).append("\n"));

        return sb.toString();
    }
}
