/*
 * 版權宣告: FDC all rights reserved.
 */
package parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.mysql.cj.core.util.StringUtils;

import db.DBConnection;
import db.DBConnectionNative;
import entity.ArcEvent;
import entity.ArcEventCorrelation;
import entity.Event;
import errorhandle.MyCEFParsingException;

public class CEFParser {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, String> colKeyMapping;
    private Map<String, String> corrColKeyMapping;

    private DBConnectionNative db = new DBConnectionNative();

    public List<Event> parse(String line) throws MyCEFParsingException {
        List<Event> list = new ArrayList<Event>();

        JSONObject json = new JSONObject();

        String[] tokens = line.split("\\s[a-zA-Z0-9_]+=");
        logger.debug(line);

        // header
        try {
            String[] cefTokens = tokens[0].split("\\|");
            json = unserialize("name", cefTokens[5], json);
            json = unserialize("dvcVendor", cefTokens[1], json);
            json = unserialize("dvcProduct", cefTokens[2], json);
            json = unserialize("dvcEventClassId", cefTokens[4], json);
        } catch (Exception e) {
            logger.error("{}", e.toString());
            throw new MyCEFParsingException();
        }

        Pattern pattern = Pattern.compile("\\s[a-zA-Z0-9_]+=");
        Matcher matcher = pattern.matcher(line);

        String startVal = "";
        String endVal = "";
        String mrtVal = "";

        int i = 1;
        while (matcher.find()) {
            String key = matcher.group().replace("=", "").trim();
            String val = tokens[i++];
            logger.debug(key + " " + val);

            if ("start".equals(key)) {
                startVal = val;
            } else if ("end".equals(key)) {
                endVal = val;
            } else if ("rt".equals(key)) {
                if ("".equals(startVal)) {
                    startVal = val;
                }
                if ("".equals(endVal)) {
                    endVal = val;
                }
            } else if ("mrt".equals(key)) {
                mrtVal = val;
            } else {
                json = unserialize(key, val, json);
            }
        }

        JSONObject jsonCorr = json;

        Gson gson = new Gson();
        
        ArcEvent log = null;
        try {
            log = gson.fromJson(json.toString(), ArcEvent.class);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error(json.toString());
            throw new MyCEFParsingException();
        }        
        
        ArcEventCorrelation logCorr = null;
        try {
            if (json.has("BASE_EVENT_IDS")) {
                logCorr = gson.fromJson(jsonCorr.toString(), ArcEventCorrelation.class);
            }
        } catch (Exception e) {
            logger.error("{}", e.toString());
            
            throw new MyCEFParsingException();
        }

        // ArcEvent 補start and mrt
        if (!StringUtils.isEmptyOrWhitespaceOnly(startVal)) {
            Date temp = new Date(Long.parseLong(startVal));
            log.setSTART_TIME(temp);
        }

        if (!StringUtils.isEmptyOrWhitespaceOnly(mrtVal)) {
            log.setMANAGER_RECEIPT_TIME(new Date(Long.parseLong(mrtVal)));
        }

        // ArcEventCorrelation add end_time
        if (logCorr != null && !StringUtils.isEmptyOrWhitespaceOnly(endVal)) {
            logCorr.setEnd_time(new Date(Long.parseLong(endVal)));
        }

        // put into List
        list.add(log);
        if (logCorr != null) {
            list.add(logCorr);
        }
        return list;
    }

    private JSONObject unserialize(String key, String value, JSONObject json) {

        if ("dst".equals(key) || "dvc".equals(key) || "src".equals(key)) {
            long v = ipToLong(value);
            try {
                json.put(colKeyMapping.get(key), v);
            } catch (JSONException e) {
                logger.error(e.toString());
            }
        } else if ("eventId".equals(key)) {
            try {
                long eventId = Long.parseLong(value);
                json.put(colKeyMapping.get(key), eventId);
                json.put(corrColKeyMapping.get(key), eventId);
            } catch (JSONException e) {
                logger.error(e.toString());
            }

        } else if ("priority".equals(key) || "dpt".equals(key) || "spt".equals(key) || "cnt".equals(key)
                || "type".equals(key)) {
            try {
                json.put(colKeyMapping.get(key), Long.parseLong(value));
            } catch (JSONException e) {
                logger.error(e.toString());
            }
        } else if (colKeyMapping.containsKey(key)) {
            try {
                json.put(colKeyMapping.get(key), value);
            } catch (JSONException e) {
                logger.error(e.toString());
            }
        } else if (corrColKeyMapping.containsKey(key)) {
            try {
                json.put(corrColKeyMapping.get(key), value);
            } catch (JSONException e) {
                logger.error(e.toString());
            }
        }

        return json;
    }

