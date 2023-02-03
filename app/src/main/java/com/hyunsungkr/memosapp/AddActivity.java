package com.hyunsungkr.memosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyunsungkr.memosapp.data.DatabaseHandler;
import com.hyunsungkr.memosapp.model.Memo;

public class AddActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSave);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=editTitle.getText().toString().trim();
                String content = editContent.getText().toString().trim();
                // 제목과 내용 모두 있어야한다.!
                if(title.isEmpty()||content.isEmpty()){
                    Toast.makeText(AddActivity.this,"필수항목입니다. 입력해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                // 묶어서 처리할 contact 객체를 하나 만든다.
                Memo memo = new Memo(title,content);

                // DB에 저장한다.
                DatabaseHandler db = new DatabaseHandler(AddActivity.this);
                db.addMemo(memo);


                // 액티비티를 종료한다.
                finish();

            }
        });

    }
}