package tech.arinzedroid.finny.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import tech.arinzedroid.finny.dataModels.SavingsModel

@Dao
interface SavingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSavings(savingsModel: SavingsModel)

    @Query("SELECT * FROM SavingsModel")
    fun getAllSavings(): LiveData<List<SavingsModel>>

    @Query("SELECT * FROM SavingsModel WHERE id = :id")
    fun getSavingById(id: String): SavingsModel

    @Delete
    fun deleteSavings(savingsModel: SavingsModel): Int

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateSavings(savingsModel: SavingsModel): Int
}