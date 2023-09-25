package com.zybooks.databaselogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText MicroEditText, NameEditText, EmailEditText, AccessCodeEditText, ConfirmCodeEditText;
    private RadioGroup GenderRadio;
    private RadioButton GenderRadioButton;
    //private RadioButton femaleRadio;
    private Spinner BreedSpinner;
    private CheckBox NeuteredCheckBox;
    private TextView MicroTextView, NameTextView, EmailTextView, AccessCodeTextView, ConfirmCodeTextView;
    private Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MicroTextView = findViewById(R.id.micro_label);
        MicroEditText = findViewById(R.id.micro_textEdit);
        NameTextView = findViewById(R.id.name_label);
        NameEditText = findViewById(R.id.name_textEdit);
        GenderRadio = findViewById(R.id.gender_radioGroup);
        //maleRadio = findViewById(R.id.male_radioButton);
        //femaleRadio = findViewById(R.id.female_radioButton);
        EmailTextView = findViewById(R.id.email_label);
        EmailEditText = findViewById(R.id.email_textEdit);
        AccessCodeTextView = findViewById(R.id.accessCode_label);
        AccessCodeEditText = findViewById(R.id.accessCode_textEdit);
        ConfirmCodeTextView = findViewById(R.id.confirmCode_label);
        ConfirmCodeEditText = findViewById(R.id.confirmCode_textEdit);
        BreedSpinner = findViewById(R.id.breed_spinner);
        NeuteredCheckBox = findViewById(R.id.neutered_checkbox);
        db = new Database(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.breed_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BreedSpinner.setAdapter(adapter);
    }

    public void ResetClick(View view){
        MicroEditText.getText().clear();
        NameEditText.getText().clear();
        GenderRadio.check(R.id.female_radioButton);
        EmailEditText.getText().clear();
        AccessCodeEditText.getText().clear();
        ConfirmCodeEditText.getText().clear();
        BreedSpinner.setSelection(0);
        NeuteredCheckBox.setChecked(true);
    }
    public void SubmitClick(View view){

        String microChip = MicroEditText.getText().toString();
        String name = NameEditText.getText().toString();

        int GenderID = GenderRadio.getCheckedRadioButtonId();
        GenderRadioButton = (RadioButton) findViewById(GenderID);
        String Gender = GenderRadioButton.getText().toString();

        String Neutered;
        if (NeuteredCheckBox.isChecked()){
            Neutered = "Neutered";
        }
        else Neutered = "Not neutered";

        String Email = EmailEditText.getText().toString();
        String AccessCode = AccessCodeEditText.getText().toString();
        String ConfirmCode = ConfirmCodeEditText.getText().toString();
        String breed = BreedSpinner.getSelectedItem().toString();

        if (microChip.equals("") || name.equals("") || Email.equals("") || AccessCode.equals("") ||
            ConfirmCode.equals(""))
        {
            Toast.makeText(this, "Make sure to complete all fields.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            // if length is out of range
            if(microChip.length() < 5 || microChip.length() > 15){
                Toast.makeText(this, "Microchip ID must be 5 - 15 characters long",
                        Toast.LENGTH_SHORT).show();
            }
            // microchipID has special characters
            else if (Check.MicroCharacters(microChip)){
                Toast.makeText(this, "Microchip ID cannot have non alphanumeric " +
                        "characters (e.g., ‘%’, ‘&’)", Toast.LENGTH_SHORT).show();
            }
            // ID not capitalized
            else if(!microChip.toUpperCase().equals(microChip)){
                Toast.makeText(this, "Capitalize every letter in ID",
                        Toast.LENGTH_SHORT).show();
            }
            // check if ID is in database -- DO NOT ALLOW DUPLICATES
            else if(db.checkMicroID(microChip)){
                Toast.makeText(this, "This ID already exists in database.", Toast.LENGTH_SHORT).show();
            }
            // first letter of every word is not capitalized
            else if(!Check.isAllUpper(name)){
                Toast.makeText(this, "Capitalize the first letter of every word in Name.",
                        Toast.LENGTH_SHORT).show();
            }
            // wrong email format
            else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                Toast.makeText(this, "Email format is incorrect.",
                        Toast.LENGTH_SHORT).show();
            }
            // prefix too short
            else if (!Check.isPrefixCorrect(Email)){
                Toast.makeText(this, "Email prefix must be more than 3 characters.",
                        Toast.LENGTH_SHORT).show();
            }
            // improper domain
            else if(!Check.isProperDomain(Email)){
                Toast.makeText(this, "Domain used for email is invalid.",
                        Toast.LENGTH_SHORT).show();
            }
            // confirm Code doesn't match access Code
            else if(!ConfirmCode.equals(AccessCode)){
                Toast.makeText(this, "Access field and Confirm field do not much.",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                db.insertData(microChip, name, Gender, Email, AccessCode, ConfirmCode, breed, Neutered);
                Toast.makeText(this, "Registered successfully.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}