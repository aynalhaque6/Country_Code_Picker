-dontwarn android.support.**
-dontwarn androidx.**
-keep class android.support.v7.widget.** { *; }
-keep class android.support.v4.** { *; }
-keep class androidx.** { *; }

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class com.google.i18n.phonenumbers.** { *; }

-keep public class com.aynal.ccp.CountryCodePicker { *; }
-keep public class com.aynal.ccp.PhoneValidator { *; }

-keep class com.aynal.ccp.Country { *; }

-keepclassmembers class **.R$drawable {
    public static final int flag_*;
}