package io;

/*
 * 版權宣告: FDC all rights reserved.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileIO {

    public String statGroupByEventName(File file, Map<String, Long> map) {       
        
        String title = "";
        
        List<String> list = new ArrayList<String>();

        try {
            // BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "MS950"));
            // String line = "";
            while (br.ready()) {
                // System.out.println(">>>");
                String brStr = br.readLine();
                if (brStr != null && brStr.length() > 0) {
                    // System.out.println("1");
                    int c = brStr.charAt(0);
                    if (c == 65279) {
                        // System.out.println("2");
                        brStr = brStr.substring(1, brStr.length());

                    }

                    if (brStr.startsWith("編號")) {
                        title = brStr;
                        continue;
                    }
                    
                    String[] token = brStr.split("\t");
                    // System.out.println(token.length);

                    try {
                        Long.parseLong(token[0]);

                        String type = token[2];                        

                        if (map.containsKey(type)) {
                            Long i = map.get(type);
                            i = Long.valueOf(i.longValue() + 1);
                            map.put(type, i);
                        } else {
                            map.put(type, Long.valueOf(1));
                        }
                    } catch (Exception e) {
                        continue;
                    }

                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

         return title;
    }
    
    public String testReadfile(File file, Map<String, Long> map, Map<String, String> timeMap) {
        String title = "";
        
        List<String> list = new ArrayList<String>();

        try {
            // BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "MS950"));
            // String line = "";
            while (br.ready()) {
                // System.out.println(">>>");
                String brStr = br.readLine();
                if (brStr != null && brStr.length() > 0) {
                    // System.out.println("1");
                    int c = brStr.charAt(0);
                    if (c == 65279) {
                        // System.out.println("2");
                        brStr = brStr.substring(1, brStr.length());

                    }

                    if (brStr.startsWith("編號")) {
                        title = brStr;
                        continue;
                    }
                    
                    String[] token = brStr.split("\t");
                    // System.out.println(token.length);

                    try {
                        Long.parseLong(token[0]);

                        String dport = token[8];
                        String dip = token[7];
                        String sip = token[5];
                        String evt = token[3];
                        String type = token[2];
                        String grandKey = dport + "|" + dip + "|" + sip + "|" + evt + "|" + type;

                        if (map.containsKey(grandKey)) {
                            Long i = map.get(grandKey);
                            i = Long.valueOf(i.longValue() + 1);
                            map.put(grandKey, i);
                        } else {
                            map.put(grandKey, Long.valueOf(1));
                        }

                        String time = token[4];
                        if (timeMap.containsKey(grandKey)) {
                            String maxTime = timeMap.get(grandKey);
                            if (time.compareTo(maxTime) > 0) {
                                timeMap.put(grandKey, time);
                            } // no else
                        } else {
                            timeMap.put(grandKey, time);
                        }

                    } catch (Exception e) {
                        continue;
                    }

                    // list.add(brStr);
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

         return title;
    }

    /**
     * 將字串附加到指定檔案結尾
     * 
     * @param file
     *            要寫入的檔案名稱
     * @return list 文字檔, 每一行為一個element
     */
    public void readfile(File file, Map<String, Long> map) {
        List<String> list = new ArrayList<String>();

        try {
            // BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "MS950"));
            // String line = "";
            while (br.ready()) {
                // System.out.println(">>>");
                String brStr = br.readLine();
                if (brStr != null && brStr.length() > 0) {
                    // System.out.println("1");
                    int c = brStr.charAt(0);
                    if (c == 65279) {
                        // System.out.println("2");
                        brStr = brStr.substring(1, brStr.length());

                    }

                    if (brStr.startsWith("all ")) {
                        String[] token = brStr.split(", ");
                        
                        if ("POA300W4".equals(token[2]) || "POA510W".equals(token[2]) ||
                                "POA620W".equals(token[2])) {
                            System.out.println(token[2] + " " + file.getName());
                        }
                        
                        if (map.containsKey(token[2])) {
                            Long i = map.get(token[2]);
                            i = Long.valueOf(i.longValue() + Long.parseLong(token[3]));
                            map.put(token[2], i);
                        } else {
                            if (token.length == 4) {
                                map.put(token[2], Long.parseLong(token[3]));
                            } else {
                                System.out.println(token[2] + " " + file.getName());
                            }
                        }

                    }
                    // list.add(brStr);
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return list;
    }
    
    public void composeListToMap(List<String> oriList, Map<String, String> map) {
        
        for(String s :oriList ) {
            String[] token = s.split("\t");
            try {
                map.put(token[0], token[1]);
            } catch (Exception e) {
                continue;
            }
        }
    }

    public List<String> readFileIntoList(File file) {
        List<String> list = new ArrayList<String>();

        try {
            // BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), "utf-8"));
            // String line = "";
            while (br.ready()) {
                // System.out.println(">>>");
                String brStr = br.readLine();
                if (brStr != null && brStr.length() > 0) {
                    // System.out.println("1");
                    int c = brStr.charAt(0);
                    if (c == 65279) {
                        // System.out.println("2");
                        brStr = brStr.substring(1, brStr.length());

                    }

                    list.add(brStr);
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

         return list;
    }
    
    public void writefile(File file, String content) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, true), "MS950");
            // BufferedWriter bw = new BufferedWriter(out);
            out.write(content);
            // bw.newLine();
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String determineCounty(String fileName) {
        String[] token = fileName.split("_");

        return token[token.length - 1].substring(0, 1);
    }
}
