package com.maderastra.ugd4_pbp_9618.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.maderastra.ugd4_pbp_9618.Model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDAO();
}
