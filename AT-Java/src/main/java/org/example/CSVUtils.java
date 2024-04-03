package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
    public static List<String> readFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public static void writeToFile(String filePath, String line) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(line);
            bw.newLine();
        }
    }

    public static int generateUniqueId(String filePath) throws IOException {
        List<String> lines = readFile(filePath);
        if (lines.isEmpty()) {
            return 1;
        } else {
            String lastLine = lines.get(lines.size() - 1);
            String[] parts = lastLine.split(",");
            return Integer.parseInt(parts[0]) + 1;
        }
    }

    public static void deleteRecord(String filePath, int lineNumber) throws IOException {
        List<String> lines = readFile(filePath);
        if (lineNumber >= 0 && lineNumber < lines.size()) {
            lines.remove(lineNumber);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
            }
        } else {
            throw new IllegalArgumentException("Número de linha inválido.");
        }
    }
}
