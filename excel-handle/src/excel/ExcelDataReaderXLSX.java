package excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import sheet.AheadDNParser;
import sheet.AheadIPParser;
import sheet.ICSTDNParser;
import sheet.ICSTIPParser;
import sheet.Parser;
import util.BlusSource;

public class ExcelDataReaderXLSX {
    

    public LinkedHashMap<String, Long> composeRedoMap(File oriFile) {
        
        LinkedHashMap<String, Long> contentMap = new LinkedHashMap<>();

        Workbook workbook = null;
        try {
            workbook = openWorkbook(oriFile);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
            return contentMap;
        }
        
        Sheet sheet = workbook.getSheetAt(0);
        
        int ri = 0;
        for (Row r : sheet) {
            
            Cell c = r.getCell(0);
            if (c != null) {
                String content = c.getStringCellValue();
                if (content.contains("重覆點選表")) {
                    contentMap = insertMap(sheet, r);
                }
            }
            
            ri++;
        }
        
        
        return contentMap;
    }
    
    public LinkedHashMap insertMap(Sheet sheet, Row r) {
        LinkedHashMap<String, Double> contentMap = new LinkedHashMap<String, Double>();
        
        int rowNum = r.getRowNum();
        
//        
//        
//        Cell dataCell = r.getCell(0);
//        String org = dataCell.getStringCellValue();
//        
////        String org = sheet.getCell(0, rowOffset + 2).getContents();
////        String temp = sheet.getCell(3, rowOffset + 2).getContents();
//        
//        dataCell = sheet.getRow(rowNum + 2).getCell(3);
//        String percentStr = dataCell.getStringCellValue();
        
        String org = "ini";
        String percentStr = "";
        
                
        for (int j = 0; !"".equals(org); j++) {
            
            Row nextRow = sheet.getRow(rowNum + j + 2);
            if (nextRow != null) {
                org = nextRow.getCell(0).getStringCellValue();
                percentStr = sheet.getRow(rowNum + j + 2).getCell(3).getStringCellValue();
                
                percentStr = percentStr.replace("%", "");
                double percent = Double.parseDouble(percentStr);
                contentMap.put(org, Double.valueOf(percent));
                
            } else {
                break;
            }
        }

        return contentMap;
    }

