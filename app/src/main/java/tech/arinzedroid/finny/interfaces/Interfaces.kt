package tech.arinzedroid.finny.interfaces

import android.arch.lifecycle.LiveData
import tech.arinzedroid.finny.dataModels.*

interface GoalsListListener{
    fun goalsLiveData(goalsList: LiveData<List<GoalsModel>>?)
}

interface GoalListener{
    fun goalData(workModel: WorkModel, goalsModel: GoalsModel?)
}

interface RevenuesListListener{
    fun revenueLiveData(revenues: LiveData<List<RevenueModel>>?)
}

interface RevenueListener{
    fun revenueData(revenue: RevenueModel?)
}

interface ExpenseListListener{
    fun expenseLiveData(expenseList: LiveData<List<ExpenseModel>>?)
}

interface ExpenseListener{
    fun expensesData(expense: ExpenseModel?)
}

interface WorkListListener{
    fun workList(workList: List<WorkModel>?)
}

interface WorkListener{
    fun worksData(workModel: WorkModel?)
}

interface OnItemClickInterface {
    fun onClick(position:Int)
}

interface OnSavingsListener{
    fun onAdd(position: Int)
    fun onRemove(position: Int)
    fun onEdit(position: Int)
}

interface OnDialogButtonClicked {
    fun onYesClicked()
    fun onNoClicked()
    fun onCancelClicked()

}

interface TasksListListerner{
    fun onTasksList(tasksList: LiveData<List<TasksData>>?)
}

interface TaskListener{
    fun onTask(task: TasksData?)
}