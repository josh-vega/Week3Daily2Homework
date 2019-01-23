package com.example.week3daily2homework;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver myBroadcastReceiver;
    ArrayList<Hero> listOfHeroes;
    RecyclerView recyclerView;
    RecyclerViewAdapter rvAdapter;
    boolean isBound = false;
    Messenger mMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rvMainRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //filtering intent
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("hero_broadcast");
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        myBroadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                ArrayList<Hero> listOfHeroes = intent.getExtras().getParcelableArrayList("listOfHeroes");
                Toast.makeText(context,"Receiving info= " + listOfHeroes.get(0).getName(), Toast.LENGTH_LONG).show();
                Log.d("TAG", "onReceive: ");

                rvAdapter = new RecyclerViewAdapter(listOfHeroes);
                recyclerView.setAdapter(rvAdapter);

            }
        };
        registerReceiver(myBroadcastReceiver, intentFilter);


    }

    public void onClick(View view){
        Log.d("TAG", "Start Intent Service");
        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBound = true;

            //create messenger object
            mMessenger = new Messenger(service);

            //Create a message
            Message msg = Message.obtain(null, MessengerService.MSG_SAY_HELLO, 0, 0);

            //Create a bundle with data
            Bundle bundle = new Bundle();
            bundle.putString("hello", "Assemble your Heroes!");

            //Set bundle data to message
            msg.setData(bundle);

            try{
                mMessenger.send(msg);
            }
            catch (RemoteException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // unbind or process might have crashes
            mMessenger = null;
            isBound = false;
        }
    };
}

