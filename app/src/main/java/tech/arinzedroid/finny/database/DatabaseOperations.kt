package tech.arinzedroid.finny.database

import android.content.Context
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import tech.arinzedroid.finny.dataModels.*
import tech.arinzedroid.finny.database.dao.TaskDao
import tech.arinzedroid.finny.interfaces.*

class DatabaseOperations(context: Context) {

    private val mDB = AppDatabase.getInstance(context)

    fun insertGoal(goals: GoalsModel){
        doAsync {
            mDB?.goalDao()?.addGoals(goals)
        }
    }

    fun updateGoal(goal: GoalsModel){
       doAsync {
          val data = mDB?.goalDao()?.updateGoal(goal)
       }
    }

    fun getAllGoals(listListener: GoalsListListener){
        doAsync {
            val data =  mDB?.goalDao()?.getAllGoals()
            listListener.goalsLiveData(data)
        }
    }

    fun getGoalById(id: String, workModel: WorkModel, listener: GoalListener){
        doAsync {
            val data = mDB?.goalDao()?.getGoalById(id)
            listener.goalData(workModel,data)
        }
    }

    fun deleteGoal(goal: GoalsModel){
        doAsync {
            val data = mDB?.goalDao()?.deleteGoal(goal)
        }
    }

    fun getAllRevenue(listListener: RevenuesListListener) {
       doAsync {
           val data = mDB?.revenueDao()?.getAllRevenues()
           listListener.revenueLiveData(data)
       }
    }

    fun addRevenue(revenue: RevenueModel){
        doAsync {
            mDB?.revenueDao()?.addRevenue(revenue)
        }
    }

    fun deleteRevenue(revenue: RevenueModel){
        doAsync {
            val data = mDB?.revenueDao()?.deleteRevenue(revenue)
        }
    }

    fun updateRevenue(revenue: RevenueModel){
       doAsync {
           val data =  mDB?.revenueDao()?.updateRevenue(revenue)
       }
    }

    fun getRevenueById(id: String, listener: RevenueListener){
        doAsync { val data = mDB?.revenueDao()?.getRevenueById(id)
            listener.revenueData(data)
        }
    }

    fun addExpense(expense: ExpenseModel){
        doAsync {
            mDB?.expenseDao()?.addExpense(expense)
        }
    }

    fun getAllExpenses(listener: ExpenseListListener){
        doAsync {
            val data = mDB?.expenseDao()?.getAllExpense()
            listener.expenseLiveData(data)
        }
    }

    fun getExpenseById(id: String, listener: ExpenseListener){
        doAsync {
            val data = mDB?.expenseDao()?.getExpenseById(id)
            uiThread {
                listener.expensesData(data)
            }
        }
    }

    fun updateExpense(expense: ExpenseModel){
        doAsync {
            val data = mDB?.expenseDao()?.updateExpense(expense)
        }
    }

    fun deleteExpense(expense: ExpenseModel){
        doAsync {
            val data = mDB?.expenseDao()?.deleteExpense(expense)
        }
    }

    fun addWork(workModel: WorkModel){
        doAsync {
            val data = mDB?.workDao()?.addWork(workModel)
        }
    }

    fun getAllWorks(listener: WorkListListener){
        doAsync {
            val data = mDB?.workDao()?.getAllWorks()
            uiThread {
                listener.workList(data)
            }
        }
    }

    fun getDataByWorkId(id: String, listener: WorkListener){
        doAsync {
            val data = mDB?.workDao()?.getWorkByWorkId(id)
            uiThread {
                listener.worksData(data)
            }
        }
    }

    fun getWorkTodo(itemType: String, dueDate: Long, listener: WorkListListener){
        doAsync {
            val data = mDB?.workDao()?.getWorkTodo(itemType,dueDate)
            uiThread {
                listener.workList(data)
            }
        }
    }

    fun getWorkData(itemType: String, itemId: String, listener: WorkListener){
        doAsync {
            val data = mDB?.workDao()?.getWorkData(itemType,itemId)
            uiThread {
                listener.worksData(data)
            }
        }
    }

    fun updateWork(workModel: WorkModel){
        doAsync {
            val data = mDB?.workDao()?.updateWork(workModel)
        }
    }

    fun deleteWork(workModel: WorkModel){
        doAsync {
            val data = mDB?.workDao()?.deleteWork(workModel)
        }
    }

    fun addTask(task: TasksData){
        doAsync { mDB?.tasksDao()?.insertTask(task) }
    }

    fun getAllTasks(listener: TasksListListerner){
        doAsync {
            val data = mDB?.tasksDao()?.getTasks()
            uiThread { listener.onTasksList(data) }
        }
    }

    fun getTaskByWork(work:WorkModel,listener: TaskListener){
        doAsync {
            val data = mDB?.tasksDao()?.getTaskByWork(workId = work.workId,type = work.itemType,itemId = work.itemId)
            uiThread {
                listener.onTask(data)
            }
        }
    }

    fun getTaskByItem(itemType: String, itemId: String,listener: TaskListener){
        doAsync { val data = mDB?.tasksDao()?.getTaskByItem(itemId,itemType)
            uiThread {
                listener.onTask(data)
            }
        }
    }

    fun deleteTask(task: TasksData){
        doAsync { mDB?.tasksDao()?.deleteTask(task)}
    }

    fun updateTask(task: TasksData){
        doAsync { mDB?.tasksDao()?.updateTask(task) }
    }



}