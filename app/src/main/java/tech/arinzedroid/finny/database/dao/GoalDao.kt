package tech.arinzedroid.finny.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import tech.arinzedroid.finny.dataModels.GoalsModel

@Dao
interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGoals(goals: GoalsModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllGoals(goals: List<GoalsModel>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateGoal(goal:GoalsModel): Int

    @Delete()
    fun deleteGoal(goal: GoalsModel): Int

    @Query("SELECT * FROM GoalsModel")
    fun getAllGoals(): LiveData<List<GoalsModel>>

    @Query("SELECT * FROM GoalsModel WHERE id = :id")
    fun getGoalById(id: String): GoalsModel

    @Query("SELECT COUNT(*) FROM GoalsModel")
    fun getCount(): Int


}