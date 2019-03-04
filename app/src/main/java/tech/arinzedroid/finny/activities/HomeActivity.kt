package tech.arinzedroid.finny.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import tech.arinzedroid.finny.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import tech.arinzedroid.finny.adapters.SectionPagerAdapter
import tech.arinzedroid.finny.viewModels.AppViewModel
import android.support.design.widget.TabLayout
import android.widget.Toast
import tech.arinzedroid.finny.database.DatabaseOperations
import tech.arinzedroid.finny.fragments.*
import tech.arinzedroid.finny.interfaces.WorkListener
import tech.arinzedroid.finny.tasks.WorkerTask
import tech.arinzedroid.finny.utils.Notification
import java.util.concurrent.TimeUnit
import android.arch.lifecycle.Observer
import android.text.TextUtils
import androidx.work.*
import tech.arinzedroid.finny.dataModels.*
import tech.arinzedroid.finny.utils.Constants
import tech.arinzedroid.finny.utils.DateFormatter
import java.lang.Exception
import java.util.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        AddNewGoalFragment.OnGoalAddedListener, AddRevenueFragment.OnFragmentInteractionListener,
        GoalDetailsFragment.OnFragmentInteractionListener,AddExpenseFragment.OnFragmentInteractionListener,
        UpdateSavingsFragment.FragmentListener,CreateSavingsFragment.FragmentListener{


    private lateinit var appViewModel : AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)

        fab.setOnClickListener {
        openFragment()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        init()
    }

    private fun openFragment(){
        when(viewpager.currentItem){
            0 -> {
                val frag = AddNewGoalFragment.newInstance()
                frag.show(supportFragmentManager,"")
            }
            1 -> {
                val frag = AddExpenseFragment.newInstance()
                frag.show(supportFragmentManager,"")
            }
            2 -> {
                val aFrag = CreateSavingsFragment.newInstance()
                aFrag.show(supportFragmentManager,"dialog")
            }
            3 -> {
                val aFrag = AddRevenueFragment.newInstance()
                aFrag.show(supportFragmentManager,"")
            }
        }
    }

    private fun init(){

        val sectionPagerAdapter = SectionPagerAdapter(supportFragmentManager)
        viewpager.adapter = sectionPagerAdapter
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewpager))

    }

    private fun toast(msg: String){
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { ViewPumpContextWrapper.wrap(it) })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.home, menu)
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_analysis -> {
                // Handle the camera action
            }
            R.id.nav_report -> {

            }
