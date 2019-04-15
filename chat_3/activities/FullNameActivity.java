package com.example.ahmad.chat_3.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmad.chat_3.R;

public class FullNameActivity extends BaseActivity  {

    private EditText firstname, familyName;
    private Button nextBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_name);

        firstname = findViewById(R.id.etName);
        familyName = findViewById(R.id.etFamilyName);
        nextBt = findViewById(R.id.nextBT2);


        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // todo confusing variable naming

                String nameEt = firstname.getText().toString().trim();
                String lastNameEt = familyName.getText().toString().trim();

                if ((nameEt.matches("") && lastNameEt.matches(""))){
                    Toast.makeText(FullNameActivity.this,"Name and family name should be a-Z and not empty.", Toast.LENGTH_LONG).show();
                    return;
                }
                if ((nameEt.matches("") && !lastNameEt.matches(""))){
                    Toast.makeText(FullNameActivity.this,"Name should be a-Z and not empty.", Toast.LENGTH_LONG).show();
                    return;
                }
                if ((!nameEt.matches("") && lastNameEt.matches(""))){
                    Toast.makeText(FullNameActivity.this,"Family name should be a-Z and not empty.", Toast.LENGTH_LONG).show();
                    return;
                }
//                if ((name1.matches("") || !name1.matches("[a-zA-Z]+")) ||
//                        familyName1.matches("") || !familyName1.matches("[a-zA-Z]+")) {
//
//                    Toast.makeText(FullNameActivity.this,"Name and family name should be a-Z and not empty.", Toast.LENGTH_LONG).show();
//                    return;
//                }
                else {

                    Intent intent = new Intent(FullNameActivity.this, CountryActivity.class);
                    Bundle bundle = getIntent().getExtras();
                    if (bundle == null) {
                        bundle = new Bundle();
                    }
                    bundle.putString("first_name", nameEt);
                    bundle.putString("last_name", lastNameEt);
                    bundle.putString("full_name",nameEt+" "+ lastNameEt);
                    SharedPreferences pref=getApplicationContext().getSharedPreferences("prefs",0);
                    SharedPreferences.Editor editor= pref.edit();
                    editor.putString("first_name",nameEt);
                    editor.putString("last_name",lastNameEt);
                    editor.putString("full_name",nameEt+" "+ lastNameEt);
                    editor.apply();
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }


}
