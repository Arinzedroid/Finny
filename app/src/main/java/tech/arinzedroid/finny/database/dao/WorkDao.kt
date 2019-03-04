package tech.arinzedroid.finny.database.dao

import android.arch.persistence.room.*
import tech.arinzedroid.finny.dataModels.WorkModel

@Dao
interface WorkDao{

    @Query("SELECT * FROM WorkModel")
    fun getAllWorks(): List<WorkModel>

    @Query("SELECT * FROM WorkModel WHERE itemType = :itemType AND dueDate = :dueDate")
    fun getWorkTodo(itemType: String, dueDate: Long): List<WorkModel>

    @Query("SELECT * FROM WorkModel WHERE itemType = :itemType AND itemId = :itemId")
    fun getWorkData(itemType: String,itemId: String): WorkModel

    @Query("SELECT * FROM WorkModel WHERE workId = :workId")
    fun getWorkByWorkId(workId: String): WorkModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWork(workModel: WorkModel)

    @Delete
    fun deleteWork(workModel: WorkModel)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateWork(workModel: WorkModel)

}