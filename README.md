```gradle




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


ccp.registerCarrierNumberEditText(etPhone);

if (ccp.isValidFullNumber()) {
}
});
