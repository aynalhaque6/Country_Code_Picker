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
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonDataHelper {

    public static List<Country> loadCountries(Context context, boolean isBengali) {
        List<Country> countryList = new ArrayList<>();
        String fileName = isBengali ? "countries_bn.json" : "countries_en.json";

        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonString = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String iso = obj.getString("iso");
                String code = obj.getString("code");
                String name = obj.getString("name");
                String hint = obj.optString("hint", "123456");

                int flagResId = context.getResources().getIdentifier(
                        "flag_" + iso.toLowerCase(),
                        "drawable",
                        context.getPackageName()
                );

                if (flagResId == 0) {
                    flagResId = android.R.drawable.ic_menu_help;
                }
                countryList.add(new Country(iso, code, name,hint, flagResId));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return countryList;
    }
}