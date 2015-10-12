package word;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


public class POIWordUtil {
    /**
     * 讀入doc
     * 
     * @param doc
     * @return
     * @throws Exception
     */
    public static String readDoc(String doc) throws Exception {
        // 創建輸入流讀取DOC文件
        FileInputStream in = new FileInputStream(new File(doc));
        WordExtractor extractor = null;
        String text = null;
        // 創建WordExtractor
        extractor = new WordExtractor(in);
        // 對DOC文件進行提取
        text = extractor.getText();
        return text;
    }

    /**
     * 寫出doc
     * 
     * @param path
     * @param content
     * @return
     */
    public static boolean writeDoc(String path, String content) {
        boolean w = false;
        try {

            // byte b[] = content.getBytes("ISO-8859-1");
            byte b[] = content.getBytes();

            ByteArrayInputStream bais = new ByteArrayInputStream(b);

            POIFSFileSystem fs = new POIFSFileSystem();
            DirectoryEntry directory = fs.getRoot();

            DocumentEntry de = directory.createDocument("WordDocument", bais);

            FileOutputStream ostream = new FileOutputStream(path);

            fs.writeFilesystem(ostream);

            bais.close();
            ostream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return w;
    }

}