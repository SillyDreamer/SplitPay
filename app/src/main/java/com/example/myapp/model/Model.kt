package com.example.myapp.model

class Model(val dbHandler : DBOpenHelper) {

//    inner class CallDB : Runnable {
//        override fun run() {
//        }
//    }

    fun addToDBProduct(product: Product) {
        dbHandler.add(product)
    }

    fun addToDBUser(name : String, money : Int) {
        dbHandler.addUser(name, money)
    }

    fun addToDBCheck(name: String) {
        dbHandler.addCheck(name)
    }

    fun showCheckId() : Long {
        var id : Long = 0
        val cursor =  dbHandler.getChecks()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ID))
            }
        }
        return id
    }

    fun showChecks() : ArrayList<Pair<Long, String>> {
        val cursor = dbHandler.getChecks()
        val arr = arrayListOf<Pair<Long, String>>()
        if (cursor != null) {
            while(cursor.moveToNext()) {
                arr.add(Pair(cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ID)), cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME))))
            }
        }
        return arr
    }

    fun showProducts(check_id : Long) : ArrayList<Product> {
        val cursor =  dbHandler.getProducts()
        val arr = arrayListOf<Product>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getLong(cursor.getColumnIndex((DBOpenHelper.COLUMN_CHECK_ID))) == check_id) {
                    arr.add(
                        Product(
                            cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME)),
                            cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_PRICE)),
                            cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_COUNT))
                        )
                    )
                }
            }
        }
        return arr
    }

    fun showUsers(check_id : Long) : ArrayList<User> {
        val cursor =  dbHandler.getUsers()
        val arr = arrayListOf<User>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_CHECK_ID)) == check_id) {
                    arr.add(
                        User(
                            cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME)),
                            cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_MONEY)),
                            cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ID))
                        )
                    )
                }
            }
        }
        return arr
    }

    fun updateUser(name : String, money : Int, id : Long, check_id : Long) {
        dbHandler.updateUser(name, money, id, check_id)
    }

}