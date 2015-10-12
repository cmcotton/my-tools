package excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sheet.ICSTDNParser;
import sheet.ICSTIPParser;
import sheet.Parser;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelDataReaderXLS {

    public void addSheetHeader(File oriFile) {
        try {

            // 創建唯讀的 Excel 工作薄的物件(只能讀取，不能寫入)

            Workbook workbook = Workbook.getWorkbook(oriFile);
            WritableWorkbook destbook = Workbook.createWorkbook(new File("d:/testawareness/dest/refined_"
                    + oriFile.getName()));

            // 獲得工作薄（Workbook）中工作表（Sheet）的個數

            int sheetsNum = workbook.getNumberOfSheets();
            // System.out.println(sheetsNum);

            // 返回工作薄（Workbook）中工作表（Sheet）物件陣列

            Sheet[] sheets = workbook.getSheets();
            //
            // // 接著建立 Sheet，Workbook可以看成是一個excel檔案，Sheet顧名思義就是一個頁籤。
            //
            // // 可以直接 getSheet(0) 表示第一個頁籤(從0開始算)
            //
            // // 也可以透過 getSheet("頁籤名稱") 來取得頁籤
            //
            
            
            for (int i = 2; i < sheets.length; i++) {
                Sheet sheet = workbook.getSheet(i); // (只能讀取，不能寫入)

                WritableSheet destSheet = destbook.createSheet(sheet.getName(), i - 2);
                // 獲取 Sheet 的名稱
                //
                System.out.println(sheet.getName());
                //
                // 獲取 Sheet 表中所包含的總列數
                // System.out.println(sheet.getRows());
                // System.out.println(sheet.getColumns());

                boolean isValid = false;

                for (int ri = 0; ri < sheet.getRows(); ri++) {
                    for (int ci = 0; ci < sheet.getColumns(); ci++) {
                        String content = sheet.getCell(ci, ri).getContents();

                        Label label = new Label(ci, ri, content);
                        destSheet.addCell(label);

                        if (content.startsWith("二、重複點選清單")) {
                            isValid = true;
                            // break;
                        }
                    }
                    // System.out.println();

                }

                if (!isValid) {
                    System.out.println(sheet.getName() + " add");
                    Label label = new Label(0, sheet.getRows() + 1, "二、重複點選清單");
                    destSheet.addCell(label);

                    label = new Label(0, sheet.getRows() + 2, "單位");
                    destSheet.addCell(label);

                    label = new Label(1, sheet.getRows() + 2, "姓名");
                    destSheet.addCell(label);

                    label = new Label(2, sheet.getRows() + 2, "email");
                    destSheet.addCell(label);
                }

            }

            destbook.write();
            destbook.close();

            workbook.close();

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

        }
    }

    public LinkedHashMap<String, Long> composeRedoMap(File oriFile) {
        LinkedHashMap<String, Long> contentap = null;

        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(oriFile);
        } catch (BiffException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheet(0);
        for (int ri = 0; ri < sheet.getRows(); ri++) {

            String content = sheet.getCell(0, ri).getContents();

            if (content.contains("重覆點選表")) {
                contentap = insertMap(sheet, ri);
            }
        }

        return contentap;
    }

    public LinkedHashMap<String, Long> composeClickMap(File oriFile, int time) {
        LinkedHashMap<String, Long> contentap = null;

        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(oriFile);
        } catch (BiffException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheet(0);
        for (int ri = 0; ri < sheet.getRows(); ri++) {

            String content = sheet.getCell(0, ri).getContents();

            if ((time == 1 && content.contains("第一次)演練結果統計表")) || (time == 2 && content.contains("第二次)演練結果統計表"))) {
                contentap = insertMap(sheet, ri);
            }
        }

        return contentap;
    }

    /**
     * 
     * @param oriFile
     * @param sheetNameKey
     * @param columnIndex
     * @param whereCon
     * @return
     */
    public List<String> extractSingleColumn(File oriFile, String sheetNameKey, int columnIndex, Map<Integer, String> whereCon) {

        List<String> contentList = new ArrayList<String>();

        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(oriFile);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sheet = workbook.getSheet(sheetNameKey);
        // Sheet sheet = workbook.getSheet(0);

        for (int ri = 1; ri < sheet.getRows(); ri++) {

            Set<Integer> conditionColumn = whereCon.keySet();
            for (Integer i : conditionColumn) {
                String value = sheet.getCell(i, ri).getContents();
                if (value.equals(whereCon.get(i.intValue()))) {
                    String content = sheet.getCell(columnIndex, ri).getContents();
                    contentList.add(content);
                }
            }
            

        }

        return contentList;
    }
    
    public Map<String, String> extractTwoColumn(File oriFile, String sheetNameKey, int keyColumnIndex, int valueColumnIndex) {

        Map<String, String> contentMap = new HashMap<String, String>();

        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(oriFile);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sheet = workbook.getSheet(sheetNameKey);
        // Sheet sheet = workbook.getSheet(0);

        for (int ri = 1; ri < sheet.getRows(); ri++) {

            String key = sheet.getCell(keyColumnIndex, ri).getContents();
            String value = sheet.getCell(valueColumnIndex, ri).getContents();
            contentMap.put(key, value);

        }

        return contentMap;
    }

    public LinkedHashMap<String, Long> extractSingleRowByTitle(File oriFile, String title) {
        LinkedHashMap<String, Long> contentap = new LinkedHashMap<String, Long>();

        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(oriFile);
        } catch (BiffException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheet(0);

        boolean isFinished = false;

        for (int ri = 0; ri < sheet.getRows(); ri++) {
            for (int ci = 0; ci < sheet.getColumns(); ci++) {

                String content = sheet.getCell(ci, ri).getContents();

                // 標題符合就取出整個row的資料
                if ((title.equals(content))) {
                    while (!isFinished) {
                        long number = 0;
                        try {
                            number = Long.parseLong(sheet.getCell(++ci, ri).getContents());
                            String date = sheet.getCell(ci, 0).getContents();

                            contentap.put(date, number);
                        } catch (Exception e) {
                            isFinished = true;
                            break;
                        }

                    } // end while

                }

                if (isFinished) {
                    break;
                }
            } //
            if (isFinished) {
                break;
            }
        }

        return contentap;
    }

    public LinkedHashMap insertMap(Sheet sheet, int rowOffset) {
        LinkedHashMap<String, Double> contentMap = new LinkedHashMap<String, Double>();

        String org = sheet.getCell(0, rowOffset + 2).getContents();
        String temp = sheet.getCell(3, rowOffset + 2).getContents();
        for (int j = 1; !org.equals(""); j++) {

            temp = temp.replace("%", "");
            double percent = Double.parseDouble(temp);
            contentMap.put(org, Double.valueOf(percent));
            org = sheet.getCell(0, rowOffset + j + 2).getContents();
            temp = sheet.getCell(3, rowOffset + j + 2).getContents();
        }

        return contentMap;
    }

    public void saveExcelSheetAsCSV(File oriFile, int index) {

        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(oriFile);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sheet = workbook.getSheet(index);
        Parser parser = new ICSTDNParser();        
        if (sheet.getName().contains("DN")) {
            
        } else if (sheet.getName().contains("IP")) {
            parser = new ICSTIPParser();
        }

        parser.saveContent(sheet);

    }

    
    public Map<String, String> extractTwoColumn(File oriFile, String sheetNameKey, int keyColumnIndex, 
            int valueColumnIndex, Map<Integer, String> whereCon) {
                return null;
    
    }
}
