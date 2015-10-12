package excel;

import io.FileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.StringUtils;

import sheet.PieChartSheet;
import sheet.SheetHandler;

public class ExcelDataWriter extends ExcelHandler {

    private final int WIDTH = 15;

    public void export(String fileName, String sheetName, List<List<String>> contents, List<List<String>> repeatContents) {
        Workbook specialDestbook = new XSSFWorkbook();
        Sheet destSheet = null;
        destSheet = specialDestbook.createSheet("事件列表");

        List<String> header = new ArrayList<>();
        header.add("事件單號");
        header.add("問題設備名稱");
        header.add("問題設備ip");
        header.add("問題設備管理人");
        header.add("權責單位一級");
        header.add("權責單位二級");
        header.add("權責單位三級");
        // header.add("事件類型1");
        // header.add("事件類型2");
        header.add("事件名稱");
        header.add("惡意程式類別");
        header.add("設備自訂字串1");
        header.add("檔案名稱");

        addRow(destSheet, header);
        for (List l : contents) {
            addRow(destSheet, l);
        }

        destSheet = specialDestbook.createSheet("重複事件");
        addRow(destSheet, header);
        if (repeatContents != null) {
            for (List l : repeatContents) {
                addRow(destSheet, l);
            }
        } else {
            List<String> none = new ArrayList<>();
            none.add("無");
            none.add("無");
            none.add("無");
            none.add("無");
            none.add("無");
            none.add("無");
            none.add("無");
            // header.add("事件類型1");
            // header.add("事件類型2");
            none.add("無");
            none.add("無");
            none.add("無");
            none.add("無");
            addRow(destSheet, none);
        }

        writeToAbsPath(fileName, specialDestbook);
    }

    public void exportOneSheet(String fileName, String sheetName, List<String> title, List<String> header, List<List<String>> contents, List<String> footer) {
        Workbook specialDestbook = new XSSFWorkbook();
        Sheet destSheet = null;
        destSheet = specialDestbook.createSheet(sheetName);

        // landscape
        destSheet.getPrintSetup().setLandscape(true);
        destSheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
        
        // margin
        destSheet.setMargin(XSSFSheet.TopMargin, 1);
        destSheet.setMargin(XSSFSheet.BottomMargin, 0.2);
        destSheet.setMargin(XSSFSheet.LeftMargin, 0.1);
        destSheet.setMargin(XSSFSheet.RightMargin, 0.1);

        // column width
        destSheet.setColumnWidth(0, 10 * 256);

        for (int i = 0; i < title.size(); i++) {
            destSheet.setColumnWidth(i, WIDTH * 256);
        }
        
        // background color
        XSSFCellStyle titleStyle = (XSSFCellStyle) specialDestbook.createCellStyle();
        titleStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(0xfe, 0x9a, 0x2e)));
        titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        titleStyle.setBorderBottom((short) 1);
        titleStyle.setBorderTop((short) 1);
        titleStyle.setBorderLeft((short) 1);
        titleStyle.setBorderRight((short) 1);
        titleStyle.setWrapText(true); // 自動換行
        
        if (title.size() > 0) {
            addRow(destSheet, title, titleStyle);
        }
        
        
        XSSFCellStyle gridLineStyle = (XSSFCellStyle) specialDestbook.createCellStyle();
        gridLineStyle.setBorderBottom((short) 1);
        gridLineStyle.setBorderTop((short) 1);
        gridLineStyle.setBorderLeft((short) 1);
        gridLineStyle.setBorderRight((short) 1);
        gridLineStyle.setWrapText(true); // 自動換行
        

        
        //合併第一列 9, 10
//        CellRangeAddress region = new CellRangeAddress(0, 0, 8, 9); 
//        destSheet.addMergedRegion(region);
        
//        addRow(destSheet, new ArrayList<String>());
        
        if (header.size() > 0) {
            String headerFont = HSSFHeader.fontSize((short) 14);
            Header h = destSheet.getHeader();
            h.setLeft(headerFont + HeaderFooter.date());
            h.setCenter(headerFont + "系統帳號清冊");
            h.setRight(headerFont + "機密等級:機密級");
//            addRow(destSheet, header, headerStyle);
        }

