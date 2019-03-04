package tech.arinzedroid.finny.repo


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import tech.arinzedroid.finny.dataModels.ExpenseModel
import tech.arinzedroid.finny.dataModels.GoalsModel
import tech.arinzedroid.finny.dataModels.RevenueModel
import tech.arinzedroid.finny.database.AppDatabase

class AppRepo(context: Context) {

    private val appDatabase: AppDatabase? = AppDatabase.getInstance(context)

    fun getGoalsDB(): LiveData<List<GoalsModel>>?{
        var goalM: MutableLiveData<List<GoalsModel>> = MutableLiveData<List<GoalsModel>>()
        doAsync {
            val data = appDatabase?.goalDao()?.getAllGoals()
            uiThread {
                //goalM = worksData
               data?.let {
                   goalM.postValue(it.value)
                   println("dataSize is ${it.value?.size}")
               }
            }
        }
        return goalM
    }

    fun updateGoalDB(goal: GoalsModel){
        doAsync {
            appDatabase?.goalDao()?.updateGoal(goal)
        }
    }

    fun addGoalDB(goal: GoalsModel){
        doAsync {
            appDatabase?.goalDao()?.addGoals(goal)
        }
    }

    fun deleteGoalDB(goal: GoalsModel){
        doAsync {
            appDatabase?.goalDao()?.deleteGoal(goal)
        }
    }

    fun getRevenueDB() : LiveData<List<RevenueModel>> {
        var revenueM: LiveData<List<RevenueModel>> = MutableLiveData<List<RevenueModel>>()
        doAsync {val data = appDatabase?.revenueDao()?.getAllRevenues()
            uiThread { data?.let { revenueM = it } }
        }
        return revenueM
    }

    fun addRevenueDB(revenueModel: RevenueModel) {
        doAsync { appDatabase?.revenueDao()?.addRevenue(revenueModel)}
    }

    fun updateRevenueDB(revenueModel: RevenueModel){
        doAsync { appDatabase?.revenueDao()?.updateRevenue(revenueModel)}
    }

    fun deleteRevenueDB(revenueModel: RevenueModel){
        doAsync { appDatabase?.revenueDao()?.deleteRevenue(revenueModel) }
    }

    fun addExpenseDB(expense: ExpenseModel){
        doAsync { appDatabase?.expenseDao()?.addExpense(expense)}
    }

    fun updateExpenseDB(expense: ExpenseModel){
        doAsync { appDatabase?.expenseDao()?.updateExpense(expense) }
    }

    fun deleteExpenseDB(expense: ExpenseModel){
        doAsync { appDatabase?.expenseDao()?.deleteExpense(expense) }
    }

    fun getAllExpenseDB(): LiveData<List<ExpenseModel>>{
        var expenseM: LiveData<List<ExpenseModel>> = MutableLiveData<List<ExpenseModel>>()
        doAsync { val data = appDatabase?.expenseDao()?.getAllExpense()
            uiThread { data?.let { expenseM = data }}
        }
        return expenseM
    }

}