package download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class FileDownloader {

    public static void disableCertVarification() throws NoSuchAlgorithmException, KeyManagementException {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = createTrustManager();

        // Install the all-trusting trust manager
        installAllTrustingHostVerifier(trustAllCerts);

    }

    /**
     * @param trustAllCerts
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private static void installAllTrustingHostVerifier(TrustManager[] trustAllCerts) throws NoSuchAlgorithmException,
            KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {

            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return false;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    /**
     * @return
     */
    private static TrustManager[] createTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
        return trustAllCerts;
    }

    public static void main(String[] args) {

        String[] files = { "https://1.1.1.1/publish/SocApi/QD4sf89sd45vP/0",
                "https://1.1.1.1/publish/SocApi/QD4sf89sd45vP/1" };

        try {
            System.out.println("disableCertVarification");
            disableCertVarification();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < files.length; i++) {
            try {
                String file = files[i];
                System.out.println("Try to download " + file);
                URL url = new URL(file);
                URLConnection urlConn = url.openConnection();
                BufferedInputStream is = new BufferedInputStream(urlConn.getInputStream());
                File out = new File("D:\\", i + ".csv");
                BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(out));
                byte[] b = new byte[8 * 1024];
                int read = 0;
                while ((read = is.read(b)) > -1) {
                    bout.write(b, 0, read);
                }
                bout.flush();
                bout.close();
                is.close();

            } catch (Exception mfu) {
                mfu.printStackTrace();
            }
        }

        System.out.println(files.length + " files have been Downloaded.");

    }

}