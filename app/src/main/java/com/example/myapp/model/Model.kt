package com.example.myapp.model

import android.content.Context

class Model(var context : Context) {
    val dbHandler = DBOpenHelper(context, null)


    inner class CallDB : Runnable{
        override fun run() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    fun addToDBProduct(product: Product) {
        dbHandler.add(product)
//        Toast.makeText(context, "Product added to db", Toast.LENGTH_LONG).show()
    }

    fun addToDBUser(name : String, money : Int) {
        dbHandler.addUser(name, money)
  //      Toast.makeText(context, "User added to db", Toast.LENGTH_LONG).show()

    }

    fun addToDBCheck(name: String) {
        dbHandler.addCheck(name)
    }

    fun showCheck() : Long {
        var id : Long = 0
        val cursor =  dbHandler.getChecks()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ID))
            }
        }
        return id
    }

    fun showProducts() : ArrayList<Product> {
        val cursor =  dbHandler.getProducts()
        val arr = arrayListOf<Product>()
        cursor!!.moveToFirst()
        arr.add(
            Product(
                cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_PRICE)),
                cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_COUNT))
            )
        )
        while (cursor.moveToNext()) {
            arr.add(
                Product(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_PRICE)),
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_COUNT))
                )
            )
        }
        return arr
    }

    fun showUsers() : ArrayList<User> {
        val cursor =  dbHandler.getUsers()
        val arr = arrayListOf<User>()
        cursor!!.moveToFirst()
        arr.add(
            User(
                cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME)),
                cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_MONEY))
            )
        )
        while (cursor.moveToNext()) {
            arr.add(
                User(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME)),
                    cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_MONEY))
                )
            )
        }
        return arr
    }

    fun updateUser(name : String, money : Int, id : Int) {
        dbHandler.updateUser(name, money, id)
    }

    fun dropTable() {
        dbHandler.dropTable()
    }
}