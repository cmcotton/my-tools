/*
 * 版權宣告: FDC all rights reserved.
 */
package sheet;

import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class PieChartSheet extends SheetHandler {
    
    @Override
    public void insertPieChart(Map<String, Long> map, Workbook workbook, int sheetNumber,
            String domainVarName, String rangeVarName) {
        Sheet sheetMaster = workbook.getSheetAt(sheetNumber);
        
        Cell datecell = null;
        CellStyle cellStyle = workbook.createCellStyle();
        
        int i = 1;
        Set<String> keySet = map.keySet();
        try {
            for (String key : keySet) {
                long value = map.get(key);

                Row r = sheetMaster.getRow(i);
                if (r == null)
                    r = sheetMaster.createRow(i);

                r.createCell(0).setCellValue(key); // High risk
                r.createCell(4).setCellValue(value); // High risk
 
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int rowNum = map.size();

        String sheetName = sheetMaster.getSheetName();
        int deface = 1;
        
        // Search for named range
        Name rangeCell = workbook.getName(domainVarName); //"AbnormalAttackAllEvt");
        // Set new range for named range
        String reference = sheetName + "!$A$" + (deface + 1) + ":$A$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);   
        
        rangeCell = workbook.getName(rangeVarName); //"AbnormalAttackPie");        
        reference = sheetName + "!$E$" + (deface + 1) + ":$E$" + (rowNum + deface);
        rangeCell.setRefersToFormula(reference);        
    }
    

}
