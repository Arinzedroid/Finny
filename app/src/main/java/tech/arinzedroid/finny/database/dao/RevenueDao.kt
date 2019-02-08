package tech.arinzedroid.finny.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import tech.arinzedroid.finny.dataModels.RevenueModel

@Dao
interface RevenueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRevenue(revenue: RevenueModel)

    @Query("SELECT * FROM RevenueModel")
    fun getAllRevenues(): LiveData<List<RevenueModel>>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateRevenue(revenue: RevenueModel): Int

    @Delete()
    fun deleteRevenue(revenue: RevenueModel): Int
}