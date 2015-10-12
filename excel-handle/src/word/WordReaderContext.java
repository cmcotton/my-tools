/*
 * 版權宣告: FDC all rights reserved.
 */
package word;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;

import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

/**
 * 程式資訊摘要：
 * <P>
 * 類別名稱　　：WordReaderFactory.java
 * <P>
 * 程式內容說明：
 * <P>
 * 程式修改記錄：
 * <P>
 * XXXX-XX-XX：
 * <P>
 * 
 * @author chtd
 * @version 1.0
 * @since 1.0
 */
public class WordReaderContext {
    
    public static void main(String[] args) {
        new WordReaderContext().getTableContent(new File("D:\\todo\\103V1.doc"), 
                new LinkedList<String>(), new LinkedList<String>(), 
                new LinkedList<String>(), new LinkedList<String>());
    }

    public void getTableContent(File path, LinkedList<String> emailList, LinkedList<String> nameList,
            LinkedList<String> titleList, LinkedList<String> secList) {

        try {
            WordReader reader = null;
            
            InputStream in = new FileInputStream(path);
            StringBuffer output = new StringBuffer();
            POITextExtractor textExtractor = ExtractorFactory.createExtractor(in);
            if (textExtractor instanceof WordExtractor) { // doc, word 97-2003
                reader = new WordReader();                

            } else if (textExtractor instanceof XWPFWordExtractor) { // docx, word 2007
            
                reader = new Word2007Reader();
                
            }
            
            // strategy
            reader.readWordFile(path, emailList, nameList, titleList, secList);

        } catch (Exception e) {

        }
    }

}
