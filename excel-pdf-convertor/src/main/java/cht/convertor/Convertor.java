package cht.convertor;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Convertor {
    private String path;
    public static boolean runFlag = false;

    public Convertor(String path) {
        this.path = path;
    }

    public void saveExcelAsPdf(String filePath, String outFile) {
        ComThread.InitSTA();
        ActiveXComponent actcom = new ActiveXComponent("Excel.Application");
        try {
            System.out.println((new Date()).toString() + "  start convert from : " + filePath + " to " + outFile);
            actcom.setProperty("Visible", new Variant(false));
            Dispatch excels = actcom.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.invoke(excels, "Open", Dispatch.Method,
                    new Object[] { filePath, new Variant(false), new Variant(false) }, new int[9]).toDispatch();
            Dispatch.invoke(excel, "SaveAs", Dispatch.Method, new Object[] { outFile, new Variant(57),
                    new Variant(false), new Variant(57), new Variant(57), new Variant(false), new Variant(true),
                    new Variant(57), new Variant(false), new Variant(true), new Variant(false) }, new int[1]);
            Dispatch.call(excel, "Close", new Variant(false));
            if (actcom != null) {
                actcom.invoke("Quit", new Variant[] {});
                actcom = null;
            }
            ComThread.Release();
            File temp = new File(filePath);
            temp.renameTo(new File(filePath + "." + getDateStr()));
            temp = new File(filePath);
            temp.deleteOnExit();
            temp = null;
            System.out.println((new Date()).toString() + "  convert ok : " + filePath + " to " + outFile);
        } catch (Exception es) {
            es.printStackTrace();
        }
    }

    public void listAllFile() {
        runFlag = true;
        String fileName = "", appdex = "";
        File temp = null;
        try {
            File[] list = new File(path).listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    boolean x = false;
                    if (pathname.getName().toLowerCase().endsWith(".xlsx")) {
                        x = true;
                    }
                    return x;
                }
            });
            System.out.println((new Date()).toString() + "  Total Convert File : " + list.length);
            for (int i = 0; i < list.length; i++) {
                fileName = list[i].getName().substring(0, list[i].getName().indexOf("."));
                appdex = list[i].getName().substring(list[i].getName().indexOf("."));
                temp = new File(path + fileName + ".pdf");
                if (temp.exists()) {
                    temp.renameTo(new File(path + fileName + "-" + getDateStr() + ".pdf"));
                }
                saveExcelAsPdf(path + fileName + appdex, path + fileName + ".pdf");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        runFlag = false;
    }

    public String getDateStr() {
        Calendar cl = Calendar.getInstance();
        cl.setTime(new Date());
        String str = cl.get(Calendar.YEAR) + "" + (cl.get(Calendar.MONTH) + 1) + "" + cl.get(Calendar.DATE) + ""
                + cl.get(Calendar.HOUR) + "" + cl.get(Calendar.MINUTE) + "" + cl.get(Calendar.SECOND);
        return str;
    }
    
    public static void main(String[] args) {
        Convertor test = new Convertor("d:/logs/");
        test.listAllFile();
    }
}