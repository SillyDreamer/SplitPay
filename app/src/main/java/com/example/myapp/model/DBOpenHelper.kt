package com.example.myapp.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBOpenHelper(
    context: Context,
    factory: SQLiteDatabase.CursorFactory?
) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, factory,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {

        val CHECK_TABLE =
            ("CREATE TABLE $TABLE_NAME3($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_DATE TEXT)")
        db.execSQL(CHECK_TABLE)

        val CREATE_PRODUCTS_TABLE =
            ("CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," + COLUMN_PRICE + " TEXT," + COLUMN_COUNT + " TEXT," + COLUMN_CHECK_ID + " INTEGER" + ")")
        db.execSQL(CREATE_PRODUCTS_TABLE)
        val CREATE_USER_TABLE =
            ("CREATE TABLE " + TABLE_NAME2 + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," + COLUMN_MONEY + " INTEGER," + COLUMN_PAID + " TEXT," + COLUMN_CHECK_ID + " INTEGER" + ")")
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2)
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME3")
        onCreate(db)
    }

    fun addCheck(name: String, date: String) {
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_DATE, date)
        val db = this.writableDatabase
        db.insert("checks", null, values)
        db.close()
    }

    fun add(product: Product) {
        var id: Long = 0
        val cursor = getChecks()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
            }
        }

        val values = ContentValues()
        values.put(COLUMN_NAME, product.name)
        values.put(COLUMN_PRICE, product.price)
        values.put(COLUMN_COUNT, product.count)
        values.put(COLUMN_CHECK_ID, id)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        Log.d("123", "addProduct")
        db.close()
    }

    fun addUser(name: String) {
        var id: Long = 0
        val cursor = getChecks()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
            }
        }

        Log.d("123", "addUser")
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_MONEY, 0)
        values.put(COLUMN_PAID, 0)
        values.put(COLUMN_CHECK_ID, id)
        val db = this.writableDatabase
        db.insert(TABLE_NAME2, null, values)
        db.close()
    }

    fun getProducts(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun getUsers(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM users", null)
    }

    fun getChecks(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM checks", null)
    }

    fun updateUser(name: String, money: Int, paid: Double, id: Long, check_id: Long) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_MONEY, money)
        values.put(COLUMN_PAID, paid)
        values.put(COLUMN_CHECK_ID, check_id)
        db.update(TABLE_NAME2, values, "_id = $id", null)
    }

    fun updateCheck(name: String, date: String, id: Long) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_DATE, date)
        db.update(TABLE_NAME3, values, "_id = $id", null)
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "check.db"
        val TABLE_NAME = "products"
        val TABLE_NAME2 = "users"
        val TABLE_NAME3 = "checks"
        val COLUMN_ID = "_id"
        val COLUMN_NAME = "name"
        val COLUMN_PRICE = "price"
        val COLUMN_COUNT = "count"
        val COLUMN_MONEY = "money"
        val COLUMN_CHECK_ID = "checkid"
        val COLUMN_DATE = "date"
        val COLUMN_PAID = "paid"
    }
}