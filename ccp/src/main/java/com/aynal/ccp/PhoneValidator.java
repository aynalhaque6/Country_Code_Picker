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

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class PhoneValidator {
    public static boolean isValid(String phoneNumber, String countryIsoCode) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, countryIsoCode.toUpperCase());
            return phoneUtil.isValidNumber(numberProto);
        } catch (NumberParseException e) {
            return false;
        }
    }

    public static String getFormattedNumber(String phoneNumber, String countryIsoCode) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, countryIsoCode.toUpperCase());
            return phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            return null;
        }
    }
}