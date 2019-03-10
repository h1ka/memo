package com.trap.memo.service;

import javax.net.ssl.*;
import java.io.InputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SSLService {

            public static byte[] gen(String noteName) throws Exception {
            // configure the SSLContext with a TrustManager
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
            String urlStr = String.format("https://avatars.dicebear.com/v2/identicon/%s.svg",noteName);

            URL url = new URL(urlStr);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
            InputStream inputStream = conn.getInputStream();
            byte[] bytes = new byte[1024];
            inputStream.read(bytes);
            System.out.println("connection identicon code: "+ conn.getResponseCode());
            conn.disconnect();
            return bytes;
        }

        private static class DefaultTrustManager implements X509TrustManager {

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }
}
