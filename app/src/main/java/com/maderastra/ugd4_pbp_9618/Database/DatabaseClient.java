package com.maderastra.ugd4_pbp_9618.Database;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Room;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient databaseClient;

    private AppDatabase database;

    private DatabaseClient(Context context){
        this.context = context;
        database = Room.databaseBuilder(context, AppDatabase.class, "user").build();
    }

    public static synchronized DatabaseClient getInstance(Context context){
        if (databaseClient==null){
            databaseClient = new DatabaseClient(context);
        }
        return databaseClient;
    }

    public AppDatabase getDatabase() {return database; }
}
