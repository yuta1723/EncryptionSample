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
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getString(EncryptionUtil.PREFERENCE_ID, null) == null) {
            Log.d(TAG, "Encrypt key is null. Generate EncryptKey");
            //乱数を生成
            byte[] salt = EncryptionUtil.generateKey();
            sp.edit().putString(EncryptionUtil.PREFERENCE_ID, salt.toString()).commit();
        }

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button EncButton = (Button) findViewById(R.id.encryptButton);
        final TextView textEncView = (TextView) findViewById(R.id.textEnc);
        final TextView textDecView = (TextView) findViewById(R.id.textDec);
        EncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Encryption
                String EncText = editText.getText().toString();
                String EncedText = EncryptionUtil.encryptAES(mContext, EncText);
                textEncView.setText(EncedText);

                //Decryption
                String DecText = textEncView.getText().toString();
                String DecedText = EncryptionUtil.decryptAES(mContext, DecText);
                textDecView.setText(DecedText);
            }
        });

    }
}


//        try {
//            KeyGenerator generator = KeyGenerator.getInstance("AES");
//            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//            generator.init(ENCRYPT_KEY_LENGTH, random);
//            return generator.generateKey();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

