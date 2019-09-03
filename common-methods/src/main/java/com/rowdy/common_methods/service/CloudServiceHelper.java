package com.rowdy.common_methods.service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CloudServiceHelper {
    public CloudServiceHelper() {
    }

    public static String toString(InputStream stream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();

        try {
            String line;
            try {
                while((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } catch (Exception var13) {
                var13.printStackTrace();
            }
        } finally {
            try {
                stream.close();
            } catch (IOException var12) {
                var12.printStackTrace();
            }

        }

        return sb.toString();
    }

    /** @deprecated */
    @Deprecated
    public static String getPayloadJSON(Map<String, Object> nameValueParams) throws JSONException {
        String payload = "";
        if (nameValueParams != null && !nameValueParams.isEmpty()) {
            Set<String> keys = nameValueParams.keySet();
            if (keys.size() > 0) {
                JSONObject json = new JSONObject();
                Iterator var4 = keys.iterator();

                while(var4.hasNext()) {
                    String key = (String)var4.next();
                    json.put(key, nameValueParams.get(key));
                }

                payload = json.toString();
            }
        }

        return payload;
    }
}
