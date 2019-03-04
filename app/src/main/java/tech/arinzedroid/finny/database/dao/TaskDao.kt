package tech.arinzedroid.finny.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import tech.arinzedroid.finny.dataModels.TasksData

@Dao
interface TaskDao {

    @Query("SELECT * FROM TasksData")
    fun getTasks(): LiveData<List<TasksData>>

    @Query("SELECT * FROM TasksData WHERE itemType = :type AND itemId = :itemId AND workId = :workId")
    fun getTaskByWork(type: String, itemId: String, workId: String): TasksData

    @Query("SELECT * FROM TasksData WHERE itemId = :itemId AND itemType = :itemType")
    fun getTaskByItem(itemId: String, itemType: String): TasksData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(tasksData: TasksData)

    @Delete
    fun deleteTask(tasksData: TasksData)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateTask(tasksData: TasksData)



}