package com.hyunsungkr.memosapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.hyunsungkr.memosapp.model.Memo;
import com.hyunsungkr.memosapp.util.Util;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DB_NAME,null,Util.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 테이블 생성
        String CREATE_MEMO_TABLE = "create table memo ( id integer primary key, title text, content text )";

        sqLiteDatabase.execSQL(CREATE_MEMO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // 기존의 테이블을 삭제하고 새 테이블을 다시 만든다.

        String DROP_TABLE="drop table memo";
//        sqLiteDatabase.execSQL(DROP_TABLE);

        sqLiteDatabase.execSQL(DROP_TABLE,new String[]{Util.DB_NAME});

        onCreate(sqLiteDatabase);



    }

    public void addMemo(Memo memo) {
// 1. 데이터 베이스를 가져온다.
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "insert into memo(title,content) values (?,?)";
        String[] args = new String[] {memo.title, memo.content };
        db.execSQL(query,args);
        db.close();
    }

    public ArrayList<Memo> getAllMemos() {
        // 1. 데이터베이스를 가져온다.
        SQLiteDatabase db = this.getReadableDatabase();
        // 2. 쿼리문을 만든다.
        String query = "select * from memo order by id desc";
        // 3. 쿼리문을 실행하여 커서로 받는다.
        Cursor cursor = db.rawQuery(query,null);
        // 3-1. 여러 데이터를 저장할 ArrayList를 만든다.

        ArrayList<Memo> memoArrayList = new ArrayList<>();
        // 4. 커서에서 데이터를 뽑아낸다.
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);

                Log.i("MEMO_TABLE",id +" ,"+title+", "+content);



                // 이 데이터를 화면에 표시하기 위해서는
                // 메모리에 전부 다 남아있어야한다!!!
                Memo memo = new Memo(id,title,content);
                memoArrayList.add(memo);


            } while(cursor.moveToNext());
        }

        // 5. db 닫기
        db.close();

        // 6. DB에서 읽어온 연락처 정보를 리턴해야한다.
        return memoArrayList;
    }

    public ArrayList<Memo> searchMemo(String keyword) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select * from memo where title like '%" + keyword + "%'" +"or content like '%" + keyword + "%'";
        Cursor cursor = db.rawQuery(query,null);

        ArrayList<Memo> memoList = new ArrayList<>();
        cursor.moveToFirst();

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);

                // 이 데이터를 화면에 표시하기 위해서는
                // 메모리에 전부 다 남아있어야한다!!!
                Memo memo = new Memo(id,title,content);
                memoList.add(memo);


            } while(cursor.moveToNext());
        }

        // 5. db 닫기
        db.close();

        // 6. DB에서 읽어온 연락처 정보를 리턴해야한다.
        return memoList;



    }
}

