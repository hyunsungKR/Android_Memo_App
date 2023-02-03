package com.hyunsungkr.memosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hyunsungkr.memosapp.adapter.MemoAdapter;
import com.hyunsungkr.memosapp.data.DatabaseHandler;
import com.hyunsungkr.memosapp.model.Memo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editSearch;
    ImageView imgSearch;
    ImageView imgDelete;
    Button btnAdd;

    // RecyclerView를 사용할 때
    // RecyclerView,Adapter,ArrayList를 쌍으로 적어라.
    RecyclerView recyclerView;
    MemoAdapter adapter;
    ArrayList<Memo> memoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSearch = findViewById(R.id.editSearch);
        imgSearch = findViewById(R.id.imgSearch);
        imgDelete = findViewById(R.id.imgDelete);
        btnAdd = findViewById(R.id.btnAdd);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AddActivity 실행
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);

            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                // 유저가 입력한 키워드를 뽑는다.
                String keyword = editSearch.getText().toString().trim();

                // 2글자 이상 입력했을 때만 검색이 되도록 한다.
                if(keyword.length() < 2 && keyword.length() > 0){
                    return;
                }

                // 디비에서 가져온다.
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                memoList=db.searchMemo(keyword);

                // 화면에 보여준다.
                adapter = new MemoAdapter(MainActivity.this,memoList);
                recyclerView.setAdapter(adapter);


            }
        });

        // 메모 검색 ( 버튼 클릭 시 )
        imgSearch.setOnClickListener(view -> {
            // EditText에서 검색어 저장
            String keyword = editSearch.getText().toString().trim();

            if(keyword.isEmpty()){
                editSearch.setText("");
                return;
            }
            // keyword로 DB의 데이터 검색
            DatabaseHandler db = new DatabaseHandler(MainActivity.this);
            memoList = db.searchMemo(keyword);

            // 검색 결과 출력
            adapter = new MemoAdapter(MainActivity.this, memoList);
            recyclerView.setAdapter(adapter);
        });


        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 저장된 모든 메모를 가져온다.
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                memoList = db.getAllMemos();
                adapter = new MemoAdapter(MainActivity.this,memoList);
                recyclerView.setAdapter(adapter);
                // 기존에 입력된 검색어도 삭제.
                editSearch.setText("");
            }
        });

        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        memoList = db.getAllMemos();

        adapter = new MemoAdapter(MainActivity.this,memoList);
        recyclerView.setAdapter(adapter);







    }


}