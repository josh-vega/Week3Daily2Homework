package com.example.week3daily2homework;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;


public class MyIntentService extends IntentService {
    Random random;
    ArrayList<Hero> listOfHeroes;

    public MyIntentService(){
        super("");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        listOfHeroes = new ArrayList<>();
        random = new Random();
        listOfHeroes.add(new Hero("Black", "Screaming", "Color", R.drawable.hero_black));
        listOfHeroes.add(new Hero("Gray", "Flying", "Color", R.drawable.hero_gray));
        listOfHeroes.add(new Hero("White", "Swordfighting", "Color", R.drawable.hero_white));
        listOfHeroes.add(new Hero("Fighter", "Strength", "Tri", R.drawable.skull));
        listOfHeroes.add(new Hero("Caster", "Magic", "Tri", R.drawable.wix));
        listOfHeroes.add(new Hero("Saber", "Unknown", "Tri", R.drawable.reaper));
        ArrayList<Hero> heroArrayList = new ArrayList<>();
        int counter = 0;
        while(counter<3){
            heroArrayList.add(listOfHeroes.get(random.nextInt(6)));
            counter++;
        }
        Log.d("TAG", "Random list generated: " + heroArrayList.get(0).getName());
        startBroadcast(heroArrayList);
        stopSelf();
    }

    private void startBroadcast(ArrayList<Hero> arrayList){
        Intent intent = new Intent();
        intent.setAction("hero_broadcast");
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("listOfHeroes", (ArrayList<? extends Parcelable>)arrayList);
        intent.putExtras(bundle);
        Log.d("TAG", "onCreate: SENDING BROADCAST");
        sendBroadcast(intent);
    }
}
