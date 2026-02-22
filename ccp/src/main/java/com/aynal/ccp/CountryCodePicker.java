package com.aynal.ccp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import java.util.List;

public class CountryCodePicker extends LinearLayout {

    private LinearLayout holder;
    private CardView cardFlag;
    private ImageView ivFlag, ivArrow;
    private TextView tvFullName, tvNameCode, tvPhoneCode;
    
    // Config
    private boolean showFlag = true;
    private boolean showFullName = false;
    private boolean showNameCode = false;
    private boolean showPhoneCode = true;
    private boolean showArrow = true;
    private boolean autoDetectSim = true;
    private boolean isBengali = false;
    private String defaultNameCode = "BD";
    
    private String customServerUrl = null;

    private Country selectedCountry;
    private List<Country> allCountries;
    private EditText registeredEditText;

    // ===== Country change callbacks =====
    public interface OnCountryChangeListener {
        void onCountrySelected(Country country);
    }

    private OnCountryChangeListener onCountryChangeListener;
    private Runnable onCountryChangeRunnable;

    // If setCountry... is called before countries are loaded
    private String pendingIsoNameCode = null;
    private String pendingDialCode = null;

    public CountryCodePicker(Context context) {
        super(context);
        init(context, null);
    }

    public CountryCodePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_ccp, this, true);
        
        holder = view.findViewById(R.id.holder);
        cardFlag = view.findViewById(R.id.cardFlag);
        ivFlag = view.findViewById(R.id.ivFlag);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvNameCode = view.findViewById(R.id.tvNameCode);
        tvPhoneCode = view.findViewById(R.id.tvPhoneCode);
        ivArrow = view.findViewById(R.id.ivArrow);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CountryCodePicker, 0, 0);
            try {
                showFlag = a.getBoolean(R.styleable.CountryCodePicker_ccp_showFlag, true);
                showFullName = a.getBoolean(R.styleable.CountryCodePicker_ccp_showFullName, false);
                showNameCode = a.getBoolean(R.styleable.CountryCodePicker_ccp_showNameCode, false);
                showPhoneCode = a.getBoolean(R.styleable.CountryCodePicker_ccp_showPhoneCode, true);
                showArrow = a.getBoolean(R.styleable.CountryCodePicker_ccp_showArrow, true);
                autoDetectSim = a.getBoolean(R.styleable.CountryCodePicker_ccp_autoDetectSim, true);
                isBengali = a.getBoolean(R.styleable.CountryCodePicker_ccp_isBengali, false);
                
                String defCode = a.getString(R.styleable.CountryCodePicker_ccp_defaultNameCode);
                if (defCode != null) defaultNameCode = defCode;
                float defaultSize = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP,
                        14,
                        getResources().getDisplayMetrics()
                );

                float textSize = a.getDimension(R.styleable.CountryCodePicker_ccp_textSize, defaultSize);

                if (textSize > 0) {
                    tvFullName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                    tvNameCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                    tvPhoneCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }


                int textColor = a.getColor(R.styleable.CountryCodePicker_ccp_textColor, Color.BLACK);
                tvFullName.setTextColor(textColor);
                tvNameCode.setTextColor(textColor);
                tvPhoneCode.setTextColor(textColor);
            } finally {
                a.recycle();
            }
        }

        holder.setOnClickListener(v -> openPickerDialog());
        loadData();
    }
    public void setServerUrl(String url) {
        this.customServerUrl = url;
        loadData();
    }

    private void loadData() {
        SmartCountryLoader.loadCountries(getContext(), customServerUrl, isBengali, countries -> {
            this.allCountries = countries;
            if (selectedCountry == null) {
                // 1) If caller requested a country programmatically, prefer that
                if (pendingIsoNameCode != null) {
                    setCountryByIso(pendingIsoNameCode);
                    pendingIsoNameCode = null;
                    pendingDialCode = null;
                    return;
                }
                if (pendingDialCode != null) {
                    setCountryByDialCodeInternal(pendingDialCode);
                    pendingDialCode = null;
                    return;
                }

                // 2) Otherwise default behaviour
                if (autoDetectSim) detectSim();
                else setCountryByIso(defaultNameCode);
            }
        });
    }

    private void detectSim() {
        try {
            TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
            String iso = tm.getSimCountryIso();
            setCountryByIso((iso != null && !iso.isEmpty()) ? iso : defaultNameCode);
        } catch (Exception e) {
            setCountryByIso(defaultNameCode);
        }
    }

    private void setCountryByIso(String iso) {
        if (allCountries == null) return;
        for (Country c : allCountries) {
            if (c.getIso().equalsIgnoreCase(iso)) {
                setSelectedCountry(c);
                return;
            }
        }
        if (!iso.equalsIgnoreCase("BD")) setCountryByIso("BD");
    }

    private void setSelectedCountry(Country country) {
        this.selectedCountry = country;

        ivFlag.setImageResource(country.getFlagRes());
        tvFullName.setText(country.getName());
        tvNameCode.setText(country.getIso().toUpperCase());
        tvPhoneCode.setText(country.getDialCode());

        cardFlag.setVisibility(showFlag ? VISIBLE : GONE);
        tvFullName.setVisibility(showFullName ? VISIBLE : GONE);
        tvNameCode.setVisibility(showNameCode ? VISIBLE : GONE);
        tvPhoneCode.setVisibility(showPhoneCode ? VISIBLE : GONE);
        ivArrow.setVisibility(showArrow ? VISIBLE : GONE);

        if (registeredEditText != null) {
            registeredEditText.setHint(country.getHint());
        }

        // Notify listeners
        notifyCountryChanged();
    }

    private void openPickerDialog() {
        if (allCountries == null) return;
        CountryPicker picker = new CountryPicker(getContext(), isBengali, customServerUrl);
        picker.show(country -> setSelectedCountry(country));
    }

    public void registerCarrierNumberEditText(EditText editText) {
        this.registeredEditText = editText;
        if (selectedCountry != null) editText.setHint(selectedCountry.getHint());
    }

    // ===== Public APIs: programmatic country set =====

    /**
     * Set country by ISO 2-letter name code (e.g., "BD", "US", "IN").
     */
    public void setCountryForNameCode(String nameCode) {
        if (nameCode == null) return;
        String iso = nameCode.trim();
        if (iso.isEmpty()) return;

        if (allCountries == null) {
            pendingIsoNameCode = iso;
            pendingDialCode = null;
            loadData();
            return;
        }
        setCountryByIso(iso);
    }

    /**
     * Set country by phone dial code (e.g., "+880" or "880").
     */
    public void setCountryForDialCode(String dialCode) {
        if (dialCode == null) return;
        String code = dialCode.trim();
        if (code.startsWith("+")) code = code.substring(1);
        if (code.isEmpty()) return;

        if (allCountries == null) {
            pendingDialCode = code;
            pendingIsoNameCode = null;
            loadData();
            return;
        }
        setCountryByDialCodeInternal(code);
    }

    private void setCountryByDialCodeInternal(String dialCodeNoPlus) {
        if (allCountries == null) return;
        for (Country c : allCountries) {
            String cDial = c.getDialCode();
            String cClean = cDial == null ? "" : cDial.replace("+", "").trim();
            if (dialCodeNoPlus.equals(cClean)) {
                setSelectedCountry(c);
                return;
            }
        }
    }

    // ===== Listener APIs =====

    /**
     * Listener with selected country object.
     */
    public void setOnCountryChangeListener(OnCountryChangeListener listener) {
        this.onCountryChangeListener = listener;
    }

    /**
     * No-arg listener for simple callbacks.
     */
    public void setOnCountryChangeListener(Runnable listener) {
        this.onCountryChangeRunnable = listener;
    }

    private void notifyCountryChanged() {
        try {
            if (onCountryChangeListener != null && selectedCountry != null) {
                onCountryChangeListener.onCountrySelected(selectedCountry);
            }
        } catch (Exception ignored) {
        }

        try {
            if (onCountryChangeRunnable != null) {
                onCountryChangeRunnable.run();
            }
        } catch (Exception ignored) {
        }
    }

    public boolean isValidFullNumber() {
        if (selectedCountry == null || registeredEditText == null) return false;
        return PhoneValidator.isValid(registeredEditText.getText().toString(), selectedCountry.getIso());
    }

    public String getFullNumberWithPlus() {
        if (selectedCountry == null || registeredEditText == null) return "";
        String number = registeredEditText.getText().toString();
        String formatted = PhoneValidator.getFormattedNumber(number, selectedCountry.getIso());
        return formatted != null ? formatted : selectedCountry.getDialCode() + number;
    }

    public String getSelectedCountryName() {
        if (selectedCountry != null) {
            return selectedCountry.getName();
        }
        return "";
    }

    public String getSelectedCountryNameCode() {
        if (selectedCountry != null) {
            return selectedCountry.getIso().toUpperCase();
        }
        return "";
    }

    public String getSelectedCountryCodeWithPlus() {
        if (selectedCountry != null) {
            return selectedCountry.getDialCode();
        }
        return "";
    }

}