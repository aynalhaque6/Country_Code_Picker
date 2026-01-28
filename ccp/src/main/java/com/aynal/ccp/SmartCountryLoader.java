package com.aynal.ccp;

/*
 * SOFTWARE LICENSE AGREEMENT
 * Copyright (c) 2025 Md Aynal Haque. All rights reserved.
 *
 * This software and all related components, including source code, design, documentation, and assets, are the exclusive property of Md Aynal Haque and are protected under international copyright laws.
 *
 * You may NOT, without written permission from the owner:
 *
 * Copy, reproduce, or redistribute the software
 *
 * Modify, adapt, or create derivative works
 *
 * Sell, rent, lease, or sublicense the software
 *
 * Reverse engineer, decompile, or attempt to extract source code
 *
 * Publish or make the software available publicly
 *
 * The software is provided “AS IS,” without any warranty of any kind.
 * For licensing or permission inquiries, contact:
 * WhatsApp: +880 1856-859311
 */

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SmartCountryLoader {

    public static void loadCountries(Context context, String serverUrl, boolean isBengali, CountryDataCallback callback) {

        if (serverUrl == null || serverUrl.isEmpty()) {
            loadFromLocal(context, isBengali, callback);
            return;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            List<Country> serverList = new ArrayList<>();
            try {
                URL url = new URL(serverUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                if (conn.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    serverList = parseJson(context, response.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            List<Country> finalServerList = serverList;
            handler.post(() -> {
                if (!finalServerList.isEmpty()) {
                    callback.onDataLoaded(finalServerList);
                } else {
                    loadFromLocal(context, isBengali, callback);
                }
            });
        });
    }

    private static void loadFromLocal(Context context, boolean isBengali, CountryDataCallback callback) {
        List<Country> localList = JsonDataHelper.loadCountries(context, isBengali);
        callback.onDataLoaded(localList);
    }

    private static List<Country> parseJson(Context context, String jsonString) {
        List<Country> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String iso = obj.optString("iso", "");
                String code = obj.optString("code", "");
                String name = obj.optString("name", "");
                String hint = obj.optString("name", "");

                int flagResId = context.getResources().getIdentifier(
                        "flag_" + iso.toLowerCase(),
                        "drawable",
                        context.getPackageName()
                );
                if (flagResId == 0) flagResId = android.R.drawable.ic_menu_help;

                list.add(new Country(iso, code, name,hint, flagResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}