package com.diets.weightloss.common.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {

    val migration_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("create table FavoriteEntity (id int (10) NOT NULL, PRIMARY KEY (id));")
        }
    }

    val migration_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("create table WaterIntake (id int (20) NOT NULL, typeId int (10) NOT NULL, dirtyCapacity int (10) NOT NULL, clearCapacity int (10) NOT NULL, PRIMARY KEY (id));")
            database.execSQL("create table DrinksCapacities (typeDrink int NOT NULL, dirtyCapacity int NOT NULL,  PRIMARY KEY (typeDrink));")
            database.execSQL("create table WaterRate (timestamp int NOT NULL, rate int NOT NULL, PRIMARY KEY (timestamp));")
        }
    }

    val migration_3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("create table BadMig (id int (20) NOT NULL, PRIMARY KEY (id));")
        }
    }

    val migration_4_5 = object : Migration(4, 5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("create table HistoryDiet (id INTEGER NOT NULL, dietNumber INTEGER NOT NULL, startTime INTEGER NOT NULL, endTime INTEGER NOT NULL, state INTEGER NOT NULL, difficulty INTEGER NOT NULL, loseLifes INTEGER NOT NULL, userDifficulty INTEGER NOT NULL, satisfaction INTEGER NOT NULL, comment TEXT NOT NULL, weightUntil FLOAT NOT NULL, weightAfter FLOAT NOT NULL, PRIMARY KEY (id));")
            database.execSQL("alter table DietPlanEntity add column dietNumber INTEGER DEFAULT 0 NOT NULL;")
            database.execSQL("alter table DietPlanEntity add column startTime INTEGER DEFAULT 0 NOT NULL;")
            database.execSQL("alter table DietPlanEntity add column weightUntil INTEGER DEFAULT 0 NOT NULL;")
        }
    }
}