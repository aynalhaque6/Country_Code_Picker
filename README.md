# 🌍 Custom Country Code Picker (CCP)

A professional, fully customizable, and lightweight Country Code Picker library for Android. It supports **Auto SIM Detection**, **Phone Number Validation**, **Offline/Online Data Loading**, and **Localization (English/Bengali)**.

![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
![Language](https://img.shields.io/badge/Language-Java-orange.svg)
![License](https://img.shields.io/badge/License-MIT-blue.svg)

## ✨ Features

* ✅ **Auto SIM Detection:** Automatically selects the country based on the user's SIM card.
* ✅ **Phone Number Validation:** Validates phone numbers using Google's `libphonenumber`.
* ✅ **Hybrid Data System:** Works offline (Assets) and supports online data updates via JSON URL.
* ✅ **Smart Search:** Search by Country Name, ISO Code, or Dial Code.
* ✅ **XML Customization:** Full control over UI elements via XML attributes (Hide/Show Flags, Codes, Name).
* ✅ **Localization:** Built-in support for English and Bengali languages.
* ✅ **Auto Formatting:** Automatically hints and formats the phone number based on the selected country.

---

## 📸 Screenshots

| Minimal View | Full View | Search Dialog | Validation |
|:---:|:---:|:---:|:---:|
| <img src="screenshots/minimal.png" width="200"/> | <img src="screenshots/full.png" width="200"/> | <img src="screenshots/dialog.png" width="200"/> | <img src="screenshots/validation.png" width="200"/> |

*(Note: Add your screenshots in a `screenshots` folder in your repo)*

---

## 🛠 Installation

### Step 1. Add the JitPack repository to your build file
Add it in your root `build.gradle` (or `settings.gradle`):

```gradle
dependencyResolutionManagement {
    repositories {
        ...
        maven { url '[https://jitpack.io](https://jitpack.io)' }
    }
}


dependencies {
    implementation 'com.github.YourUsername:RepoName:Tag'
    // Required for validation
    implementation 'com.googlecode.libphonenumber:libphonenumber:8.13.28'
}


<com.aynal.ccp.CountryCodePicker
    android:id="@+id/ccp"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:ccp_showFlag="true"
    app:ccp_showFullName="false"
    app:ccp_showNameCode="true"
    app:ccp_showPhoneCode="true"
    app:ccp_showArrow="true"
    app:ccp_autoDetectSim="true"
    app:ccp_defaultNameCode="BD"
    app:ccp_textSize="16sp"
    app:ccp_textColor="#000000"/>

// Optional: Set Online Data URL (If null, offline data is used)
        // ccp.setServerUrl("[https://your-domain.com/countries.json](https://your-domain.com/countries.json)");

        // 🔥 Register EditText for Validation & Hints
        ccp.registerCarrierNumberEditText(etPhone);

        btnSubmit.setOnClickListener(v -> {
            // Check if number is valid for the selected country
            if (ccp.isValidFullNumber()) {
                String fullNumber = ccp.getFullNumberWithPlus();
                Toast.makeText(this, "Valid: " + fullNumber, Toast.LENGTH_SHORT).show();
            } else {
                etPhone.setError("Invalid Phone Number");
            }
        });