//            R.id.nav_slideshow -> {
//
//            }
//            R.id.nav_manage -> {
//
//            }
//            R.id.nav_share -> {
//
//            }
//            R.id.nav_send -> {
//
//            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateGoal(goalName: String) {
        val goal = GoalsModel(goalName)
        appViewModel.addGoal(goal)
    }

    override fun onUpdateGoal(goal: GoalsModel, position: Int) {
        appViewModel.updateGoal(goal)
        if(supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack()
        }

        createGoalTask(goal)
    }

    override fun onDeleteGoal(goal: GoalsModel) {
        appViewModel.deleteGoal(goal)
        if(supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack()
        }

        DatabaseOperations(this).getWorkData(Constants.GOAL,goal.id,
                object: WorkListener{
                    override fun worksData(workModel: WorkModel?) {
                        if(workModel != null){
                            cancelScheduler(workModel,true)
                        }
                    }
                })
    }


    override fun onCreateRevenue(revenue: RevenueModel) {
        appViewModel.addRevenue(revenue)
        createRevenueTask(revenue)
    }

    override fun onUpdateRevenue(revenue: RevenueModel, position: Int) {
       appViewModel.updateRevenue(revenue)
        DatabaseOperations(this).getWorkData(Constants.REVENUE,revenue.id,
                object: WorkListener{
                    override fun worksData(workModel: WorkModel?) {
                        if(workModel != null){
                            if(revenue.activate){
                                restartScheduler(DateFormatter.getHoursCount("monthly"),revenue.automate,workModel)
                            }else{
                                cancelScheduler(workModel,false)
                            }
                        }
                    }
                })
    }

    override fun onDeleteRevenue(revenue: RevenueModel?, position: Int) {
        if(revenue != null){
            appViewModel.deleteRevenue(revenue)
            toast("Revenue deleted successfully")
            DatabaseOperations(this).getWorkData(Constants.REVENUE,revenue.id,
                    object: WorkListener{
                        override fun worksData(workModel: WorkModel?) {
                            if(workModel != null){
                                cancelScheduler(workModel,true)
                            }
                        }
                    })
        }

    }

    override fun onCreateExpense(expense: ExpenseModel) {
        appViewModel.addExpense(expense)
    }

    override fun onUpdateExpense(expense: ExpenseModel, position: Int) {
        appViewModel.updateExpense(expense)
    }

    override fun onDeleteExpense(expense: ExpenseModel) {
        appViewModel.deleteExpense(expense)
    }

    override fun onAddSavings(amt: Double, savingsModel: SavingsModel) {
       savingsModel.totalAmt += amt
        appViewModel.updateSavings(savingsModel)
    }

    override fun onRemoveSavings(amt: Double, savingsModel: SavingsModel) {
        savingsModel.totalAmt -= amt
        appViewModel.updateSavings(savingsModel)
    }

    override fun onCreateSavings(savingsModel: SavingsModel) {
        appViewModel.addSavings(savingsModel)
    }

    override fun onSaveSavings(savingsModel: SavingsModel) {
        appViewModel.updateSavings(savingsModel)
    }

    override fun onDeleteSavings(savingsModel: SavingsModel) {
        appViewModel.deleteSavings(savingsModel)
    }

    private fun createRevenueTask(revenue: RevenueModel){
        val workRequest: WorkRequest
        val workModel = WorkModel(dueDate = Date())

        if(revenue.automate){
            val interval = DateFormatter.getHoursCount("monthly")
            workModel.isWorkPeriodic = true
            workModel.interval = interval
            workRequest = PeriodicWorkRequest.Builder(WorkerTask::class.java,
                    interval,TimeUnit.HOURS).build()
        }else{
            workModel.isWorkPeriodic = false
            workRequest = OneTimeWorkRequest.Builder(WorkerTask::class.java).build()
        }

        //create a workModel with workRequestId and workId
        workModel.createdAt = Date()
        workModel.dueDate = Date()
        workModel.itemType = Constants.REVENUE
        workModel.itemId = revenue.id
        workModel.workId = workRequest.id.toString()
        DatabaseOperations(this).addWork(workModel)

        scheduleTask(workRequest,Constants.REVENUE,revenue.automate)

    }

    private fun createGoalTask(goal: GoalsModel){
        //fetch workModel from DB
        DatabaseOperations(this).getWorkData(Constants.GOAL,goal.id,
                object: WorkListener{
                    override fun worksData(workModel: WorkModel?) {
                        //get work interval
                        val interval = DateFormatter.getHoursCount(goal.occurrence)

                        if(workModel != null){
                            if(goal.status){
                                restartScheduler(interval,goal.recurrent,workModel)
                            }else{
                                cancelScheduler(workModel,false)
                            }
                        }else{
                            val workRequest: WorkRequest
                            val nWorkModel = WorkModel(dueDate = Date())

                            if(goal.recurrent){
                                nWorkModel.interval = interval
                                nWorkModel.isWorkPeriodic = true
                                workRequest = PeriodicWorkRequest.Builder(WorkerTask::class.java,
                                        interval,TimeUnit.HOURS).build()
                            }else{
                                nWorkModel.isWorkPeriodic = false
                                workRequest = OneTimeWorkRequest.Builder(WorkerTask::class.java).build()
                            }

                            //create a workModel with workRequestId and workId
                            nWorkModel.createdAt = Date()
                            nWorkModel.dueDate = Date()
                            nWorkModel.itemType = Constants.GOAL
                            nWorkModel.itemId = goal.id
                            nWorkModel.workId = workRequest.id.toString()
                            DatabaseOperations(this@HomeActivity).addWork(nWorkModel)

                            scheduleTask(workRequest,Constants.GOAL,goal.recurrent)

                        }
                    }

                })
    }

    private fun scheduleTask(workRequest: WorkRequest, itemType: String, isPeriodic: Boolean){

        val instance = WorkManager.getInstance()

//        try{
//            val workSpec = workRequest.workSpec
//            println("intervalDuration for itemType $itemType is ${workSpec.intervalDuration} in mins")
//        }catch (exception: Exception){
//            exception.printStackTrace()
//        }

        if(isPeriodic){
            instance.enqueueUniquePeriodicWork(workRequest.id.toString(),ExistingPeriodicWorkPolicy.KEEP,
                    workRequest as PeriodicWorkRequest)
        }else{
            instance.enqueueUniqueWork(workRequest.id.toString(),ExistingWorkPolicy.KEEP,
                    workRequest as OneTimeWorkRequest)
        }

        //get liveData of workStatus and update UI appropriately
        instance.getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer {
            //TODO show notification to user
            if(it != null && it.state.isFinished){
                createNotification(itemType)
            }
        })
    }

    private fun createNotification(itemType: String) {
        val title = "Finny"
        val msg: String = when (itemType) {
            Constants.REVENUE -> {
                "Your Revenue has been updated with recent data"
            }
            Constants.GOAL ->{
                "Your Goal has been updated with recent data"
            }
            else -> {
                "unknown updated done"
            }
        }

        Notification(this).createNotification(title,msg)
    }

    private fun cancelScheduler(workData: WorkModel, delete: Boolean){
        val workId = workData.workId
        if(!TextUtils.isEmpty(workId)){
            val uId = UUID.fromString(workId)
            WorkManager.getInstance().cancelWorkById(uId)
            if(delete){
                DatabaseOperations(this).deleteWork(workData)
            }else{
                workData.workId = ""
                DatabaseOperations(this).updateWork(workData)
            }
        }
        //TODO show notification of scheduler cancelled

    }

    private fun restartScheduler(hours: Long, auto: Boolean, workData: WorkModel){
        if(TextUtils.isEmpty(workData.workId)){
            val workRequest: WorkRequest
            val instance = WorkManager.getInstance()
            if(auto){
                workData.isWorkPeriodic = true
                workData.interval = hours
                workRequest = PeriodicWorkRequest.Builder(WorkerTask::class.java,hours,TimeUnit.SECONDS).build()
                instance.enqueueUniquePeriodicWork(workRequest.id.toString(),ExistingPeriodicWorkPolicy.KEEP,
                        workRequest)
            }else{
                workData.isWorkPeriodic = false
                workRequest = OneTimeWorkRequest.Builder(WorkerTask::class.java).build()
                instance.enqueueUniqueWork(workRequest.id.toString(),ExistingWorkPolicy.KEEP,
                        workRequest)
            }

            //update workData
            workData.workId = workRequest.id.toString()
            DatabaseOperations(this).updateWork(workData)

            //get liveData of workStatus and update UI appropriately
            instance.getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer {
                createNotification(workData.itemType)
            })

        }
        else{
            if(!workData.isWorkPeriodic){

                workData.isWorkPeriodic = true
                workData.interval = hours
                val workRequest = PeriodicWorkRequest.Builder(WorkerTask::class.java,hours,TimeUnit.HOURS).build()

                //update workData
                workData.workId = workRequest.id.toString()
                DatabaseOperations(this).updateWork(workData)

                val instance = WorkManager.getInstance()

                //enqueue the request
                instance.enqueueUniquePeriodicWork(workData.workId, ExistingPeriodicWorkPolicy.KEEP,workRequest)

                //get liveData of workStatus and update UI appropriately
                instance.getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer {
                    createNotification(workData.itemType)
                })
            }
        }
    }

}
