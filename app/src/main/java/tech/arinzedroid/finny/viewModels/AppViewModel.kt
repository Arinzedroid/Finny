package tech.arinzedroid.finny.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import tech.arinzedroid.finny.dataModels.*
import tech.arinzedroid.finny.database.AppDatabase
import tech.arinzedroid.finny.repo.AppRepo


class AppViewModel(application: Application) : AndroidViewModel(application){

    private val appDatabase: AppDatabase? = AppDatabase.getInstance(this.getApplication())

    private var goalMutableList : LiveData<List<GoalsModel>>? = null
    private var revenueMutableList: LiveData<List<RevenueModel>>? = null
    private var expenseMutableList: LiveData<List<ExpenseModel>>? = null
    private var savingsMutableList: LiveData<List<SavingsModel>>? = null

    init {
        if(goalMutableList == null){
          getGoalsDB()
        }
        if(revenueMutableList == null){
            getRevenueDB()
        }
        if(expenseMutableList == null){
            getAllExpenseDB()
        }
        if(savingsMutableList == null){
            getAllSavingsDB()
        }

    }

    fun addGoal(goal: GoalsModel){
       addGoalDB(goal)
    }

    fun updateGoal(goal: GoalsModel){
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

    fun updateRevenue(revenueModel: RevenueModel){
        updateRevenueDB(revenueModel)
    }

    fun addExpense(expense: ExpenseModel){
        addExpenseDB(expense)
    }

    fun deleteExpense(expense: ExpenseModel){
        deleteExpenseDB(expense)
    }

    fun updateExpense(expense: ExpenseModel){
        updateExpenseDB(expense)
    }

    fun addSavings(savingsModel: SavingsModel){
        addSavingsDB(savingsModel)
    }

    fun deleteSavings(savingsModel: SavingsModel){
        deleteSavingsDB(savingsModel)
    }

    fun updateSavings(savingsModel: SavingsModel){
        updateSavingsDB(savingsModel)
    }


    fun getGoals() : LiveData<List<GoalsModel>>? = goalMutableList

    fun getRevenue(): LiveData<List<RevenueModel>>? = revenueMutableList

    fun getExpense() : LiveData<List<ExpenseModel>>? = expenseMutableList

    fun getSavings() : LiveData<List<SavingsModel>>? = savingsMutableList

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

    private fun addExpenseDB(expense: ExpenseModel){
        doAsync { appDatabase?.expenseDao()?.addExpense(expense)}
    }

    private fun updateExpenseDB(expense: ExpenseModel){
        doAsync { appDatabase?.expenseDao()?.updateExpense(expense) }
    }

    private fun deleteExpenseDB(expense: ExpenseModel){
        doAsync { appDatabase?.expenseDao()?.deleteExpense(expense) }
    }

    private fun getAllExpenseDB(){
        doAsync { val data = appDatabase?.expenseDao()?.getAllExpense()
            uiThread { expenseMutableList =  data}
        }
    }

    private fun getAllSavingsDB(){
        doAsync {
            val data = appDatabase?.savingsDao()?.getAllSavings()
            uiThread {
                savingsMutableList = data
            }
        }
    }

    private fun addSavingsDB(savingsModel: SavingsModel){
        doAsync {
            appDatabase?.savingsDao()?.addSavings(savingsModel)
        }
    }

    private fun updateSavingsDB(savingsModel: SavingsModel){
        doAsync { appDatabase?.savingsDao()?.updateSavings(savingsModel)
        }
    }

    private fun deleteSavingsDB(savingsModel: SavingsModel){
        doAsync {
            appDatabase?.savingsDao()?.deleteSavings(savingsModel)
        }
    }
}

