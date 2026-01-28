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

public class Country {
    private String iso;
    private String dialCode;
    private String name;
    private String hint;
    private int flagRes;
    public Country(String iso, String dialCode, String name, String hint, int flagRes) {
        this.iso = iso;
        this.dialCode = dialCode;
        this.name = name;
        this.hint = hint;
        this.flagRes = flagRes;
    }

    public String getIso() { return iso; }
    public String getDialCode() { return dialCode; }
    public String getName() { return name; }
    public String getHint() { return hint; }
    public int getFlagRes() { return flagRes; }
}