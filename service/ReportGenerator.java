
package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

    public static String generate(Path logsDirectory, Path reportFile)
            throws IOException {

        long totalDeposits = 0;
        long totalWithdrawals = 0;

        List<String> report = new ArrayList<>();

        report.add("===== MINIBANK END-OF-DAY REPORT =====");
        report.add("");

        if (Files.exists(logsDirectory)) {

            try (DirectoryStream<Path> files =
                    Files.newDirectoryStream(logsDirectory, "*.log")) {

                for (Path file : files) {

                    if (!Files.isRegularFile(file)
                            || Files.size(file) == 0) {
                        continue;
                    }

                    BasicFileAttributes attributes =
                        Files.readAttributes(
                            file, BasicFileAttributes.class
                        );

                    report.add("File: " + file.getFileName());
                    report.add("Size: " + attributes.size() + " bytes");
                    report.add("Last modified: "
                        + attributes.lastModifiedTime());

                    try (BufferedReader reader =
                            Files.newBufferedReader(file)) {

                        String line;

                        while ((line = reader.readLine()) != null) {

                            String[] parts = line.split("\\|");

                            if (parts.length < 2) {
                                continue;
                            }

                            try {
                                long amount =
                                    Long.parseLong(parts[1].trim());

                                if (amount <= 0) {
                                    continue;
                                }

                                if (parts[0].equals("DEPOSIT")) {
                                    totalDeposits += amount;
                                } else if (parts[0].equals("WITHDRAW")) {
                                    totalWithdrawals += amount;
                                }

                            } catch (NumberFormatException e) {
                                // Ignore invalid log amounts.
                            }
                        }
                    }

                    report.add("");
                }
            }

        } else {
            report.add("Logs directory does not exist yet.");
            report.add("");
        }

        report.add("Total deposits: Rs. " + totalDeposits);
        report.add("Total withdrawals: Rs. " + totalWithdrawals);
        report.add("Net amount: Rs. "
            + (totalDeposits - totalWithdrawals));

        String result = String.join(
            System.lineSeparator(), report
        );

        Path parent = reportFile.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        Files.writeString(reportFile, result);

        return result;
    }
}
