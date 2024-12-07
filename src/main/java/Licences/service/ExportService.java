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

                row.createCell(0).setCellValue(license.getID());
                row.createCell(1).setCellValue(license.getKEY());
                row.createCell(2).setCellValue(license.getCompany() != null ? license.getCompany().getNAME() : "N/A");
                row.createCell(3).setCellValue(license.getLicensePlan() != null ? license.getLicensePlan().getNAME() : "N/A");
                row.createCell(4).setCellValue(license.getSTART_DATE() != null ? license.getSTART_DATE().toString() : "N/A");
                row.createCell(5).setCellValue(license.getEND_DATE() != null ? license.getEND_DATE().toString() : "N/A");
                row.createCell(6).setCellValue(license.isREVOKED());
                row.createCell(7).setCellValue(license.isEXTENDED());
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
}