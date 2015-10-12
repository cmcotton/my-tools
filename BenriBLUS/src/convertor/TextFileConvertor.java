package convertor;

import io.FileIO;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TextFileConvertor implements Convertor {
    
    String targetFilePath = "./output/AHEAD.csv";

    @Override
    public void generateBlusFile(File file) {
        
        FileIO io = new FileIO();
        List<String> list = io.readFileIntoList(file);
        
        String today = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        
        File newDNFile = new File(targetFilePath);
//        File newIPFile = new File("./output/FORE_IP.csv");
        io.writefile(newDNFile, "SN,DN-List,First-Date,Last-Date\n"); // title
//        io.writefile(newIPFile, "SN,DN-List,First-Date,Last-Date\n"); // title
        
        int dnCount = 1;
//        int ipCount = 1;
        
        for(int i = 4; i < list.size(); i++) {
            String domainOrIPAddr = list.get(i);
            String[] token = domainOrIPAddr.split(".");
            
//            if (token.length == 4) { // 
//                try {
//                    Integer.parseInt(token[0]);
//                    Integer.parseInt(token[1]);
//                    Integer.parseInt(token[2]);
//                    Integer.parseInt(token[3]);
//                    io.writefile(newIPFile, ipCount++ + "," + domainOrIPAddr + "," + today + "," + today + "\n");
//                } catch (Exception e) {
//                    io.writefile(newDNFile, dnCount++ + "," + domainOrIPAddr + "," + today + "," + today + "\n");
//                }
//            } else { // domain
                io.writefile(newDNFile, dnCount++ + "," + domainOrIPAddr + "," + today + "," + today + "\n");
//            }            
        }
        
        
    }

    /* (non-Javadoc)
     * @see convertor.Convertor#deleteOldFile(java.io.File)
     */
    @Override
    public void deleteOldFile(File file) {
        File targetFile = new File(targetFilePath);
        targetFile.delete();
        
    }

}
