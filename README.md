# Country Code Picker (com.aynal.ccp)

A lightweight Android Country Code Picker with:

- Flags
- Country name / ISO name code (BD, US, IN...)
- Dial code (+880, +1...)
- Optional SIM auto-detect
- Phone number validation (via the library's `PhoneValidator`)

## Installation

If you are using this as a **module**:

1. Copy the `ccp` folder into your project.
2. In `settings.gradle`:

```gradle
include ':ccp'
```

3. In your app `build.gradle`:

```gradle
implementation project(':ccp')
```

Sync Gradle.

## XML Usage

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

## Java / Kotlin Usage

### Register an EditText

```java
CountryCodePicker ccp = findViewById(R.id.ccp);
EditText etPhone = findViewById(R.id.numberEt);

ccp.registerCarrierNumberEditText(etPhone);
```

### Get Selected Country

```java
String name = ccp.getSelectedCountryName();       // e.g., Bangladesh
String iso  = ccp.getSelectedCountryNameCode();   // e.g., BD
String dial = ccp.getSelectedCountryCodeWithPlus(); // e.g., +880
```

### Validate Phone

```java
if (ccp.isValidFullNumber()) {
    String full = ccp.getFullNumberWithPlus();
}
```

## New: Country Change Listener

This library version adds a callback when the user selects a new country.

### Option A: Simple Runnable

```java
ccp.setOnCountryChangeListener(() -> {
    // update UI
});
```

### Option B: Listener with Country object

```java
ccp.setOnCountryChangeListener(country -> {
    // country is com.aynal.ccp.Country
});
```

> The callback is fired when the selected country changes via the picker dialog
> or programmatic set methods.

## New: Programmatic Country Set

### By ISO name code (BD, US, IN)

```java
ccp.setCountryForNameCode("BD");
```

### By dial code (+880 / 880)

```java
ccp.setCountryForDialCode("+880");
// or
ccp.setCountryForDialCode("880");
```

## Optional: Online JSON URL

If you have a countries JSON file hosted online:

```java
ccp.setServerUrl("https://your-domain.com/countries.json");
```

If `setServerUrl(null)` or not used, the library uses offline data.
