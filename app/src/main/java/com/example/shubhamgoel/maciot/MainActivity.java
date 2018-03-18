package com.example.shubhamgoel.maciot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class MainActivity extends AppCompatActivity {

    public static String username="";



    public static Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://13.127.224.111:4000");
        } catch (URISyntaxException e) {

            Log.i("catch", "This is my message");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // mSocket.on("FrontEndData",onStopTyping);
        mSocket.connect();
      //  attemptSend();
        Log.i("myTag", "This is my message");
        setContentView(R.layout.activity_main);




        //
    }

    private Emitter.Listener onStopTyping = new Emitter.Listener() {
        @Override
        public void call(final java.lang.Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    try {
                        Log.e("TRY", "sdhajk");
                        username = data.getString("username");
                    } catch (JSONException e) {
                        Log.e("CATCH", e.getMessage());
                        return;
                    }
                   // removeTyping(username);
                }
            });
        }


    };

    public void buttonOnClick(View v)
    {
       // Button button = (Button) v;
       // button.setText("Clicked");
        TextView passView = (TextView) findViewById(R.id.passwordText);
        TextView userView = (TextView) findViewById(R.id.usernameText);
        String user = userView.getText().toString();
        String pass = passView.getText().toString();

        if(true)//(pass.equals("2040") && user.equals("supermaciot"))
        {
            Log.d("login", "This is my message");
            Intent launchActivity1= new Intent(MainActivity.this,InnerPage.class);
            startActivity(launchActivity1);
        }
        else
        {
            passView.setText("");
            userView.setText("");

        }
        username = user;

    }


    private void attemptSend() {


       // Log.d("myTag", "This is my message");
    }
}
