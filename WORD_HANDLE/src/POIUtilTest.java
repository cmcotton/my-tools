import word.POIWordUtil;
import junit.framework.TestCase;

public class POIUtilTest extends TestCase {

    public void testReadDoc() {
        try {
            String text = POIWordUtil.readDoc("D:/m化服務.doc");
            System.out.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testWriteDoc() {
        String wr;
        try {
            wr = POIWordUtil.readDoc("D:/m化服務.doc");

            boolean b = POIWordUtil.writeDoc("d:\\demo_new.doc", wr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}