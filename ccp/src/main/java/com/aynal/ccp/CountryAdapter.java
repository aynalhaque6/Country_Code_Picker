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

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private final List<Country> mainList;
    private final List<Country> filteredList;
    private final OnItemClick listener;

    public interface OnItemClick {
        void onSelect(Country country);
    }

    public CountryAdapter(List<Country> data, OnItemClick listener) {
        this.mainList = data;
        this.filteredList = new ArrayList<>(data);
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(mainList);
        } else {
            String q = query.toLowerCase();
            for (Country c : mainList) {
                if (c.getName().toLowerCase().contains(q) ||
                        c.getDialCode().contains(q) ||
                        c.getIso().contains(q)) {
                    filteredList.add(c);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = filteredList.get(position);
        holder.tvName.setText(country.getName());
        holder.tvCode.setText(country.getDialCode());
        holder.ivFlag.setImageResource(country.getFlagRes());

        holder.itemView.setOnClickListener(v -> listener.onSelect(country));
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFlag;
        TextView tvName, tvCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFlag = itemView.findViewById(R.id.ivFlag);
            tvName = itemView.findViewById(R.id.tvCountryName);
            tvCode = itemView.findViewById(R.id.tvDialCode);
        }
    }
}