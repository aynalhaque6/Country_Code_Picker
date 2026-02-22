# Country Code Picker (CCP) — com.aynal.ccp

This library is used to display **country flag + dial code (+880) + name/code** and to do **phone number validation** in Android apps.

---

## Features

- ✅ Select country from Country list dialog
- ✅ Flag show/hide
- ✅ Phone code show/hide (+880)
- ✅ Name/NameCode show/hide (BD/US)
- ✅ SIM-based auto detect (optional)
- ✅ Default country set (BD/US/IN)
- ✅ Phone number validation (libphonenumber based)
- ✅ Programmatic country set (NameCode / DialCode) *(if added in your updated version)*
- ✅ Country change listener *(if added in your updated version)*

---

## Installation (Module)

If your project has `ccp` as a separate module:

### `settings.gradle`
```gradle
include ':ccp'
```

### `app/build.gradle`
```gradle
dependencies {
implementation project(':ccp')
}
````

Sync.

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
````

---

## Supported XML Attributes

| Attribute | Work
|---|---|
| `ccp_showFlag` | Show/hide flag
| `ccp_showFullName` | Show full name of country |
| `ccp_showNameCode` | Show ISO code (BD/US/IN) |
| `ccp_showPhoneCode` | Show dial code (+880) |
| `ccp_showArrow` | Show arrow (dialog open indicator) |
| `ccp_autoDetectSim` | Auto-detect country according to SIM |
| `ccp_defaultNameCode` | Default country (BD/US/IN) |
| `ccp_textSize` | Text size |
| `ccp_textColor` | Text color |
| `ccp_isBengali` | Bengali name/data (if data supports) |

> Note: If `ccp_autoDetectSim="true"` is set, it can override the default country of SIM.

---

## Register EditText (Required for Validation)

**MUST** enter this for phone validation to work:

```java
CountryCodePicker ccp = findViewById(R.id.ccp);
EditText etPhone = findViewById(R.id.phoneEt);

ccp.registerCarrierNumberEditText(etPhone);
````

---

## Validate Phone Number

```java
if (ccp.isValidFullNumber()) { 
String fullNumber = ccp.getFullNumberWithPlus(); // e.g. +88017XXXXXXXXX 
Toast.makeText(this, "Valid: " + fullNumber, Toast.LENGTH_SHORT).show();
} else { 
etPhone.setError("Invalid Phone Number");
}
```

---

## Show Dial Code in a TextView (Separate CodeView)

If you have a separate `TextView`:

```java
TextView codeView = findViewById(R.id.countryCodeView);
codeView.setText(ccp.getSelectedCountryCodeWithPlus()); // e.g. +880
```

---

## Listen Country Change (If Supported in Your Updated Library)

If you have added a listener in your updated library, then:

### Runnable style
```java
ccp.setOnCountryChangeListener(() -> {
// update code view / validation
codeView.setText(ccp.getSelectedCountryCodeWithPlus());
});
```

### Country object style (if any)
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

> Note: For dial code mapping to work, the library's country data must contain the phoneCode.

---

## Lock Country (Disable country change)

Sometimes you may want the country to be fixed (e.g. setting the country from a preference).

### Best Practice: Overlay Blocker (100% works)

#### XML (blocker on CCP)
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
````

#### Java
```java
View blocker = findViewById(R.id.ccpBlocker);

if (!preferenceCountryCode.isEmpty()) { 
ccp.setCountryForNameCode(preferenceCountryCode); 
blocker.setVisibility(View.VISIBLE); // block dialog open
} else {
blocker.setVisibility(View.GONE); // allow
}
```

> Just `ccp.setEnabled(false)` will often cause the UI/touch behavior to look bad. Blocker is the safest.

---
## Common Problems & Fix

### 1) CodeView does not update when country changes
✅ Solution:
- Use listener (`setOnCountryChangeListener`)
- Or call `updateCountryCodeText()` after overlay/arrow click

### 2) Validation is changing but UI code is not changing
✅ Reason: You are setting codeView with `getFullNumberWithPlus()`
✅ Solution: Use `getSelectedCountryCodeWithPlus()`

### 3) SIM auto detect is overriding your default BD
✅ Solution: In XML
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
