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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.List;

public class CountryPicker {
    private Context context;
    private boolean isBengali;
    private String serverUrl = null;
    public CountryPicker(Context context, boolean isBengali) {
        this(context, isBengali, null);
    }
    public CountryPicker(Context context, boolean isBengali, String serverUrl) {
        this.context = context;
        this.isBengali = isBengali;
        this.serverUrl = serverUrl;
    }

    public void show(CountryAdapter.OnItemClick listener) {
        // Loading Dialog

        SmartCountryLoader.loadCountries(context, serverUrl, isBengali, countries -> {

            BottomSheetDialog dialog = new BottomSheetDialog(context);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_country_picker, null);
            dialog.setContentView(view);

            TextView tvTitle = view.findViewById(R.id.tvTitle);
            EditText etSearch = view.findViewById(R.id.etSearch);
            RecyclerView rv = view.findViewById(R.id.rvCountries);

            tvTitle.setText(isBengali ? "দেশ নির্বাচন করুন" : "Select Country");

            rv.setLayoutManager(new LinearLayoutManager(context));
            CountryAdapter adapter = new CountryAdapter(countries, country -> {
                listener.onSelect(country);
                dialog.dismiss();
            });
            rv.setAdapter(adapter);

            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.filter(s.toString());
                }
                @Override
                public void afterTextChanged(Editable s) {}
            });

            dialog.show();
        });
    }
}