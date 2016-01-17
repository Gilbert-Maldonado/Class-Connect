package net.classconnect.classconnect;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends Activity {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        callbackManager = CallbackManager.Factory.create();
        Parse.initialize(this);

        //TESTING CODE DELETE WHEN DONE
        List<String> friends1 = new ArrayList<String>();
        List<String> friends2 = new ArrayList<String>();
        friends1.add("Joe");
        friends2.add("Bob");
        String name1 = "Bob";
        String name2 = "Joe";
        String ID1 = "B1";
        String ID2 = "J2";
        List<String> classes1 = new ArrayList<String>();
        List<String> classes2 = new ArrayList<String>();
        classes1.add("CS314");
        classes1.add("CS312");
        classes1.add("CS311");
        classes1.add("HIS355");
        classes2.add("CS314");
        classes2.add("CS312");
        classes2.add("CS311");
        Student s1 = new Student(ID1, name1, classes1, friends1);
        Student s2 = new Student(ID2, name2, classes2, friends2);
        Processing p1 = new Processing(name1, s1);
        p1.setFriends();
        p1.getMatches();
        //TESTING CODE ENDS HERE

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startActivity(new Intent(MainActivity.this, ClassEnterActivity.class));
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
