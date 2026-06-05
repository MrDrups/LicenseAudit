package Licences.service;

import Licences.model.Company;
import Licences.model.License;
import Licences.model.LicenseLog;
import Licences.model.LicensePlan;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Service
public class ExportService {

    public byte[] exportLicensesToExcel(List<License> licenses) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Licenses");

            // Заголовки таблицы
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Ключ", "Компания", "Лицензионный план", "Дата начала", "Дата окончания", "Отозвана", "Продлена", "Комментарий", "Период уведомлений"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(getHeaderCellStyle(workbook));
            }

            // Заполнение данных
            int rowIdx = 1;
            for (License license : licenses) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(license.getID());
                row.createCell(1).setCellValue(license.getKEY());
                row.createCell(2).setCellValue(license.getCompany() != null ? license.getCompany().getNAME() : "N/A");
                row.createCell(3).setCellValue(license.getLicensePlan() != null ? license.getLicensePlan().getNAME() : "N/A");
                row.createCell(4).setCellValue(license.getSTART_DATE() != null ? license.getSTART_DATE().toString() : "N/A");
                row.createCell(5).setCellValue(license.getEND_DATE() != null ? license.getEND_DATE().toString() : "N/A");
                row.createCell(6).setCellValue(license.isREVOKED());
                row.createCell(7).setCellValue(license.isEXTENDED());
                row.createCell(8).setCellValue(license.getCOMMENT() != null ? license.getCOMMENT() : "");
                row.createCell(9).setCellValue(license.getNOTIFICATION_PERIOD() != null ? license.getNOTIFICATION_PERIOD() : "");
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] exportCompaniesToExcel(List<Company> companies) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Companies");

            // Заголовки таблицы
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Название", "Адрес", "Контакт", "Описание"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(getHeaderCellStyle(workbook));
            }

            // Заполнение данных
            int rowIdx = 1;
            for (Company company : companies) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(company.getID());
                row.createCell(1).setCellValue(company.getNAME());
                row.createCell(2).setCellValue(company.getADDRESS());
                row.createCell(3).setCellValue(company.getCONTACT());
                row.createCell(4).setCellValue(company.getDESC());
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }


    public byte[] exportLicensePlansToExcel(List<LicensePlan> licensePlans) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("License Plans");

            // Заголовки таблицы
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Название", "Макс число пользователей", "Цена в тыс. руб"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(getHeaderCellStyle(workbook));
            }

            // Заполнение данных
            int rowIdx = 1;
            for (LicensePlan licensePlan : licensePlans) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(licensePlan.getID());
                row.createCell(1).setCellValue(licensePlan.getNAME());
                row.createCell(2).setCellValue(licensePlan.getMAX_USERS() != null ? licensePlan.getMAX_USERS() : 0);
                row.createCell(3).setCellValue(licensePlan.getPRICE() != null ? licensePlan.getPRICE().doubleValue() : 0.0);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] exportLicenseLogsToExcel(List<LicenseLog> licenseLogs) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("License Logs");

            // Заголовки таблицы
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Тип Изменения", "Дата Изменения", "Старое Значение", "Новое значение", "Пользователь", "ID лицензии"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(getHeaderCellStyle(workbook));
            }

            // Заполнение данных
            int rowIdx = 1;
            for (LicenseLog licenseLog : licenseLogs) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(licenseLog.getID());
                row.createCell(1).setCellValue(licenseLog.getCHANGE_TYPE());
                row.createCell(2).setCellValue(licenseLog.getCHANGE_DATE() != null ? licenseLog.getCHANGE_DATE().toString() : "");
                row.createCell(3).setCellValue(licenseLog.getOLD_VALUE());
                row.createCell(4).setCellValue(licenseLog.getNEW_VALUE());
                row.createCell(5).setCellValue(licenseLog.getUser() != null ? licenseLog.getUser().getNAME() : "N/A");
                row.createCell(6).setCellValue(licenseLog.getLicense().getID());
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }


    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    // ========== CSV Export Methods ==========

    private static final String CSV_BOM = "\uFEFF";
    private static final String CSV_DELIMITER = ",";
    private static final String CSV_LINE_SEP = "\r\n";

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(CSV_DELIMITER) || value.contains("\"") || value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private String csvLine(String... values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) sb.append(CSV_DELIMITER);
            sb.append(escapeCsv(values[i]));
        }
        sb.append(CSV_LINE_SEP);
        return sb.toString();
    }

    public byte[] exportLicensesToCsv(List<License> licenses) {
        StringBuilder sb = new StringBuilder(CSV_BOM);
        sb.append(csvLine("ID", "Ключ", "Компания", "Лицензионный план", "Дата начала", "Дата окончания", "Отозвана", "Продлена", "Комментарий", "Период уведомлений"));
        for (License l : licenses) {
            sb.append(csvLine(
                    String.valueOf(l.getID()),
                    l.getKEY(),
                    l.getCompany() != null ? l.getCompany().getNAME() : "N/A",
                    l.getLicensePlan() != null ? l.getLicensePlan().getNAME() : "N/A",
                    l.getSTART_DATE() != null ? l.getSTART_DATE().toString() : "N/A",
                    l.getEND_DATE() != null ? l.getEND_DATE().toString() : "N/A",
                    String.valueOf(l.isREVOKED()),
                    String.valueOf(l.isEXTENDED()),
                    l.getCOMMENT() != null ? l.getCOMMENT() : "",
                    l.getNOTIFICATION_PERIOD() != null ? l.getNOTIFICATION_PERIOD() : ""
            ));
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public byte[] exportCompaniesToCsv(List<Company> companies) {
        StringBuilder sb = new StringBuilder(CSV_BOM);
        sb.append(csvLine("ID", "Название", "Адрес", "Контакт", "Описание"));
        for (Company c : companies) {
            sb.append(csvLine(
                    String.valueOf(c.getID()),
                    c.getNAME(),
                    c.getADDRESS(),
                    c.getCONTACT(),
                    c.getDESC()
            ));
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public byte[] exportLicensePlansToCsv(List<LicensePlan> licensePlans) {
        StringBuilder sb = new StringBuilder(CSV_BOM);
        sb.append(csvLine("ID", "Название", "Макс число пользователей", "Цена в тыс. руб"));
        for (LicensePlan lp : licensePlans) {
            sb.append(csvLine(
                    String.valueOf(lp.getID()),
                    lp.getNAME(),
                    lp.getMAX_USERS() != null ? String.valueOf(lp.getMAX_USERS()) : "0",
                    lp.getPRICE() != null ? String.valueOf(lp.getPRICE()) : "0"
            ));
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public byte[] exportLicenseLogsToCsv(List<LicenseLog> licenseLogs) {
        StringBuilder sb = new StringBuilder(CSV_BOM);
        sb.append(csvLine("ID", "Тип Изменения", "Дата Изменения", "Старое Значение", "Новое значение", "Пользователь", "ID лицензии"));
        for (LicenseLog ll : licenseLogs) {
            sb.append(csvLine(
                    String.valueOf(ll.getID()),
                    ll.getCHANGE_TYPE(),
                    ll.getCHANGE_DATE() != null ? ll.getCHANGE_DATE().toString() : "",
                    ll.getOLD_VALUE(),
                    ll.getNEW_VALUE(),
                    ll.getUser() != null ? ll.getUser().getNAME() : "N/A",
                    ll.getLicense() != null ? String.valueOf(ll.getLicense().getID()) : "N/A"
            ));
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}