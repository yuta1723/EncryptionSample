package com.ynaito.encryptionsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private byte[] mSalt;
    public final static String PREFERENCE_ID = "hogehoge";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getString(PREFERENCE_ID, null) == null) {
            Log.d(TAG, "Encrypt key is null. Generate EncryptKey");
            //乱数を生成
            byte[] salt = EncryptionUtil.generateKey();
            sp.edit().putString(PREFERENCE_ID, salt.toString()).commit();
            mSalt = salt;
        } else {
            mSalt = sp.getString(PREFERENCE_ID, null).getBytes();
        }

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button EncButton = (Button) findViewById(R.id.encryptButton);
        final TextView textEncView = (TextView) findViewById(R.id.textEnc);
        final TextView textDecView = (TextView) findViewById(R.id.textDec);
        EncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Encryption
                byte[] EncText = editText.getText().toString().getBytes();
                byte[] EncedText = EncryptionUtil.encryptAES(mSalt, EncText);
                Log.d(TAG,"encrypted : " + EncryptionUtil.byteToString(EncedText));
                textEncView.setText(EncryptionUtil.byteToString(EncedText));

                //Decryption
                byte[] DecText = EncryptionUtil.StringToByte(textEncView.getText().toString());
                byte[] DecedText = EncryptionUtil.decryptAES(mSalt, DecText);
                textDecView.setText(new String(DecedText));
            }
        });

    }
}

