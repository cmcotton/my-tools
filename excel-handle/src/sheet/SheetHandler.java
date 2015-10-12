package sheet;

import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class SheetHandler {
    

    public void insertIntoSheet(Map<String, Long> map1, Map<String, Long> map2, Map<String, Long> map3,
            Map<String, Long> map4, Map<String, Long> map5, Map<String, Long> map6, Map<String, Long> map7,
            Map<String, Long> map8, Map<String, Long> map9, Map<String, Long> map10, Map<String, Long> map11,
            Map<String, Long> map12, Workbook workbook, int sheetNumber) {
        Sheet sheetMaster = workbook.getSheetAt(sheetNumber);
        
        Cell datecell = null;
        CellStyle cellStyle = workbook.createCellStyle();
        
        int i = 1;
        Set<String> keySet = map1.keySet();
        try {
            for (String key : keySet) {
                long value = map1.get(key);

                Row r = sheetMaster.getRow(i);
                if (r == null)
                    r = sheetMaster.createRow(i);

                datecell = r.createCell(0); // month
                // datecell.setCellValue("1/1/2012");
                datecell.setCellValue(key);
                datecell.setCellStyle(cellStyle);

                r.createCell(1).setCellValue(value); // High risk
                
                r.createCell(2).setCellValue(map2.get(key).longValue()); // medium risk
                
                r.createCell(3).setCellValue(map3.get(key)); // low risk
                
                r.createCell(4).setCellValue(value + map2.get(key) + map3.get(key)); // risk monthly
  
                //
                r.createCell(6).setCellValue(map4.get(key)); // medium risk
                r.createCell(7).setCellValue(map5.get(key)); // medium risk
                r.createCell(8).setCellValue(map6.get(key)); // medium risk
                r.createCell(9).setCellValue(map7.get(key)); // medium risk
                r.createCell(10).setCellValue(map4.get(key) + map5.get(key) + map6.get(key) + map7.get(key)); // medium risk
                
                // 
                r.createCell(12).setCellValue(map8.get(key)); // medium risk
                r.createCell(13).setCellValue(map9.get(key)); // medium risk
                r.createCell(14).setCellValue(map10.get(key)); // medium risk
                r.createCell(15).setCellValue(map11.get(key)); // medium risk
                r.createCell(16).setCellValue(map12.get(key)); // medium risk
                r.createCell(17).setCellValue(map8.get(key) + map9.get(key) + map10.get(key) + map11.get(key) + map12.get(key)); // 
                
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int rowNum = map1.size();

        String sheetName = sheetMaster.getSheetName();
        int deface = 1;
        
        // Search for named range
        Name rangeCell = workbook.getName("Month");
        // Set new range for named range
        String reference = sheetName + "!$A$" + (deface + 1) + ":$A$" + (rowNum + deface);
        // Assigns range value to named range
        rangeCell.setRefersToFormula(reference);

        rangeCell = workbook.getName("High");
        reference = sheetName + "!$B$" + (deface + 1) + ":$B$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
        
        rangeCell = workbook.getName("Medium");
        reference = sheetName + "!$C$" + (deface + 1) + ":$C$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
        
        rangeCell = workbook.getName("Low");
        reference = sheetName + "!$D$" + (deface + 1) + ":$D$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
        
        rangeCell = workbook.getName("RiskMonthly");
        reference = sheetName + "!$E$" + (deface + 1) + ":$E$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
        
        rangeCell = workbook.getName("ViolatePolicy");
        reference = sheetName + "!$G$" + (deface + 1) + ":$G$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
        
        rangeCell = workbook.getName("AbnormalAttack");
        reference = sheetName + "!$H$" + (deface + 1) + ":$H$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
        
        rangeCell = workbook.getName("Virus");
        reference = sheetName + "!$I$" + (deface + 1) + ":$I$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
        
        rangeCell = workbook.getName("DeviceAbnormal");
        reference = sheetName + "!$J$" + (deface + 1) + ":$J$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
        
        rangeCell = workbook.getName("EventMonthly");
        reference = sheetName + "!$K$" + (deface + 1) + ":$K$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
        
        rangeCell = workbook.getName("IPS");
        reference = sheetName + "!$M$" + (deface + 1) + ":$M$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
        
        rangeCell = workbook.getName("Firewall");
        reference = sheetName + "!$N$" + (deface + 1) + ":$N$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
        
        rangeCell = workbook.getName("Antivirus");
        reference = sheetName + "!$O$" + (deface + 1) + ":$O$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
        
        rangeCell = workbook.getName("Honeypot");
        reference = sheetName + "!$P$" + (deface + 1) + ":$P$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
        
        rangeCell = workbook.getName("Audit");
        reference = sheetName + "!$Q$" + (deface + 1) + ":$Q$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
        
        rangeCell = workbook.getName("DeviceMonthly");
        reference = sheetName + "!$R$" + (deface + 1) + ":$R$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);
    }
    
    public void insertPieChart(Map<String, Long> map, Workbook workbook, int sheetNumber,
            String domainVarName, String rangeVarName) {    
    }
    
    public void addNewColumn(Map<String, Long> newColumn, Workbook workbook, int sheetNumber, String title) {
        Sheet sheetMaster = workbook.getSheetAt(sheetNumber);
        
        Cell datacell = null;        
        
        // title
        Row r = sheetMaster.getRow(0);
        int colIndex = r.getLastCellNum();
        datacell = r.createCell(colIndex);
        datacell.setCellValue(title);
        
        try {
            for(int i = 1; i <= sheetMaster.getLastRowNum(); i++) {
                r = sheetMaster.getRow(i);
                if (r != null) {
                    datacell = r.getCell(0);
                    String rowTitle = "";
                    if (datacell != null && datacell.getCellType() == Cell.CELL_TYPE_STRING) {                        
                        rowTitle = datacell.getStringCellValue();
//                        System.out.println(rowTitle);
                        if (rowTitle.startsWith("最新")) { // 可以結束這個sheet了
                            break;
                        }
                    }
                    long value = 0;
                    if (newColumn.containsKey(rowTitle)) {
                        value = newColumn.get(rowTitle);
                    } 
//                    colIndex = r.getLastCellNum();

                    datacell = r.createCell(colIndex);
                    if (datacell != null) {
                        datacell.setCellValue(value);
                    } else {
                        System.out.println("datacell is null.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void addNewRow(Map<String, Long> newColumn, Workbook workbook, int sheetNumber, String title) {
        Sheet sheetMaster = workbook.getSheetAt(sheetNumber);
        
        Cell datecell = null;        
        
        int rowNum = sheetMaster.getLastRowNum();
        Row r = sheetMaster.getRow(rowNum);
        if (r == null) {
            r = sheetMaster.createRow(rowNum);
        }
        
        // title

    }

}