//        setBGColor(destSheet, 1, header.size(), headerStyle);
        
        for (List l : contents) {
            addRow(destSheet, l, gridLineStyle);
        }
        
        addRow(destSheet, new ArrayList<String>());
        addRow(destSheet, new ArrayList<String>());
        
        if (footer.size() > 0) {
            Footer f = destSheet.getFooter();
            f.setLeft("管理單位組長:");
            f.setCenter("第" + HeaderFooter.page() + "頁，共" + HeaderFooter.numPages() + "頁");
            f.setRight("使用單位   組長:__________ 科長:__________");
//            addRow(destSheet, footer);
        }
        
        
        writeToAbsPath(fileName, specialDestbook);
        
        FileIO io = new FileIO();
        try {
            io.saveAsPdf(fileName + "pdf", specialDestbook);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void exportStat(String fileName, String sheetName, List<List<String>> contents) {
        Workbook specialDestbook = new XSSFWorkbook();
        Sheet destSheet = null;
        destSheet = specialDestbook.createSheet(sheetName);

        List<String> header = new ArrayList<>();
        header.add("處室");
        header.add("破解工具");
        header.add("間諜軟體");
        header.add("廣告軟體");
        header.add("駭客工具");

        addRow(destSheet, header);
        for (List l : contents) {
            addRow(destSheet, l);
        }

        writeToAbsPath(fileName, specialDestbook);
    }

    /**
     * 
     * @param oriFile
     * @param specialEntity
     *            需要獨立出一個檔案的單位, e.g. 首長室
     */
    public void addRedoTableHeader(File oriFile, List<String> specialEntity) {
        try {

            Workbook workbook = openWorkbook(oriFile);

            splitBySheetName(oriFile, workbook, specialEntity);

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

        }
    }

    public void addRow(Sheet sheet, List<String> content) {

        int temp = sheet.getPhysicalNumberOfRows();
        int newLine = temp + 1;
        Row dr = sheet.getRow(temp);
        // Row dr = sheet.getRow(sheet.getLastRowNum() + 1);
        if (dr == null) {
            dr = sheet.createRow(temp);
            System.out.println(sheet.getSheetName() + " added line " + newLine);
        }

        for (int i = 0; i < content.size(); i++) {
            Cell cell = dr.createCell(i);
            cell.setCellValue(content.get(i));
        }
    }
    
    public void addRow(Sheet sheet, List<String> content, CellStyle style) {

        int temp = sheet.getPhysicalNumberOfRows();
        int newLine = temp + 1;
        Row dr = sheet.getRow(temp);
        // Row dr = sheet.getRow(sheet.getLastRowNum() + 1);
        if (dr == null) {
            dr = sheet.createRow(temp);
            System.out.println(sheet.getSheetName() + " added line " + newLine);
        }

        for (int i = 0; i < content.size(); i++) {
            Cell cell = dr.createCell(i);
            cell.setCellValue(content.get(i));
            cell.setCellStyle(style);
        }
    }
    
    public void setBGColor(Sheet sheet, int rowIdx, int rowSize, XSSFCellStyle style) {
        Row dr = sheet.getRow(rowIdx);
        
        for (int i = rowSize - 1; i >= 0; i--) {
            Cell cell = dr.getCell(i);
            cell.setCellStyle(style);
        }
    }

    /**
     * 專屬處理dashboard的輸出檔
     * 
     * @param oriFile
     */
    public void drawGridLine(File oriFile) {
        Workbook workbook = null;
        try {
            workbook = openWorkbook(oriFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 設定框線
        CellStyle styleRow1 = workbook.createCellStyle();

        styleRow1.setBorderBottom((short) 1);

        styleRow1.setBorderTop((short) 1);

        styleRow1.setBorderLeft((short) 1);

        styleRow1.setBorderRight((short) 1);

        styleRow1.setWrapText(true); // 自動換行

        writeToFile("d:/grid.xls", workbook);

    }

    /**
     * @param oriFile
     * @param workbook
     * @param sheetsNum
     * @param destbook
     */
    private void splitBySheetName(File oriFile, Workbook workbook, List<String> specialEntity) {

        boolean found = false;
        File orgFile = null;
        File allEmpl = new File("./org");
        File[] files = allEmpl.listFiles();
        for (File f : files) {
            String orgFileName = f.getName().replace(".csv", "");
            if (oriFile.getName().contains(orgFileName)) {
                orgFile = f;
                found = true;
                break;
            }

        }
        FileIO io = new FileIO();
        List<String> emplist = io.readFileIntoList(orgFile, FileIO.BIG5);
        Map<String, String> empNameId = new HashMap<>();

        for (String s : emplist) {
            String[] token = s.split(",");
            if (token != null && token.length == 6) {
                empNameId.put(token[2].trim(), token[5].trim());
            }
        }

        Workbook specialDestbook = new XSSFWorkbook();
        Workbook otherDestbook = new XSSFWorkbook();

        // 獲得工作薄（Workbook）中工作表（Sheet）的個數
        int sheetsNum = workbook.getNumberOfSheets();
        for (int i = 2; i < sheetsNum; i++) {
            Sheet sheet = workbook.getSheetAt(i); // (只能讀取，不能寫入)
            String sheetName = sheet.getSheetName();

            Sheet destSheet = null;
            if (specialEntity.contains(sheetName)) { // 獨立檔案
                destSheet = specialDestbook.createSheet(sheetName);
                // 補充 重複點選清單 標題
                // boolean isValid = copyContent(sheet, destSheet);
                copyContent(sheet, destSheet, empNameId);
                addRedoHeader(sheet, destSheet, true);

                setColumnWidth(destSheet);
            } else {
                destSheet = otherDestbook.createSheet(sheetName);
                // 補充 重複點選清單 標題
                // boolean isValid = copyContent(sheet, destSheet);
                copyContent(sheet, destSheet, empNameId);
                addRedoHeader(sheet, destSheet, true);

            }

            setColumnWidth(destSheet);
        }

        writeToFile("首長_" + oriFile.getName(), specialDestbook);
        writeToFile("其他_" + oriFile.getName(), otherDestbook);

    }

    void setColumnWidth(Sheet sheet) {
        // set width
        sheet.setColumnWidth(0, 12 * 256);
        sheet.setColumnWidth(1, 12 * 256);

        sheet.setColumnWidth(2, 25 * 256);

        sheet.setColumnWidth(3, 17 * 256);
        sheet.setColumnWidth(4, 12 * 256);

        sheet.setColumnWidth(5, 12 * 256);

    }

    /**
     * @param sheet
     * @param destSheet
     * @param isValid
     */
    private void addRedoHeader(Sheet sheet, Sheet destSheet, boolean isValid) {
        if (isValid) {
            return;
        } else {
            System.out.println(sheet.getSheetName() + " added.");

            Row dr = destSheet.getRow(sheet.getLastRowNum() + 4);
            if (dr == null) {
                dr = destSheet.createRow(sheet.getLastRowNum() + 4);
            }

            Cell datecell = dr.createCell(0);
            datecell.setCellValue("二、重複點選清單");
            //
            // Label label = new Label(0, sheet.getRows() + 1, "二、重複點選清單");
            // destSheet.addCell(label);

            dr = destSheet.createRow(sheet.getLastRowNum() + 5);
            datecell = dr.createCell(0);
            datecell.setCellValue("單位");

            datecell = dr.createCell(1);
            datecell.setCellValue("姓名");

            datecell = dr.createCell(2);
            datecell.setCellValue("email");
        }
    }

    /**
     * @param sheet
     * @param destSheet
     * @return
     */
    private void copyContent(Sheet sheet, Sheet destSheet, Map<String, String> empNameId) {
        // boolean isValid = false;

        for (int ri = 0; ri <= sheet.getLastRowNum(); ri++) {

            Row r = sheet.getRow(ri);
            if (r == null) {
                continue;
            }
            int lastColumn = r.getLastCellNum();

            // 新檔案建立 new row
            Row dr = destSheet.getRow(ri);
            if (dr == null)
                dr = destSheet.createRow(ri);

            for (int ci = 0; ci < lastColumn; ci++) {
                Cell c = r.getCell(ci, Row.RETURN_BLANK_AS_NULL);
                if (c != null) {
                    String content = "";

                    if (c.getCellType() == Cell.CELL_TYPE_STRING) {
                        content = c.getStringCellValue();
                    } else if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        // System.out.println(c.getNumericCellValue());
                        content = Integer.toString((int) c.getNumericCellValue());

                    }
                    Cell dc = dr.createCell(ci);
                    dc.setCellValue(content);

                    if (ci == 1 && "姓名".equals(content)) {
                        Cell newCol = dr.createCell(lastColumn);
                        newCol.setCellValue("員工代號");
                    } else if (ci == 1 && empNameId.containsKey(content)) {
                        Cell newCol = dr.createCell(lastColumn);
                        newCol.setCellValue(empNameId.get(content));
                    }
                }

            }

            // add emp id

        }
        // return isValid;
    }

    public void exportExcel(Map<String, Long> map1, Map<String, Long> map2, Map<String, Long> map3,
            Map<String, Long> map4, Map<String, Long> map5, Map<String, Long> map6, Map<String, Long> map7,
            Map<String, Long> map8, Map<String, Long> map9, Map<String, Long> map10, Map<String, Long> map11,
            Map<String, Long> map12, Map<String, Long> violatePolicyStat, Map<String, Long> abnormalAttackStat,
            Map<String, Long> virusStat, Map<String, Long> deviceAbnormalStat, String sourceFullName,
            String thisMonthString) {
        // Load sample excel file

        Workbook workbook = null;

        try {
            workbook = openWorkbook(new File("Template.xlsx"));
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }

        // sample.xls

        Map aggregateMap = new HashMap();
        aggregateMap.putAll(virusStat);
        aggregateMap.putAll(violatePolicyStat);
        aggregateMap.putAll(deviceAbnormalStat);
        aggregateMap.putAll(abnormalAttackStat);

        SheetHandler sh = new SheetHandler();
        // sh.insertIntoSheet(map1, map2, map3, map4, map5, map6, map7, map8,
        // map9, map10, map11, map12, workbook, 0);

        sh = new PieChartSheet();

        int si = 1;
        // 表二
        sh.addNewColumn(aggregateMap, workbook, si++, thisMonthString);
        // sh.insertPieChart(violatePolicyStat, workbook, si++,
        // "ViolatePolicyEvt", "ViolatePolicyPie");

        sh.addNewColumn(violatePolicyStat, workbook, si++, thisMonthString);
        // sh.insertPieChart(abnormalAttackStat, workbook, si++,
        // "AbnormalAttackEvt", "AbnormalAttackPie");
        sh.addNewColumn(abnormalAttackStat, workbook, si++, thisMonthString);
        // sh.insertPieChart(virusStat, workbook, si++, "VirusEvt", "VirusPie");
        sh.addNewColumn(virusStat, workbook, si++, thisMonthString);
        // sh.insertPieChart(deviceAbnormalStat, workbook, si++,
        // "DeviceAbnormalEvt", "DeviceAbnormalPie");
        sh.addNewColumn(deviceAbnormalStat, workbook, si++, thisMonthString);

        writeToFile(sourceFullName, workbook);
    }

    public void exportExcel(Map<String, Long> allAttributeMap, Map<String, Long> violatePolicyStat,
            Map<String, Long> abnormalAttackStat, Map<String, Long> virusStat, Map<String, Long> deviceAbnormalStat,
            String targetNamePrefix, String thisMonthString, String countyTag) {

        String targetFileName = targetNamePrefix + "_" + countyTag + ".xlsx";
        // Load dashboard excel file
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(OPCPackage.open(new FileInputStream("./output/" + targetFileName)));
        } catch (InvalidFormatException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } // or
          // sample.xls

        Map aggregateMap = new HashMap();
        aggregateMap.putAll(virusStat);
        aggregateMap.putAll(violatePolicyStat);
        aggregateMap.putAll(deviceAbnormalStat);
        aggregateMap.putAll(abnormalAttackStat);

        SheetHandler sh = new SheetHandler();
        // sh.insertIntoSheet(map1, map2, map3, map4, map5, map6, map7, map8,
        // map9, map10, map11, map12, workbook, 0);

        sh = new PieChartSheet();

        int si = 0;
        // 表一
        sh.addNewColumn(allAttributeMap, workbook, si++, thisMonthString);
        // 表二
        sh.addNewColumn(aggregateMap, workbook, si++, thisMonthString);
        // sh.insertPieChart(violatePolicyStat, workbook, si++,
        // "ViolatePolicyEvt", "ViolatePolicyPie");

        sh.addNewColumn(violatePolicyStat, workbook, si++, thisMonthString);
        // sh.insertPieChart(abnormalAttackStat, workbook, si++,
        // "AbnormalAttackEvt", "AbnormalAttackPie");
        sh.addNewColumn(abnormalAttackStat, workbook, si++, thisMonthString);
        // sh.insertPieChart(virusStat, workbook, si++, "VirusEvt", "VirusPie");
        sh.addNewColumn(virusStat, workbook, si++, thisMonthString);
        // sh.insertPieChart(deviceAbnormalStat, workbook, si++,
        // "DeviceAbnormalEvt", "DeviceAbnormalPie");
        sh.addNewColumn(deviceAbnormalStat, workbook, si++, thisMonthString);

        writeToFile(targetFileName, workbook);
    }

    /**
     * @param sourceFullName
     * @param workbook
     */
    private void writeToFile(String targetFileName, Workbook workbook) {

        // final String destFilePath = "./output/chart_" + sourceFullName;
        final String destFilePath = "./output/" + targetFileName;
        FileOutputStream f = null;
        try {
            f = new FileOutputStream(destFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Number Of Sheets:" + workbook.getNumberOfSheets());
        // Sheet s = workbook.getSheetAt(0);
        // System.out.println("Number Of Rows:" + s.getLastRowNum());
    }

    private void writeToAbsPath(String targetFileName, Workbook workbook) {

        // final String destFilePath = "./output/chart_" + sourceFullName;
        final String destFilePath = targetFileName;
        FileOutputStream f = null;
        try {
            f = new FileOutputStream(destFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Number Of Sheets:" + workbook.getNumberOfSheets());
        // Sheet s = workbook.getSheetAt(0);
        // System.out.println("Number Of Rows:" + s.getLastRowNum());
    }

    private Map maxMap(Map[] allMaps) {
        Map biggestMap = null;
        int max = 0;
        for (Map m : allMaps) {
            if (m.size() > max) {
                max = m.size();
                biggestMap = m;
            }
        }

        return biggestMap;

    }

    public void convertCsvToExcel(File sourceFile) {
        FileIO io = new FileIO();
        List<String> list = io.readFileIntoList(sourceFile, FileIO.BIG5);

        Workbook workbook = createWorkbook();
        Sheet sheet = workbook.createSheet();

        // set width
        sheet.setColumnWidth(0, 10 * 256);

        for (int i = 1; i < 4; i++) {

            sheet.setColumnWidth(i, WIDTH * 256);
        }

        for (int i = 4; i < 9; i++) {
            sheet.setColumnWidth(i, 19 * 256);
        }
        sheet.setColumnWidth(9, 5 * 256);
        sheet.setColumnWidth(10, 5 * 256);

        boolean needBorder = false;

        for (int i = 0; i < list.size(); i++) {
            Row r = sheet.createRow(i);

            String line = list.get(i);

            String[] token = line.split("\t");
            if (token.length == 0) {
                continue;
            }

            String lead = token[0];
            if (lead.startsWith("編號") || lead.startsWith("排名")) {
                needBorder = true;
            } else if (line.trim().length() == 0) {
                needBorder = false;
            }

            short maxRows = 0;
            for (int j = 0; j < token.length; j++) {
                String content = token[j];

                short temp = (short) Math.ceil((double) content.length() * 256 / sheet.getColumnWidth(j));
                if (temp > maxRows) {
                    maxRows = temp;
                }

                if ("狀態".equals(content)) {
                    content = "說明";
                } else if ("未結案".equals(content)) {
                    content = "略";
                }
                // Create a new cell in current row
                Cell cell = r.createCell(j);
                // Set value to new value
                cell.setCellValue(content);

                if (needBorder) {
                    drawBorder(workbook, cell);
                }

            }

            if (needBorder && maxRows > 1) {
                r.setHeight((short) (maxRows * 256));
            }
        }

        writeToFile(sourceFile.getName(), workbook);
    }

    public static void main(String[] args) {
        ExcelDataWriter w = new ExcelDataWriter();
        w.drawGridLine(new File("D:/testDashboard/source/EventListByQuery_20140331191435_A.xls"));
    }

}
