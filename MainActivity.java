package com.example.btchuyenactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle saveInstance_144){
        super.onCreate(saveInstance_144);
        setContentView(R.layout.login);
        Button btnLogin_144 = findViewById(R.id.btnLogin_144);
        EditText editTextText2_144 = findViewById(R.id.editTextText2);
        btnLogin_144.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v_144) {
                String userName_144 = editTextText2_144.getText().toString();
                Intent intent_144 = new Intent(MainActivity.this, ProfileActivity.class);
                intent_144.putExtra("username", userName_144);
                startActivity(intent_144);
            }
        });
    }
}
