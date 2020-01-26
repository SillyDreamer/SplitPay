package com.example.myapp.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBOpenHelper(context: Context,
                   factory: SQLiteDatabase.CursorFactory?
): SQLiteOpenHelper(context,
    DATABASE_NAME, factory,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {

        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," + COLUMN_PRICE + " TEXT," + COLUMN_COUNT + " TEXT" + ")")
        db.execSQL(CREATE_PRODUCTS_TABLE)
        val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_NAME2 + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," + COLUMN_MONEY + " INTEGER" + ")")
        db.execSQL(CREATE_USER_TABLE)
        Log.d("123", "onCreateDB")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2)
        onCreate(db)
    }

    fun add(product : Product) {
        val values = ContentValues()
        values.put(COLUMN_NAME, product.name)
        values.put(COLUMN_PRICE, product.price)
        values.put(COLUMN_COUNT, product.count)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        Log.d("123", "addProduct")
        db.close()

    }

    fun addUser(name : String, money : Int) {
        Log.d("123", "addUser")
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_MONEY, 0)
        val db = this.writableDatabase
        db.insert(TABLE_NAME2, null, values)
        db.close()
    }

    fun getProducts() : Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun getUsers() : Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM users", null)
    }

    fun updateUser(name : String, money : Int, id : Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("name", name)
        values.put("money", money)
        db.update(TABLE_NAME2, values, "_id = " + id, null)
    }


    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "check.db"
        val TABLE_NAME = "products"
        val TABLE_NAME2 = "users"
        val COLUMN_ID = "_id"
        val COLUMN_NAME = "name"
        val COLUMN_PRICE = "price"
        val COLUMN_COUNT = "count"
        val COLUMN_MONEY = "money"
    }
}