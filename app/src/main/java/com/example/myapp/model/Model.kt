package com.example.myapp.model

class Model(private val dbHandler: DBOpenHelper) {

    fun addToDBProduct(product: Product) {
        dbHandler.add(product)
    }

    fun addToDBUser(name: String) {
        dbHandler.addUser(name)
    }

    fun addToDBCheck(name: String, date: String) {
        dbHandler.addCheck(name, date)
    }

    fun showCheckId(): Long {
        var id: Long = 0
        val cursor = dbHandler.getChecks()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                id = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ID))
            }
        }
        return id
    }

    fun showChecks(): ArrayList<Triple<String, String, String>> {
        val cursor = dbHandler.getChecks()
        val arr = arrayListOf<Triple<String, String, String>>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                arr.add(
                    Triple(
                        cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ID)).toString(),
                        cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_DATE))
                    )
                )
            }
        }
        return arr
    }

    fun showProducts(check_id: Long): ArrayList<Product> {
        val cursor = dbHandler.getProducts()
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

    fun showUsers(check_id: Long): ArrayList<User> {
        val cursor = dbHandler.getUsers()
        val arr = arrayListOf<User>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_CHECK_ID)) == check_id) {
                    arr.add(
                        User(
                            cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME)),
                            cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_MONEY)),
                            cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_PAID)).toDouble(),
                            cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COLUMN_ID))
                        )
                    )
                }
            }
        }
        return arr
    }

    fun updateUser(name: String, money: Int, paid: Double, id: Long, check_id: Long) {
        dbHandler.updateUser(name, money, paid, id, check_id)
    }

    fun updateCheck(name: String, date: String, id: Long) {
        dbHandler.updateCheck(name, date, id)
    }

}