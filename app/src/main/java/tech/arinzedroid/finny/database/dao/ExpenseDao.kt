package tech.arinzedroid.finny.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import tech.arinzedroid.finny.dataModels.ExpenseModel

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addExpense(expense: ExpenseModel)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateExpense(expense: ExpenseModel)

    @Query("SELECT * FROM ExpenseModel")
    fun getAllExpense(): LiveData<List<ExpenseModel>>?

    @Query("SELECT * FROM ExpenseModel WHERE id = :id")
    fun getExpenseById(id: String): ExpenseModel

    @Delete
    fun deleteExpense(expense: ExpenseModel)
}