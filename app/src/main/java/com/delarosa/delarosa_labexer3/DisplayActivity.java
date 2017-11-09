package com.delarosa.delarosa_labexer3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.delarosa.delarosa_labexer3.R.id.tvDisplay;

public class DisplayActivity extends AppCompatActivity {
    EditText etViewFilename;
    TextView etViewData;
    Button btnNext, btnSharedPrefs, btnIntStorage, btnIntCache, btnExtStorage, btnExtCache, btnExtPublic;
    FileInputStream fis;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        etViewData = (TextView) findViewById(R.id.etViewData);
        etViewFilename = (EditText) findViewById(R.id.etViewFilename);

        btnSharedPrefs = (Button) findViewById(R.id.btnViewSharedPrefs);
        btnIntStorage = (Button) findViewById(R.id.btnViewIntStorage);
        btnIntCache = (Button) findViewById(R.id.btnViewIntCache);
        btnExtStorage = (Button) findViewById(R.id.btnViewExtStorage);
        btnExtCache = (Button) findViewById(R.id.btnViewExtCache);
        btnExtPublic = (Button) findViewById(R.id.btnViewExtPublic);

        btnNext = (Button) findViewById(R.id.btnNext);
    }

    public void viewSharedPrefs(View view){
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String data = preferences.getString("data", "");

        etViewData.setText(data);
    }

    public void viewIntStorage(View view){
        String filename = etViewFilename.getText().toString();
        StringBuffer buffer = new StringBuffer();
        int read = 0;

        try{
            fis = openFileInput(filename);
            while((read = fis.read()) != -1){
                buffer.append((char)read);
            }

            fis.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        etViewData.setText(buffer.toString());
    }

    public void viewIntCache(View view){
        String filename = etViewFilename.getText().toString();
        File file = getCacheDir();

        viewData(file, filename);
    }

    public void viewExtCache(View view){
        String filename = etViewFilename.getText().toString();
        File file = getExternalCacheDir();

        viewData(file, filename);
    }

    public void viewExtStorage(View view){
        String filename = etViewFilename.getText().toString();
        File file= getExternalFilesDir("temp");

        viewData(file, filename);
    }

    public void viewExtPublic(View view){
        String filename = etViewFilename.getText().toString();
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        viewData(file, filename);
    }

    public void viewData(File file, String filename){
        StringBuffer buffer = new StringBuffer();
        int read = 0;
        try{
            FileInputStream fis = new FileInputStream(new File(file, filename));
            while((read = fis.read()) != -1){
                buffer.append((char) read);
            }
            etViewData.setText(buffer.toString());
            fis.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToMainActivity(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
