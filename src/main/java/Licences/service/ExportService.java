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
import java.util.List;


@Service
public class ExportService {

    public byte[] exportLicensesToExcel(List<License> licenses) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Licenses");

            // Заголовки таблицы
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Ключ", "Компания", "Лицензионный план", "Дата начала", "Дата окончания", "Отозвана", "Продлена"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(getHeaderCellStyle(workbook));
            }

            // Заполнение данных
            int rowIdx = 1;
            for (License license : licenses) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(license.getL01_ID());
                row.createCell(1).setCellValue(license.getL01_KEY());
                row.createCell(2).setCellValue(license.getCompany() != null ? license.getCompany().getC01_NAME() : "N/A");
                row.createCell(3).setCellValue(license.getLicensePlan() != null ? license.getLicensePlan().getLP01_NAME() : "N/A");
                row.createCell(4).setCellValue(license.getL01_START_DATE() != null ? license.getL01_START_DATE().toString() : "N/A");
                row.createCell(5).setCellValue(license.getL01_END_DATE() != null ? license.getL01_END_DATE().toString() : "N/A");
                row.createCell(6).setCellValue(license.isL01_REVOKED());
                row.createCell(7).setCellValue(license.isL01_EXTENDED());
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

                row.createCell(0).setCellValue(company.getC01_ID());
                row.createCell(1).setCellValue(company.getC01_NAME());
                row.createCell(2).setCellValue(company.getC01_ADRESS());
                row.createCell(3).setCellValue(company.getC01_CONTACT());
                row.createCell(4).setCellValue(company.getC01_DESC());
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

                row.createCell(0).setCellValue(licensePlan.getLP01_ID());
                row.createCell(1).setCellValue(licensePlan.getLP01_NAME());
                row.createCell(2).setCellValue(licensePlan.getLP01_MAX_USERS() != null ? licensePlan.getLP01_MAX_USERS() : 0);
                row.createCell(3).setCellValue(licensePlan.getLP01_PRICE() != null ? licensePlan.getLP01_PRICE().doubleValue() : 0.0);
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

                row.createCell(0).setCellValue(licenseLog.getLL01_ID());
                row.createCell(1).setCellValue(licenseLog.getLL01_CHANGE_TYPE());
                row.createCell(2).setCellValue(licenseLog.getLL01_CHANGE_DATE() != null ? licenseLog.getLL01_CHANGE_DATE().toString() : "");
                row.createCell(3).setCellValue(licenseLog.getLL01_OLD_VALUE());
                row.createCell(4).setCellValue(licenseLog.getLL01_NEW_VALUE());
                row.createCell(5).setCellValue(licenseLog.getUser() != null ? licenseLog.getUser().getU01_NAME() : "N/A");
                row.createCell(6).setCellValue(licenseLog.getLicense().getL01_ID());
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
}