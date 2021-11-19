package com.example.storageapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileStorageActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edit_write,edit_read;
    private Button btn_wInner,btn_wOuter,btn_rInner,btn_rOuter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filestorage);

        edit_write = findViewById(R.id.edit_write);
        btn_wInner = findViewById(R.id.save_inner);
        btn_wInner.setOnClickListener(this);
        btn_wOuter = findViewById(R.id.save_outer);
        btn_wOuter.setOnClickListener(this);

        edit_read = findViewById(R.id.edit_read);
        btn_rInner = findViewById(R.id.load_inner);
        btn_rInner.setOnClickListener(this);
        btn_rOuter = findViewById(R.id.load_outer);
        btn_rOuter.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save_inner:
                String data_w_i = edit_write.getText().toString();
                if(TextUtils.isEmpty(data_w_i)){
                    Toast.makeText(FileStorageActivity.this,"请输入需要下入文件的内容！！",Toast.LENGTH_SHORT).show();
                    return;
                }
                saveInnerFile(data_w_i);
                Toast.makeText(FileStorageActivity.this,"数据已保存至内部存储中的文件",Toast.LENGTH_SHORT).show();
                break;
            case R.id.load_inner:
                String data_r_i = readInnerFile("lzl.txt");
                if(!TextUtils.isEmpty(data_r_i)){
                    edit_read.setText(data_r_i);
                }
                break;
            default:
                break;
        }
    }

    /*将数据存储至文件--内部存储（文件默认位于data/data/<packagename>目录下*/
    private void saveInnerFile(String data) {
        FileOutputStream fos = null;
        try{
            fos = openFileOutput("lzl.txt", Context.MODE_PRIVATE);
            fos.write(data.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if (fos != null){
                    fos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //读取内部存储中的文件数据
    private String readInnerFile(String filename) {
        String content = "";
        FileInputStream fis = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fis = openFileInput(filename);
            InputStreamReader insReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(insReader);
            String line = reader.readLine();
            while(line != null){
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
            if (fis != null){
                fis.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            content = stringBuilder.toString();
        }
        return  content;
    }

}
