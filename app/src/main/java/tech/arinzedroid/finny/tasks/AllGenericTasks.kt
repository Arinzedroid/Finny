package tech.arinzedroid.finny.tasks

import android.text.TextUtils
import androidx.work.WorkManager
import tech.arinzedroid.finny.Finny
import tech.arinzedroid.finny.dataModels.GoalsModel
import tech.arinzedroid.finny.dataModels.RevenueModel
import tech.arinzedroid.finny.dataModels.TasksData
import tech.arinzedroid.finny.dataModels.WorkModel
import tech.arinzedroid.finny.database.DatabaseOperations
import tech.arinzedroid.finny.interfaces.GoalListener
import tech.arinzedroid.finny.interfaces.RevenueListener
import tech.arinzedroid.finny.interfaces.WorkListListener
import tech.arinzedroid.finny.interfaces.WorkListener
import tech.arinzedroid.finny.utils.Constants
import tech.arinzedroid.finny.utils.CurrencyFormatter
import tech.arinzedroid.finny.utils.Notification
import java.util.*

class AllGenericTasks {

    private val context = Finny.getContext()

    fun handleWorkerTask(id: UUID){
        println("inside handle revenue ID >>> $id")

        //get data from db with same workRequestId
        context?.let {
            DatabaseOperations(it).getDataByWorkId(id.toString(),workListener)
            //Notification(it).createNotification("DUMMY","This is a dummy notif")
        }
    }

    private fun deleteWork(workModel: WorkModel, delete: Boolean){
        val wrkId = workModel.workId
        if(!TextUtils.isEmpty(wrkId)){
            val wId = UUID.fromString(wrkId)
            WorkManager.getInstance().cancelWorkById(wId)
            if(delete){
                context?.let {
                    DatabaseOperations(it).deleteWork(workModel)
                }
            }
        }
    }

    private val workListener = object: WorkListener {
        override fun worksData(workModel: WorkModel?) {
            println("Inside workListener with workData")
            if(workModel != null){
                println("Inside workData, workModel is not null")
                when(workModel.itemType){
                    Constants.REVENUE -> {
                        println("workModel itemType is revenue, database call to get itemId")
                        context?.let {
                            DatabaseOperations(it).getRevenueById(workModel.itemId,revenueHandler)
                        }
                    }
                    Constants.GOAL -> {
                        context?.let {
                            DatabaseOperations(it).getGoalById(workModel.itemId,workModel,goalHandler)
                        }
                    }
                    else -> {
                        println("WorkModel with itemType ${workModel.itemType} not yet defined ")
                    }
                }
            }else{
                println("workModel is null")
            }
        }
    }

    private val handler = object: WorkListListener {
        override fun workList(workList: List<WorkModel>?) {
            println("inside revenue handler")
            if(!workList.isNullOrEmpty()){
                workList.forEach {
                    context?.let {it1 ->
                        DatabaseOperations(it1).getRevenueById(it.itemId,revenueHandler)
                    }
                }
            }
        }
    }

    private val revenueHandler = object: RevenueListener{
        override fun revenueData(revenue: RevenueModel?) {
            println("revenue data got")
            if(revenue != null){
                if(revenue.activate){
                    var amt = revenue.amt
                    amt += revenue.recurrentAmt
                    println("Revenue data with Original amt ${revenue.amt} and new amt $amt then save to DB")
                    revenue.amt = amt
                    context?.let {
                        DatabaseOperations(it).updateRevenue(revenue)
                        println("update revenue called with id ${revenue.id}")
                        val task = TasksData()
                        task.dateCreated = Date()
                        task.isSuccess = true
                        task.itemId = revenue.id
                        task.itemType = Constants.REVENUE
                        task.status = Constants.SUCCESS
                        DatabaseOperations(it).addTask(task)
                    }
                }else{
                    val task = TasksData()
                    task.dateCreated = Date()
                    task.isSuccess = false
                    task.itemId = revenue.id
                    task.itemType = Constants.REVENUE
                    task.status = Constants.FAILED
                    context?.let {
                        DatabaseOperations(it).addTask(task)
                    }
                    println("Revenue data not activated")
                }
            }else{
                println("Revenue data is null")
            }
        }

    }

