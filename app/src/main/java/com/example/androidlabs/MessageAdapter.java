package com.example.androidlabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<Messenger> messages;
    private LayoutInflater inflater;

    public MessageAdapter(Context context, List<Messenger> messages ) {
        this.context = context;
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        if (messages.get(position).isSend()) {
            view = inflater.inflate(R.layout.left, null);
        } else {
            view = inflater.inflate(R.layout.right, null);
        }
        TextView messageText = view.findViewById(R.id.msgr);
        messageText.setText(messages.get(position).getContent());
        return view;
    }

    @Override
    public int getCount() {
        return messages.size();
    }


     @Override
     public  Messenger getItem(int position) {
         return messages.get(position);
     }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
