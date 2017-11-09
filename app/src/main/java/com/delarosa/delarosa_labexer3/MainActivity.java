package com.delarosa.delarosa_labexer3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText etData, etFilename;
    FileOutputStream fos;
    SharedPreferences preferences;
    Button btnSharedPrefs, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etData = (EditText) findViewById(R.id.etData);
        etFilename = (EditText) findViewById(R.id.etFilename);
        btnSharedPrefs = (Button) findViewById(R.id.btnViewSharedPrefs);
        btnNext = (Button) findViewById(R.id.btnNext);
    }

    public void saveSharedPrefs(View view){

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String data = etData.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("data",data);
        editor.commit();

        Toast.makeText(this, "Saved in Shared Preferences!", Toast.LENGTH_LONG).show();
    }

    public void saveIntStorage(View view){
        String data = etData.getText().toString();
        String filename = etFilename.getText().toString();

        try{
            fos = openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Toast.makeText(this, "Saved in Internal Storage!", Toast.LENGTH_LONG).show();
    }

    public void saveIntCache(View view){
        File folder = getCacheDir();
        String filename = etFilename.getText().toString();
        File file = new File(folder, filename);

        String data = etData.getText().toString();

        saveData(file, data);

        Toast.makeText(this, "Message saved to Internal Cache!", Toast.LENGTH_LONG).show();
    }

    public void saveExtCache(View view){
        File folder = getExternalCacheDir();
        String filename = etFilename.getText().toString();
        File file = new File(folder, filename);

        String data = etData.getText().toString();

        saveData(file, data);

        Toast.makeText(this, "Message saved to External Cache!", Toast.LENGTH_LONG).show();
    }

    public void saveExtStorage(View view) throws FileNotFoundException {
        File folder = getExternalFilesDir("temp");
        String filename = etFilename.getText().toString();
        File file = new File(folder, filename);

        String data = etData.getText().toString();

        saveData(file, data);

        Toast.makeText(this, "Message saved to External Storage!", Toast.LENGTH_LONG).show();
    }

    public void saveExtPublic(View view){
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String filename = etFilename.getText().toString();
        File file = new File(folder, filename);

        String data = etData.getText().toString();

        saveData(file, data);

        Toast.makeText(this, "Message saved to Downloads folder!", Toast.LENGTH_LONG).show();
    }

    public void saveData(File file, String data){
        FileOutputStream fos = null;

        try{
            fos = new FileOutputStream(file);
            fos.write(data.getBytes());
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void goToDisplay(View view){
        Intent intent = new Intent(this,DisplayActivity.class);
        startActivity(intent);
    }
}
