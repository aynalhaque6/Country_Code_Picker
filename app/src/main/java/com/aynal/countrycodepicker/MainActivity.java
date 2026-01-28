package com.aynal.countrycodepicker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.aynal.ccp.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText etPhone;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ccp = findViewById(R.id.ccp);
        etPhone = findViewById(R.id.etPhone);
        btnSubmit = findViewById(R.id.btnSubmit);

//         ccp.setServerUrl("https://gist.githubusercontent.com/keeguon/2310008/raw/bdc2ce1c1e3f28f9cab5b4393c7549f38361be4e/countries.json");

        ccp.registerCarrierNumberEditText(etPhone);

        btnSubmit.setOnClickListener(v -> {

            if (ccp.isValidFullNumber()) {
                String fullNumber = ccp.getFullNumberWithPlus();

                Toast.makeText(this, "Valid Number: " + fullNumber, Toast.LENGTH_SHORT).show();
            } else {
                etPhone.setError("Invalid Phone Number");
                etPhone.requestFocus();
            }
        });
    }
}