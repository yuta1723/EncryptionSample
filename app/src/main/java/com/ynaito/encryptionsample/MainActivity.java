package com.ynaito.encryptionsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private String AES_KEY = "hogehoge";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button EncButton = (Button) findViewById(R.id.encryptButton);
        final TextView textEncView = (TextView) findViewById(R.id.textEnc);
        final TextView textDecView = (TextView) findViewById(R.id.textDec);
        EncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Encryption
                String EncText = editText.getText().toString();
                String EncedText = EncryptionUtil.encryptAES(AES_KEY,EncText);
                textEncView.setText(EncedText);

                //Decryption
                String DecText = textEncView.getText().toString();
                String DecedText = EncryptionUtil.decryptAES(AES_KEY,DecText);
                textDecView.setText(DecedText);
            }
        });

    }
}
