package it.ilfuma.rc.casteldileva.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Shop.class}, version = 1)
public abstract class AppShopDatabase extends RoomDatabase {
    public abstract ShopDAO shopDAO();
}