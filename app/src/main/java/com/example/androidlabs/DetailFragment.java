package com.example.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;
    private int id;



    public void setTablet(boolean tablet) { isTablet = tablet; }





    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
      //  id = dataFromActivity.getLong(ChatRoomActivity. );
        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment, container, false);
        //show the message
        TextView message = (TextView)result.findViewById(R.id.message);
        message.setText("message: "+dataFromActivity.getString("item"));
        //show the id:
        TextView idView = (TextView)result.findViewById(R.id.idText);
        idView.setText("Listview ID=" + id);
        TextView position = (TextView)result.findViewById(R.id.position);
        position.setText("DB id = "+id);
        // get the delete button, and add a click listener:
        Button deleteButton = (Button)result.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener( clk -> {
            if(isTablet) { //both the list and details are on the screen:
                ChatRoomActivity parent = (ChatRoomActivity)getActivity();
              //  parent.deleteMessageId((int)db_id); //this deletes the item and updates the list
                //now remove the fragment since you deleted it from the database:
                // this is the object to be removed, so remove(this):
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            //for Phone:
            else {
                EmptyActivity parent = (EmptyActivity) getActivity();
                Intent backtoChatRoomActivity = new Intent();
               // backtoChatRoomActivity.putExtra(ChatRoomActivity.db_id, dataFromActivity.getLong("db_id"));
                parent.setResult(Activity.RESULT_OK, backtoChatRoomActivity); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back
            }
        });
        return result;
    }
}
