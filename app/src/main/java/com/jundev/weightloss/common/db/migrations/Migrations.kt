package com.jundev.weightloss.common.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {

    val migration_1_2 = object : Migration(1,2){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("create table FavoriteEntity (id int (10) NOT NULL, PRIMARY KEY (id));")
        }
    }

    val migration_2_3 = object : Migration(2,3){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("create table WaterIntake (id int (20) NOT NULL, typeId int (10) NOT NULL, dirtyCapacity int (10) NOT NULL, clearCapacity int (10) NOT NULL, PRIMARY KEY (id));")
            database.execSQL("create table DrinksCapacities (typeDrink int NOT NULL, dirtyCapacity int NOT NULL, PRIMARY KEY (typeDrink));")
            database.execSQL("create table WaterRate (timestamp int NOT NULL, rate int NOT NULL, PRIMARY KEY (timestamp));")
        }
    }
}