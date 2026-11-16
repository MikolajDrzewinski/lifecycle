package matos.csu.lifecycle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final int duration = Toast.LENGTH_SHORT;

    private EditText txtColorSelected;
    private TextView txtSpyBox;
    private LinearLayout myScreen;
    private static final String PREFNAME = "myPrefFile1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtColorSelected = findViewById(R.id.editText1);
        Button btnExit = findViewById(R.id.button1);
        txtSpyBox = findViewById(R.id.textView1);
        myScreen = findViewById(R.id.myScreen1);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtColorSelected.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                String chosenColor = s.toString().toLowerCase(Locale.US);
                txtSpyBox.setText(chosenColor);
                setBackgroundColor(chosenColor, myScreen);
            }
        });

        Toast.makeText(this, "onCreate", duration).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateMeUsingSavedStateData();
        Toast.makeText(this, "onStart", duration).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", duration).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String chosenColor = txtSpyBox.getText().toString();
        outState.putString("savedColorKey", chosenColor);
        Toast.makeText(this, "onSaveInstanceState", duration).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        String chosenColor = txtSpyBox.getText().toString();
        saveStateData(chosenColor);
        Toast.makeText(this, "onPause", duration).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", duration).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", duration).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart", duration).show();
    }

    private void setBackgroundColor(String chosenColor, LinearLayout myScreen) {
        if (chosenColor == null) return;
        if (chosenColor.contains("red")) {
            myScreen.setBackgroundColor(0xffff0000);
        } else if (chosenColor.contains("green")) {
            myScreen.setBackgroundColor(0xff00ff00);
        } else if (chosenColor.contains("blue")) {
            myScreen.setBackgroundColor(0xff0000ff);
        } else if (chosenColor.contains("white")) {
            myScreen.setBackgroundColor(0xffffffff);
        }
    }

    private void saveStateData(String chosenColor) {
        SharedPreferences myPrefContainer = getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor myPrefEditor = myPrefContainer.edit();
        myPrefEditor.putString("chosenBackgroundColor", chosenColor);
        myPrefEditor.apply();
    }

    private void updateMeUsingSavedStateData() {
        SharedPreferences myPrefContainer = getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        String key = "chosenBackgroundColor";
        String defaultValue = "white";

        if (myPrefContainer != null && myPrefContainer.contains(key)) {
            String color = myPrefContainer.getString(key, defaultValue);
            setBackgroundColor(color, myScreen);
        }
    }
}
