package tech.arinzedroid.finny.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import tech.arinzedroid.finny.dataModels.GoalsModel
import tech.arinzedroid.finny.dataModels.RevenueModel
import tech.arinzedroid.finny.dataModels.UpdatedGoal
import tech.arinzedroid.finny.dataModels.UpdatedRevenue
import tech.arinzedroid.finny.database.AppDatabase
import tech.arinzedroid.finny.repo.AppRepo


class AppViewModel(application: Application) : AndroidViewModel(application){

    private val appDatabase: AppDatabase? = AppDatabase.getInstance(this.getApplication())

    private var goalMutableList : LiveData<List<GoalsModel>>? = null
    private var revenueMutableList: LiveData<List<RevenueModel>>? = null
    private val updatedGoalLiveData: MutableLiveData<UpdatedGoal> = MutableLiveData()
    private val updatedRevenueLiveData: MutableLiveData<UpdatedRevenue> = MutableLiveData()


    init {
        if(goalMutableList == null){
            getGoalsDB()
        }
        if(revenueMutableList == null){
            getRevenueDB()
        }
    }

    fun addGoal(goal: GoalsModel){
       addGoalDB(goal)
    }

    fun addGoals(goalsList: ArrayList<GoalsModel>){
        //goalMutableList.postValue(goalsList)
    }

    fun updateGoal(goal: GoalsModel, position: Int){
        val update = UpdatedGoal()
        update.goalsModel = goal
        update.positon = position
        updatedGoalLiveData.postValue(update)
        updateGoalDB(goal)
    }

    fun deleteGoal(goal: GoalsModel){
        deleteGoalDB(goal)
    }

    fun addRevenue(revenueModel: RevenueModel){
        addRevenueDB(revenueModel)
    }

    fun deleteRevenue(revenueModel: RevenueModel){
        deleteRevenueDB(revenueModel)
    }

    fun updateRevenue(revenueModel: RevenueModel, position: Int){
        val updatedRevenue = UpdatedRevenue()
        updatedRevenue.revenue = revenueModel
        updatedRevenue.position = position
        updatedRevenueLiveData.postValue(updatedRevenue)
        updateRevenueDB(revenueModel)
    }




    fun getGoalUpdate(): LiveData<UpdatedGoal> = updatedGoalLiveData

    fun getGoals() : LiveData<List<GoalsModel>>? = goalMutableList

    fun getRevenueUpdate(): LiveData<UpdatedRevenue> = updatedRevenueLiveData

    fun getRevenue(): LiveData<List<RevenueModel>>? = revenueMutableList





    private fun getGoalsDB() = doAsync {
        val data = appDatabase?.goalDao()?.getAllGoals()
        uiThread {
            goalMutableList = data
        }
    }

    private fun updateGoalDB(goal: GoalsModel){
        doAsync {
            appDatabase?.goalDao()?.updateGoal(goal)
        }
    }

    private fun addGoalDB(goal:GoalsModel){
        doAsync {
            appDatabase?.goalDao()?.addGoals(goal)
        }
    }

    private fun deleteGoalDB(goal: GoalsModel){
        doAsync {
            appDatabase?.goalDao()?.deleteGoal(goal)
        }
    }

    private fun getRevenueDB() {
        doAsync {val data = appDatabase?.revenueDao()?.getAllRevenues()
            uiThread { revenueMutableList  = data }
        }
    }

    private fun addRevenueDB(revenueModel: RevenueModel) {
        doAsync { appDatabase?.revenueDao()?.addRevenue(revenueModel)}
    }

    private fun updateRevenueDB(revenueModel: RevenueModel){
        doAsync { appDatabase?.revenueDao()?.updateRevenue(revenueModel)}
    }

    private fun deleteRevenueDB(revenueModel: RevenueModel){
        doAsync { appDatabase?.revenueDao()?.deleteRevenue(revenueModel) }
    }
}

