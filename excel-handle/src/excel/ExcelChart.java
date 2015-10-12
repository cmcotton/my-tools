package excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelChart extends ExcelHandler{

    public void generateExcelChart(File sourceFile) throws IOException {

        int deface = 1;

        // Load sample excel file
        Workbook workbook = null;
        try {
            workbook = openWorkbook(new File("./ChartTemplate.xlsx"));
        } catch (InvalidFormatException | IOException e1) {
            e1.printStackTrace();
            return;
        }
        // sample.xls
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sh = workbook.getSheetAt(0);
        String sheetName = sh.getSheetName();

        // create cell style for date format
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.00%"));

        // Clear dummy values
//        sh.getRow(1).getCell(0).setCellValue("");
//        sh.getRow(1).getCell(1).setCellValue("");

        // Set headers for the data
        sh.createRow(0).createCell(1).setCellValue("First Half");
        sh.getRow(0).createCell(2).setCellValue("Second Half");
        sh.getRow(0).createCell(3).setCellValue("Redo");

        Cell datecell = null;

        ExcelDataReaderXLSX excel = new ExcelDataReaderXLSX();
        LinkedHashMap firstMap = excel.composeClickMap(sourceFile, 1);

        LinkedHashMap secondMap = excel.composeClickMap(sourceFile, 2);

        LinkedHashMap redoMap = excel.composeRedoMap(sourceFile);
        
        // 因為單位上下半年會換  取聯集
        LinkedHashSet<String> allOrg = new LinkedHashSet<>();
        
        Set<String> keySet = firstMap.keySet();
        allOrg.addAll(keySet);
        
        Set<String> secondKeySet = secondMap.keySet();
        allOrg.addAll(secondKeySet);
        

        int i = 1;
        
        
        try {
            for (String key : allOrg) {
                Double value = 0.0;
                
                if (firstMap.containsKey(key)) {
                    value = (Double) firstMap.get(key);
                }

                Row r = sh.getRow(i);
                if (r == null)
                    r = sh.createRow(i);

                datecell = r.createCell(0);
                // datecell.setCellValue("1/1/2012");
                datecell.setCellValue(key);                

                datecell = r.createCell(1);
                datecell.setCellStyle(cellStyle);
                datecell.setCellValue(value.doubleValue() / 100); // first

                Double secondValue = 0.0;
                if (secondMap.containsKey(key)) {
                    secondValue = (Double) secondMap.get(key);
                }
                datecell = r.createCell(2);
                datecell.setCellStyle(cellStyle);
                datecell.setCellValue(secondValue.doubleValue() / 100); // second
                

                Double redoValue = 0.0;
                if (redoMap.containsKey(key)) {
                    redoValue = (Double) redoMap.get(key);
                }
                datecell = r.createCell(3);
                datecell.setCellStyle(cellStyle);
                datecell.setCellValue(redoValue.doubleValue() / 100); // redo
                
//                drawBorder(workbook, datecell);
                i++;
            }
        } catch (Exception e) {

            e.printStackTrace();

        }

        int rowNum = allOrg.size();

        // Search for named range
        Name rangeCell = workbook.getName("OrgName");
        // Set new range for named range
        String reference = sheetName + "!$A$" + (deface + 1) + ":$A$" + (rowNum + deface);
        // Assigns range value to named range
        rangeCell.setRefersToFormula(reference);

        rangeCell = workbook.getName("FirstHalf");
        reference = sheetName + "!$B$" + (deface + 1) + ":$B$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);

        rangeCell = workbook.getName("SecondHalf");
        reference = sheetName + "!$C$" + (deface + 1) + ":$C$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);

        rangeCell = workbook.getName("Redo");
        reference = sheetName + "!$D$" + (deface + 1) + ":$D$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);

        final String destFilePath = "./output/chart_" + sourceFile.getName();
        FileOutputStream f = new FileOutputStream(destFilePath);
        workbook.write(f);
        f.close();

        System.out.println("Number Of Sheets:" + workbook.getNumberOfSheets());
        Sheet s = workbook.getSheetAt(0);
        System.out.println("Number Of Rows:" + s.getLastRowNum());
    }
}
