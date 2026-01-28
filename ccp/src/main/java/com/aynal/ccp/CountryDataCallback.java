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

import java.util.List;

public interface CountryDataCallback {
    void onDataLoaded(List<Country> countries);
}