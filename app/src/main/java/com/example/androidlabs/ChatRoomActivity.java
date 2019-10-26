package com.example.androidlabs;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.androidlabs.DatabaseHelper.TABLE_NAME;

public class ChatRoomActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private List<Messenger> messengerList;
    private MessageAdapter adapter;
    private ListView listView;
    private View btnSend;
    private View btnReceive;
    private EditText editText;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        myDb = new DatabaseHelper(this);
         db = myDb.getWritableDatabase();
        messengerList = new ArrayList<>();

        listView = findViewById((R.id.list_view));
        btnSend = findViewById(R.id.button5);
        btnReceive = findViewById(R.id.button6);
        editText = findViewById(R.id.editText);

        String [] columns = {DatabaseHelper.COL_MESSAGEID, DatabaseHelper.COL_ISSEND, DatabaseHelper.COL_MESSAGE};
        Cursor results = db.query(false, TABLE_NAME, columns, null, null, null, null, null, null);


        int messageColumnIndex = results.getColumnIndex(DatabaseHelper.COL_MESSAGE);
        int isSendColIndex = results.getColumnIndex(DatabaseHelper.COL_ISSEND);
        final int messageIDColIndex = results.getColumnIndex(DatabaseHelper.COL_MESSAGEID);

        printCursor(results);

        while(results.moveToNext()) {

            String message = results.getString(messageColumnIndex);

            Boolean isSend =Boolean.valueOf( results.getString(isSendColIndex));

            long messageID = results.getLong(messageIDColIndex);

            Log.i("Row: ", "ID="+messageID + " MESSAGE="+message + " IS_SEND=" + isSend);

            messengerList.add(new Messenger(message, isSend, messageID));
        }


        adapter = new MessageAdapter(this, messengerList);
        listView.setAdapter(adapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertMessage(true);

            }
        });

        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertMessage(false);


            }
        });


    }

   private void insertMessage(Boolean type){
       String message = editText.getText().toString();
       ContentValues newRowValues = new ContentValues();


       newRowValues.put(DatabaseHelper.COL_MESSAGE, message);


       newRowValues.put(DatabaseHelper.COL_ISSEND, String.valueOf(type));
       long messageID = db.insert(TABLE_NAME, null, newRowValues);
       Messenger Messenger = new Messenger(message, type, messageID);
       messengerList.add(Messenger);
       adapter.notifyDataSetChanged();
       editText.setText("");
   }

   private void printCursor(Cursor c) {
       Log.i("Database Version: ", String.valueOf(db.getVersion()));
       Log.i("Row Count: ", String.valueOf(c.getCount()));
       Log.i("Column Count: ", String.valueOf(c.getColumnCount()));
        for (String name: c.getColumnNames()) {
            Log.i("Column Names: ", name);
        }
   }





}


