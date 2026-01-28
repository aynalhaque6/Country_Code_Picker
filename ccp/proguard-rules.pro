# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Android Default Rules
-dontwarn android.support.**
-keep class android.support.v7.widget.** { *; }
-keep class android.support.v4.** { *; }

# Custom View
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# LibPhoneNumber Keep Rules
-keep class com.google.i18n.phonenumbers.** { *; }

# Keep your library's public facing classes
-keep public class com.aynal.ccp.CountryCodePicker { *; }
-keep public class com.aynal.ccp.PhoneValidator { *; }