    public LinkedHashMap<String, Long> composeClickMap(File oriFile, int time) {
        LinkedHashMap<String, Long> contentMap = null;

        Workbook workbook = null;
        try {
            workbook = openWorkbook(oriFile);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
        
        
        Sheet sheet = workbook.getSheetAt(0);
        int ri = 0;
        for (Row r : sheet) {
            
            Cell c = r.getCell(0);
            if (c != null) {
                String content = c.getStringCellValue();
                if ((time == 1 && content.contains("第一次)演練結果統計表")) || 
                        (time == 2 && content.contains("第二次)演練結果統計表"))) {
                    contentMap = insertMap(sheet, r);
                }
            }

            ri++;
        }

        return contentMap;
    }

    
    public LinkedHashMap<String, Long> extractSingleRowByTitle(File oriFile, String title) {
        LinkedHashMap<String, Long> contentMap = new LinkedHashMap<String, Long>();

        // Use a file, save memory
        Workbook workbook = null;
        try {
            workbook = openWorkbook(oriFile);
        } catch (InvalidFormatException | IOException e1) {
            e1.printStackTrace();
            return contentMap;
        }

        Sheet sheet = workbook.getSheetAt(0);

        boolean isFinished = false;
        int ri = 0;

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            int ci = 0;

            Row row = rowIterator.next();

            // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();

                String content = cell.getStringCellValue();

                // 標題符合就取出整個row的資料
                if ((title.equals(content))) {
                    while (!isFinished) {
                        cell = cellIterator.next();
                        long number = 0;
                        try {
                            content = cell.getStringCellValue();
                            number = Long.parseLong(content);
                            Cell dateCell = sheet.getRow(0).getCell(ci);
                            String date = dateCell.getStringCellValue();

                            contentMap.put(date, number);
                        } catch (Exception e) {
                            isFinished = true;
                            break;
                        }

                    } // end while

                }

                if (isFinished) {
                    break;
                }

                ci++;

            } //
            if (isFinished) {
                break;
            }
            System.out.println("");

            ri++;
        }

//        try {
//            fio.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File("d:\\test.xls"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentMap;
    }

    /**
     * @param oriFile
     * @param contentMap
     * @param workbook
     * @return
     * @throws IOException 
     * @throws InvalidFormatException 
     */
    private Workbook openWorkbook(File oriFile) throws InvalidFormatException, IOException {
        Workbook workbook = null;
        
        workbook = WorkbookFactory.create(oriFile);
        
        return workbook;
    }

    public Sheet getExcelSheet(File oriFile, int index) {

        Workbook workbook = null;
        // Use a file, save memory
        try {
            workbook = openWorkbook(oriFile);
        } catch (InvalidFormatException | IOException e1) {
            e1.printStackTrace();
            return null;
        }

        return workbook.getSheetAt(index);
    }

    public void saveExcelSheetAsCSV(File oriFile, int index) throws InvalidFormatException, IOException {

        // Use a file, save memory
        Workbook workbook = null;
        
        workbook = openWorkbook(oriFile);
        

        Sheet sheet = workbook.getSheetAt(index);
        String sheetName = workbook.getSheetName(index);

        BlusSource source = determineSourceType(oriFile);
        Parser parser = getParser(sheetName, source);

        parser.saveContent(sheet);

    }

    private BlusSource determineSourceType(File file) {
        if (file.getName().contains("Complete DN-IP")) {
            return BlusSource.ICST;
        } else if (file.getName().contains("CHTSOC")) {
            return BlusSource.FORENSIS;
        } else {
            return BlusSource.FORENSIS;
        }
    }
    
    private Parser getParser(String sheetName, BlusSource source) {
        
        Parser parser = null;
        
        if (source.equals(BlusSource.FORENSIS)) {
            if (sheetName.contains("Domain")) {
                parser = new AheadDNParser();
                
            } else if (sheetName.contains("IP")) {
                parser = new AheadIPParser();
                
            }
        } else if (source.equals(BlusSource.ICST)) {
        
            if (sheetName.contains("DN")) {
                parser = new ICSTDNParser();
            } else if (sheetName.contains("IP")) {
                parser = new ICSTIPParser();
            }
        }        
        
        return parser;
    }
    
    public LinkedList<String> extractSingleColumnByCellTitle(File oriFile, String cellTitle) {

        LinkedList<String> contentList = new LinkedList<>(); 

        // Use a file, save memory
        Workbook workbook = null;
        try {
            workbook = openWorkbook(oriFile);
        } catch (InvalidFormatException | IOException e1) {
            e1.printStackTrace();
            return contentList;
        }
        Sheet sheet  = workbook.getSheetAt(0);
//        Sheet sheet = workbook.getSheet(sheetNameKey);
        // Sheet sheet = workbook.getSheet(0);


        int startRow = 0;        
        int column = 0;
        boolean found = false;
        for (Row r : sheet) {
            
            for(int j = 0; j < r.getLastCellNum(); j++) {
                Cell c = r.getCell(j);
                if (c != null) {
                    if (c.getCellType() == Cell.CELL_TYPE_STRING) {
                        if (c.getStringCellValue().trim().equals(cellTitle)) {
                            column = j;
                            startRow = r.getRowNum();
                            found = true;
                            break;
                        }
                    }
                }
            }
            
            
        }
        
        try {
            if (found) {
                for(Row r : sheet) {
                    if (r.getRowNum() <= startRow) {
                        continue;
                    }
                    
                    Cell c = r.getCell(column);
                    if (c != null) {
                        if (c.getCellType() == Cell.CELL_TYPE_STRING) {
                            contentList.add(c.getStringCellValue().trim().replaceAll(" ", ""));
                        } else if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            
                            contentList.add(Double.toString(c.getNumericCellValue()).trim().replaceAll(" ", ""));
                        }
                        
                    } else {
                        contentList.add("");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        return contentList;
    }
    
    public Map<String, String> extractTwoColumn(File oriFile, String sheetNameKey, int keyColumnIndex, int valueColumnIndex) {

        Map<String, String> contentMap = new HashMap<String, String>();

        // Use a file, save memory
        Workbook workbook = null;
        try {
            workbook = openWorkbook(oriFile);
        } catch (InvalidFormatException | IOException e1) {
            e1.printStackTrace();
            return contentMap;
        }

        Sheet sheet = workbook.getSheet(sheetNameKey);
        // Sheet sheet = workbook.getSheet(0);

        for (Row r : sheet) {
            Cell key = r.getCell(keyColumnIndex);
            Cell value = r.getCell(valueColumnIndex);
            if (key != null && value != null) {
                if (value.getCellType() == Cell.CELL_TYPE_STRING) {
                    String content = value.getStringCellValue();
                    contentMap.put(key.getStringCellValue(), content);
                } else {
                    contentMap.put(key.getStringCellValue(), String.valueOf(value.getNumericCellValue()));
                }
                
            }
        }
            
        return contentMap;
    }
    
    public Map<String, String> extractTwoColumn(File oriFile, String sheetNameKey, int keyColumnIndex, 
            int valueColumnIndex, Map<Integer, String> whereCon) {

        Map<String, String> contentMap = new HashMap<String, String>();

        // Use a file, save memory
        Workbook workbook = null;
        try {
            workbook = openWorkbook(oriFile);
        } catch (InvalidFormatException | IOException e1) {
            e1.printStackTrace();
            return contentMap;
        }

        Sheet sheet = workbook.getSheet(sheetNameKey);
        // Sheet sheet = workbook.getSheet(0);
        Set<Integer> conditionColumn = whereCon.keySet();
        
        for (Row r : sheet) {
            
            for (Integer i : conditionColumn) {
                Cell whereValueCell = r.getCell(i.intValue());
                if (whereValueCell == null) {
                    continue;
                }
                
                String whereValue = whereValueCell.getStringCellValue();
                if (whereValue.equals(whereCon.get(i.intValue()))) {
                    Cell key = r.getCell(keyColumnIndex);
                    Cell value = r.getCell(valueColumnIndex);
                    if (key != null && value != null) {
                        contentMap.put(key.getStringCellValue(), value.getStringCellValue());
                    }
                }
            }
        }
            
        return contentMap;
    }
    
    public List<String> extractSingleColumn(File oriFile, String sheetNameKey, int columnIndex, Map<Integer, String> whereCon) {

        List<String> contentList = new ArrayList<String>();

        // Use a file, save memory
        Workbook workbook = null;
        try {
            workbook = openWorkbook(oriFile);
        } catch (InvalidFormatException | IOException e1) {
            e1.printStackTrace();
            return contentList;
        }

        Sheet sheet = workbook.getSheet(sheetNameKey);
        // Sheet sheet = workbook.getSheet(0);
        Set<Integer> conditionColumn = whereCon.keySet();
        
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            int ri = 0;
            Row r = rowIterator.next();
            
            boolean isMatched = true;
            for (Integer i : conditionColumn) {
                
                Cell whereValueCell = r.getCell(i.intValue());
                if (whereValueCell == null) {
                    continue;
                }
                String whereValue = whereValueCell.getStringCellValue();
//                System.out.println(i.intValue() + " " + whereValue + " " + whereCon.get(i.intValue()));
                String conditionValue = whereCon.get(i.intValue());
                if (!whereValue.equals(conditionValue)) {
                    isMatched = false;
                }
            }
            
            if (isMatched) {
                Cell c = r.getCell(columnIndex);
                if (c != null) {                
                    String content = c.getStringCellValue();
                    contentList.add(content);
                }
                
            }
            
            ri++;
        }

        return contentList;
    }
    
    public List<String> extractSingleColumn(File oriFile, String sheetNameKey, int columnIndex) {

        List<String> contentList = new ArrayList<String>();

        // Use a file, save memory
        Workbook workbook = null;
        try {
            workbook = openWorkbook(oriFile);
        } catch (InvalidFormatException | IOException e1) {
            e1.printStackTrace();
            return contentList;
        }

        Sheet sheet = workbook.getSheet(sheetNameKey);
        // Sheet sheet = workbook.getSheet(0);
        
        
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            int ri = 0;
            Row r = rowIterator.next();
            
            Cell c = r.getCell(columnIndex);
            if (c != null) {        
                if (c.getCellType() == Cell.CELL_TYPE_STRING) {
                    String content = c.getStringCellValue();
                    contentList.add(content);
                } else {
                    contentList.add(String.valueOf(c.getNumericCellValue()));
                }
                
            }

        }

        return contentList;
    }

}
