package word;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class WordReader {
    public static void main(String args[]) { 

        try {
            new WordReader().readWordFile(new File("D:\\todo\\103V1.doc"), null, null, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readWordFile(File wordFile, LinkedList<String> emailList, LinkedList<String> nameList,
            LinkedList<String> titleList, LinkedList<String> secList) throws Exception {

        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(wordFile));
        HWPFDocument document = new HWPFDocument(fs);

        Range range = document.getRange();           
        
        for (int i = 0; i < range.numParagraphs(); i++) {
            Paragraph tablePar = range.getParagraph(i);
            if (tablePar.isInTable()) {
                
                int nameI = -1;
                int emailI = -1;
                int titleI = -1;
                int secI = -1;
                
                Table table = range.getTable(tablePar);
                for (int rowIdx = 0; rowIdx < table.numRows(); rowIdx++) {
                    TableRow row = table.getRow(rowIdx);
                    for (int colIdx = 0; colIdx < row.numCells(); colIdx++) {
                        TableCell cell = row.getCell(colIdx);
                        String content = cell.getParagraph(0).text().trim();
                        
                        if (rowIdx == 0) {                            
                            if (content.equals("電子郵件帳號")) {
                                emailI = colIdx;
                                continue;
                            } else if (content.equals("姓名")) {
                                nameI = colIdx;
                                continue;
                            } else if (content.equals("職稱")) {
                                titleI = colIdx;
                                continue;
                            } else if (content.equals("科別")) {
                                secI = colIdx;
                                continue;
                            }
                        } else {
                            if (emailI == colIdx) {
                                emailList.add(content);
                            } else if (nameI == colIdx) {
                                nameList.add(content);
                            } else if (titleI == colIdx) {
                                titleList.add(content);
                            } else if (secI == colIdx) {
                                secList.add(content);
                            }

                        }
                    }
                    
                    // 沒有標題的欄位補空白
                    if (rowIdx > 0) {
                        if (emailI == -1) {
                            emailList.add("");
                        }

                        if (nameI == -1) {
                            nameList.add("");
                        }

                        if (titleI == -1) {
                            titleList.add("");
                        }

                        if (secI == -1) {
                            secList.add("");
                        }
                    }
                }
            }

        }


    }
}