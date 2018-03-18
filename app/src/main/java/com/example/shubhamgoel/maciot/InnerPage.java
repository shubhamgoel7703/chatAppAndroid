package com.example.shubhamgoel.maciot;

/**
 * Created by shubhamgoel on 06/03/18.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;


public class InnerPage extends AppCompatActivity {


    static List<Object> heroList = new ArrayList<>();;

    //the listview
    ListView listView;

    MyListAdapter adapter;
    String message="userMessage";
    EditText userMessgae;


    // <emo paramas>

    CheckBox mCheckBox;
    EmojiconEditText emojiconEditText;//, emojiconEditText2;
    //EmojiconTextView textView;
    ImageView emojiButton;
    ImageView submitButton;
    View rootView;
    EmojIconActions emojIcon;

    // </emo params>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  MainActivity.mSocket.connect();
        setContentView(R.layout.inner_page);

        TextView serverStatusText = (TextView) findViewById(R.id.serverStatusText);

        userMessgae = (EditText) findViewById(R.id.userMessage);


        if(MainActivity.mSocket.connected()) {
            serverStatusText.setText("Connected");
            MainActivity.mSocket.on("ChatMessage",onStopTyping);
        }
        else
            serverStatusText.setText("Connection Problem! Check the server");

      /*  ListView lv = new ListView(this);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, mobileArray);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);*/




        listView = (ListView) findViewById(R.id.mobile_list);

        //creating the adapter
        adapter = new MyListAdapter(this, R.layout.activity_listview, heroList);

        //attaching adapter to the listview
        listView.setAdapter(adapter);



        // emo things
        rootView = findViewById(R.id.root_view);
        emojiButton = (ImageView) findViewById(R.id.emoji_btn);
      //  submitButton = (ImageView) findViewById(R.id.submit_btn);
        mCheckBox = (CheckBox) findViewById(R.id.use_system_default);
        emojiconEditText = (EmojiconEditText) findViewById(R.id.userMessage);
     //   emojiconEditText2 = (EmojiconEditText) findViewById(R.id.emojicon_edit_text2);
     //   textView = (EmojiconTextView) findViewById(R.id.textView);
        emojIcon = new EmojIconActions(this, rootView, emojiconEditText, emojiButton);
        emojIcon.ShowEmojIcon();
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard", "open");
            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard", "close");
            }
        });

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                emojIcon.setUseSystemEmoji(b);
                //textView.setUseSystemDefault(b);
            }
        });
        //emojIcon.addEmojiconEditTextList(emojiconEditText2);



        // token check
        MyFirebaseInstanceIdService n = new MyFirebaseInstanceIdService();
        n.onTokenRefresh();

    }
    private Emitter.Listener onStopTyping = new Emitter.Listener() {

        @Override
        public void call(final java.lang.Object... args) {
            InnerPage.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String data = (String ) args[0];
                    JsonObject obj = new JsonParser().parse(data).getAsJsonObject();
                    String jsonUsername,jsonMessage;
                    try
                    {
                        jsonUsername = obj.get("username").getAsString();
                        jsonMessage=obj.get("message").getAsString();
                        //Object listObj =new Object(username,message);
                        heroList.add(new Object(jsonUsername, jsonMessage));
                        adapter.ListModified();
                        listView.setSelection(adapter.getCount() - 1);
                    }
                    catch (Exception e)
                    {
                        Log.e("Catch", e.getMessage());
                        return;
                    }
                    // removeTyping(username);
                }
            });
        }
    };


    public void SendButton(View v) throws MalformedURLException, JSONException {

        message = userMessgae.getText().toString();
        heroList.add(new Object(MainActivity.username, message));
        adapter.ListModified();
        listView.setSelection(adapter.getCount() - 1);
       /* userMessgae.clearFocus();
        userMessgae.setInputType(InputType.TYPE_NULL);*/
        userMessgae.setText("");

           Object obj = new Object(MainActivity.username,message);
        Gson gson = new Gson();
        String json = gson.toJson(obj);

          MainActivity.mSocket.emit("ChatMessage", json);
        FindToken(message);
    }

    public void FindToken(String mes) throws MalformedURLException, JSONException {

        // Send
        if(MyFirebaseInstanceIdService.isToken) {
            //JSONObject jsonObj = new JSONObject("{\"username\":" + MainActivity.username +",\"Token\":"+MyFirebaseInstanceIdService.token +"}");
            String token = MyFirebaseInstanceIdService.token;
            String object = "{\"username\":\""+MainActivity.username+"\",\"token\":\""+token+"\",\"message\":\""+ mes+"\"}";
            //String object2 = "{\"phonetype\":\"N95\",\"cat\":\"WP\"}";

            JSONObject jsonObj = new JSONObject(object);

            MainActivity.mSocket.emit("RegisterToken", jsonObj);
        }

    }
}