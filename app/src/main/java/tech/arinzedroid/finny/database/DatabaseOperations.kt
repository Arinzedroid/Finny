package tech.arinzedroid.finny.database

import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import tech.arinzedroid.finny.dataModels.GoalsModel
import tech.arinzedroid.finny.dataModels.RevenueModel

class DatabaseOperations(private val mDB: AppDatabase) {

    fun insertGoal(goals: GoalsModel){
        mDB.goalDao().addGoals(goals)
    }

    fun insertAllGoals(goals: List<GoalsModel>){
        mDB.goalDao().addAllGoals(goals)
    }

    fun updateGoal(goal: GoalsModel): Int{
        return mDB.goalDao().updateGoal(goal)
    }

    fun getAllGoals(): LiveData<List<GoalsModel>>{
        return mDB.goalDao().getAllGoals()
    }

    fun getGoalById(id: String): GoalsModel{
        return mDB.goalDao().getGoalById(id)
    }

    fun deleteGoal(goal: GoalsModel): Int{
        return mDB.goalDao().deleteGoal(goal)
    }

    fun getAllRevenue(): LiveData<List<RevenueModel>> {
        return mDB.revenueDao().getAllRevenues()
    }

    fun addRevenue(revenue: RevenueModel){
        return mDB.revenueDao().addRevenue(revenue)
    }

    fun deleteRevenue(revenue: RevenueModel): Int{
        return mDB.revenueDao().deleteRevenue(revenue)
    }

    fun updateRevenue(revenue: RevenueModel): Int{
        return mDB.revenueDao().updateRevenue(revenue)
    }

}