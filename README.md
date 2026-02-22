# Country Code Picker (CCP) — com.aynal.ccp

এই লাইব্রেরিটি Android অ্যাপে **দেশের ফ্ল্যাগ + ডায়াল কোড (+880) + নাম/কোড** দেখাতে এবং **ফোন নাম্বার ভ্যালিডেশন** করতে ব্যবহার হয়।

---

## Features

- ✅ Country list dialog থেকে দেশ সিলেক্ট
- ✅ Flag show/hide
- ✅ Phone code show/hide (+880)
- ✅ Name/NameCode show/hide (BD/US)
- ✅ SIM-based auto detect (optional)
- ✅ Default country set (BD/US/IN)
- ✅ Phone number validation (libphonenumber based)
- ✅ Programmatic country set (NameCode / DialCode) *(যদি আপনার আপডেটেড ভার্সনে যোগ করা থাকে)*
- ✅ Country change listener *(যদি আপনার আপডেটেড ভার্সনে যোগ করা থাকে)*

---

## Installation (Module)

যদি আপনার প্রোজেক্টে `ccp` আলাদা মডিউল হিসেবে থাকে:

### `settings.gradle`
```gradle
include ':ccp'
```

### `app/build.gradle`
```gradle
dependencies {
    implementation project(':ccp')
}
```

Sync করুন।

---

## XML Usage

### Basic Example
```xml
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
```

---

## Supported XML Attributes

| Attribute | কাজ |
|---|---|
| `ccp_showFlag` | ফ্ল্যাগ দেখাবে/লুকাবে |
| `ccp_showFullName` | দেশের পুরো নাম দেখাবে |
| `ccp_showNameCode` | ISO কোড দেখাবে (BD/US/IN) |
| `ccp_showPhoneCode` | ডায়াল কোড দেখাবে (+880) |
| `ccp_showArrow` | arrow দেখাবে (dialog open indicator) |
| `ccp_autoDetectSim` | SIM অনুযায়ী দেশ auto-detect |
| `ccp_defaultNameCode` | default দেশ (BD/US/IN) |
| `ccp_textSize` | টেক্সট সাইজ |
| `ccp_textColor` | টেক্সট কালার |
| `ccp_isBengali` | বাংলা নাম/ডাটা (যদি ডাটা সাপোর্ট করে) |

> নোট: `ccp_autoDetectSim="true"` থাকলে SIM-এর দেশ default কে override করতে পারে।

---

## Register EditText (Required for Validation)

Phone validation কাজ করতে হলে এইটা **অবশ্যই** দিন:

```java
CountryCodePicker ccp = findViewById(R.id.ccp);
EditText etPhone = findViewById(R.id.phoneEt);

ccp.registerCarrierNumberEditText(etPhone);
```

---

## Validate Phone Number

```java
if (ccp.isValidFullNumber()) {
    String fullNumber = ccp.getFullNumberWithPlus(); // e.g. +88017XXXXXXXX
    Toast.makeText(this, "Valid: " + fullNumber, Toast.LENGTH_SHORT).show();
} else {
    etPhone.setError("Invalid Phone Number");
}
```

---

## Show Dial Code in a TextView (Separate CodeView)

আপনার যদি আলাদা `TextView` থাকে:

```java
TextView codeView = findViewById(R.id.countryCodeView);
codeView.setText(ccp.getSelectedCountryCodeWithPlus()); // e.g. +880
```

---

## Listen Country Change (If Supported in Your Updated Library)

যদি আপনার আপডেটেড লাইব্রেরিতে listener যোগ করা থাকে, তাহলে:

### Runnable style
```java
ccp.setOnCountryChangeListener(() -> {
    // update code view / validation
    codeView.setText(ccp.getSelectedCountryCodeWithPlus());
});
```

### Country object style (যদি থাকে)
```java
ccp.setOnCountryChangeListener(country -> {
    codeView.setText(ccp.getSelectedCountryCodeWithPlus());
});
```

---

## Programmatic Country Set

### Set by NameCode (BD/US/IN)
```java
ccp.setCountryForNameCode("BD"); // Bangladesh
```

### Set by Dial Code (+880 / 880)
```java
ccp.setCountryForDialCode("+880");
```

> নোট: Dial code mapping কাজ করতে হলে লাইব্রেরির country data-তে phoneCode থাকতে হবে।

---

## Lock Country (Disable country change)

কখনও কখনও আপনি চাইবেন country fixed থাকবে (যেমন preference থেকে country সেট করা)।

### Best Practice: Overlay Blocker (100% works)

#### XML (CCP এর উপর blocker)
```xml
<View
    android:id="@+id/ccpBlocker"
    android:layout_width="wrap_content"
    android:layout_height="match_parent"
    android:layout_alignStart="@id/ccp"
    android:layout_alignEnd="@id/ccp"
    android:layout_alignTop="@id/ccp"
    android:layout_alignBottom="@id/ccp"
    android:clickable="true"
    android:focusable="true"
    android:visibility="gone"/>
```

#### Java
```java
View blocker = findViewById(R.id.ccpBlocker);

if (!preferenceCountryCode.isEmpty()) {
    ccp.setCountryForNameCode(preferenceCountryCode);
    blocker.setVisibility(View.VISIBLE); // block dialog open
} else {
    blocker.setVisibility(View.GONE);    // allow
}
```

> শুধু `ccp.setEnabled(false)` দিলে UI/টাচ আচরণ অনেক সময় খারাপ দেখায়। Blocker সবচেয়ে safe।

---

## Common Problems & Fix

### 1) Country change হলে codeView আপডেট হয় না
✅ সমাধান:
- listener ব্যবহার করুন (`setOnCountryChangeListener`)  
- অথবা overlay/arrow click এর পর `updateCountryCodeText()` কল করুন

### 2) Validation change হচ্ছে কিন্তু UI code change হচ্ছে না
✅ কারণ: আপনি `getFullNumberWithPlus()` দিয়ে codeView সেট করছেন  
✅ সমাধান: `getSelectedCountryCodeWithPlus()` ব্যবহার করুন

### 3) SIM auto detect আপনার default BD override করছে
✅ সমাধান: XML এ
```xml
app:ccp_autoDetectSim="false"
app:ccp_defaultNameCode="BD"
```

---

## Example: Login Screen (Recommended)

```java
ccp = findViewById(R.id.ccp);
phoneEt = findViewById(R.id.phoneEt);
ccp.registerCarrierNumberEditText(phoneEt);

loginBtn.setOnClickListener(v -> {
    String number = phoneEt.getText().toString().trim();

    if (number.isEmpty()) {
        phoneEt.setError("Phone number is required");
        return;
    }

    if (!ccp.isValidFullNumber()) {
        phoneEt.setError("Invalid Phone Number");
        return;
    }

    String fullNumber = ccp.getFullNumberWithPlus();
    // call API with fullNumber
});
```

---

## License
Private/Internal use (আপনার প্রোজেক্ট অনুযায়ী লিখুন)।
