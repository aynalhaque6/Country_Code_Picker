-keepclassmembers class com.aynal.ccp.CountryCodePicker {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class com.google.i18n.phonenumbers.** { *; }

-keep class com.aynal.ccp.Country { *; }

-keepclassmembers class **.R$drawable {
    public static final int flag_*;
}