    public CEFParser() {
        colKeyMapping = new HashMap();
        corrColKeyMapping = new HashMap();

        colKeyMapping.put("ahost", "ESM_HOST");
        colKeyMapping.put("eventId", "EVENT_ID");
        colKeyMapping.put("start", "START_TIME");
        colKeyMapping.put("name", "NAME");
        colKeyMapping.put("priority", "PRIORITY");

        colKeyMapping.put("src", "SRC_ADDRESS");
        colKeyMapping.put("spt", "SRC_PORT");
        colKeyMapping.put("sourceGeoCountryCode", "SOURCE_COUNTRY_NAME");
        colKeyMapping.put("dst", "DEST_ADDRESS");
        colKeyMapping.put("dpt", "DEST_PORT");
        colKeyMapping.put("destinationGeoCountryCode", "DESC_COUNTRY_NAME");
        colKeyMapping.put("dhost", "DEST_HOST_NAME");
        colKeyMapping.put("cnt", "BASE_EVENT_COUNT");
        colKeyMapping.put("mrt", "MANAGER_RECEIPT_TIME");
        colKeyMapping.put("act", "DVC_ACTION");
        colKeyMapping.put("cs1", "DVC_CUSTOM_STRING1");
        colKeyMapping.put("fname", "FILE_NAME");
        colKeyMapping.put("type", "EVENT_TYPE");

        colKeyMapping.put("c6a1", "DVC_CUSTOM_IPV6_ADDRESS1");
        colKeyMapping.put("c6a2", "DVC_CUSTOM_IPV6_ADDRESS2");
        colKeyMapping.put("c6a3", "DVC_CUSTOM_IPV6_ADDRESS3");
        colKeyMapping.put("c6a4", "DVC_CUSTOM_IPV6_ADDRESS4");
        colKeyMapping.put("cs2", "DVC_CUSTOM_STRING2");
        colKeyMapping.put("cs3", "DVC_CUSTOM_STRING3");
        colKeyMapping.put("cs4", "DVC_CUSTOM_STRING4");
        colKeyMapping.put("cs5", "DVC_CUSTOM_STRING5");
        colKeyMapping.put("cs6", "DVC_CUSTOM_STRING6");
        colKeyMapping.put("shost", "SRC_HOST_NAME");
        colKeyMapping.put("filePath", "FILE_PATH");

        colKeyMapping.put("suser", "SRC_USER_NAME");
        colKeyMapping.put("duser", "DEST_USER_NAME");
        colKeyMapping.put("dvc", "DVC_ADDRESS");
        colKeyMapping.put("dvchost", "DVC_HOST_NAME");
        colKeyMapping.put("deviceProcessName", "DVC_PROCESS_NAME");
        colKeyMapping.put("request", "REQUEST_URL");
        colKeyMapping.put("externalID", "EXTERNAL_ID");
        colKeyMapping.put("deviceOutboundInterface", "DVC_OUTBOUND_INTERFACE");

        colKeyMapping.put("categorySignificance", "CATEGORY_SIGNIFICANCE");
        colKeyMapping.put("categoryBehavior", "CATEGORY_BEHAVIOR");
        colKeyMapping.put("categoryDeviceGroup", "CATEGORY_DEVICE_GROUP");
        colKeyMapping.put("categoryOutcome", "CATEGORY_OUTCOME");
        colKeyMapping.put("categoryObject", "CATEGORY_OBJECT");

        colKeyMapping.put("dvcVendor", "DVC_VENDOR");
        colKeyMapping.put("dvcProduct", "DVC_PRODUCT");
        colKeyMapping.put("dvcEventClassId", "DVC_EVENT_CLASS_ID");
        colKeyMapping.put("cat", "DEVICE_EVENT_CATEGORY");
        
        // -----------------------------------------------------------------
        corrColKeyMapping.put("baseEventIds", "BASE_EVENT_IDS");
        corrColKeyMapping.put("eventId", "CORRELATED_EVENT_ID");
        corrColKeyMapping.put("end", "end_time");        
    }

    long ipToLong(String ipAddress) {

        String[] ipAddressInArray = ipAddress.split("\\.");

        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {

            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            result += ip * Math.pow(256, power);

        }

        return result;
    }

    public void handleParsingError(String line) {
        logger.error("parsing error: {}", line);

        try {
            db.connect();
            db.saveExceptionLog(line);
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            db.close();
        }
    }

    public static void main(String[] args) {

        String line;
        DBConnection db = new DBConnectionNative();

        try {
            // InputStream fis = new FileInputStream("d:/172.17.2.71_12.rt1");
            InputStream fis = new FileInputStream("d:/log3.txt");

            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);

            CEFParser parser = new CEFParser();

            db.connect();
            while ((line = br.readLine()) != null) {
                List<Event> evts = parser.parse(line);

                for (Event e : evts) {
                    db.save(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }
}