    private val goalHandler = object: GoalListener{
        override fun goalData(workModel: WorkModel, goalsModel: GoalsModel?) {
            if(goalsModel != null){
                if(goalsModel.expires >= Date()){
                    println("goal not expired")
                    checkGoalReached(goalsModel,workModel)
                }else{
                    println("goal has expired")
                    deleteWork(workModel,false)
                    context?.let {
                        Notification(it).createNotification("Finny","${goalsModel.goalName} " +
                                "has expired and so has been removed")
                    }
                }
            }else{
                println("goal is null")
            }
        }

        private fun checkGoalReached(goal: GoalsModel, workModel: WorkModel){
            val totalGoalAmt = goal.totalAmt
            val currentGoalAmt = goal.currAmt
            val debitAmt = goal.debitAmt

            val diff = totalGoalAmt - currentGoalAmt
            val addable = (3*debitAmt)/2

            //check if currentAmt is bigger than totalAmt
           if(currentGoalAmt >= totalGoalAmt){
               println("Goal is reached")
               deleteWork(workModel,false)
               context?.let {
                   Notification(it).createNotification("Finny","Congratulations, the goal \"" +
                           "${goal.goalName}\" with total amount \" ${CurrencyFormatter.addSymbol(goal.totalAmt)}" +
                           "\"has been successfully achieved")
               }
           }else{
               println("goal not reached, updating goals")
               updateGoals(goal)
               when {
                   diff <= debitAmt -> {
                       println("goal about to be reached 1")
                       context?.let {
                           Notification(it).createNotification("Finny","Hey There. " +
                                   "The goal with name \"${goal.goalName}\" " +
                                   "and amount \"${CurrencyFormatter.addSymbol(goal.totalAmt)}\" " +
                                   "is about to be achieved. Get ready to CELEBRATE!")
                       }
                   }
                   diff <= addable -> {
                       println("goal about to be reached 2")
                       context?.let {
                           Notification(it).createNotification("Finny","Hey There. " +
                                   "The goal with name \"${goal.goalName}\" " +
                                   "and amount \"${CurrencyFormatter.addSymbol(goal.totalAmt)}\" " +
                                   "is about to be achieved. Get ready to CELEBRATE!")
                       }
                   }
                   else -> {
                       println("goal not reached at all")
                       //TODO goal not yet reached
                   }
               }
           }
        }

        private fun updateGoals(goalsModel: GoalsModel) {
            context?.let {
                DatabaseOperations(it).getRevenueById(goalsModel.sourceId,
                        object : RevenueListener {
                            override fun revenueData(revenue: RevenueModel?) {
                                if (revenue != null) {
                                    println("revenue is valid")
                                    if (revenue.amt >= goalsModel.debitAmt) {

                                        val amt = goalsModel.debitAmt

                                        println("goals beforeCalc >> debit amt: $amt currAmt: " +
                                                "${goalsModel.currAmt} revAmt: ${revenue.amt}")

                                        goalsModel.currAmt += amt
                                        revenue.amt -= amt

                                        println("goals afterCalc >> debit amt: $amt currAmt: " +
                                                "${goalsModel.currAmt} revAmt: ${revenue.amt}")

                                        DatabaseOperations(it).updateGoal(goalsModel)
                                        DatabaseOperations(it).updateRevenue(revenue)

                                        val task = TasksData()
                                        task.dateCreated = Date()
                                        task.isSuccess = true
                                        task.itemId = goalsModel.id
                                        task.itemType = Constants.GOAL
                                        task.status = Constants.SUCCESS
                                        DatabaseOperations(it).addTask(task)

                                        println("revenues and goals updated")

                                    } else {
                                        println("revenue balance is not enough")
                                        val title = "Hey There. The revenue \"${revenue.name}\" is getting " +
                                                "low on balance, therefore all goals relating to this " +
                                                "revenue are suspended until revenue balance increases"
                                        Notification(it).createNotification("Finny",title)

                                        val task = TasksData()
                                        task.dateCreated = Date()
                                        task.isSuccess = false
                                        task.itemId = goalsModel.id
                                        task.itemType = Constants.GOAL
                                        task.status = Constants.FAILED
                                        DatabaseOperations(it).addTask(task)
                                    }
                                } else {
                                    println("Revenue is not valid")
                                    //TODO Revenue is not valid, update DB and user
                                }
                            }
                        }
                )
            }
        }
    }

}