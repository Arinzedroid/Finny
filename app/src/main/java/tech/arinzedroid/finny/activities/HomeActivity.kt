package tech.arinzedroid.finny.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.DialogFragment
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
import tech.arinzedroid.finny.dataModels.GoalsModel
import tech.arinzedroid.finny.fragments.AddNewGoalFragment
import tech.arinzedroid.finny.fragments.GoalDetailsFragment
import tech.arinzedroid.finny.viewModels.AppViewModel
import java.util.*
import android.support.design.widget.TabLayout
import android.widget.Toast
import tech.arinzedroid.finny.dataModels.RevenueModel
import tech.arinzedroid.finny.fragments.AddRevenueFragment
import tech.arinzedroid.finny.fragments.RevenuesListFragment
import tech.arinzedroid.finny.utils.Ntification
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        AddNewGoalFragment.OnGoalAddedListener, RevenuesListFragment.OnFragmentInteractionListener,
        AddRevenueFragment.OnFragmentInteractionListener,GoalDetailsFragment.OnFragmentInteractionListener{


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
//        val frag = RevenuesListFragment.newInstance()
//        if(frag.userVisibleHint){
//            val aFrag = AddRevenueFragment.newInstance(null)
//            aFrag.show(supportFragmentManager,"")
//        }
        when(viewpager.currentItem){
            0 -> {
                openAddNewGoalFragment()
            }
            5 -> {
                val aFrag = AddRevenueFragment.newInstance(null)
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

    private fun initq(){
        val goalList : ArrayList<GoalsModel> = ArrayList()
        for (i in 1..10){
           goalList.add(GoalsModel(goalName = "Goal $i",status = true,amt = 20.0 * i,
                   dateCreated = Date(),expires = Date(),id = i.toString()))
        }
        //appViewModel.addGoals(goalList)

    }

    private fun showNotific(){
        val notific = Ntification(this)
        notific.createNotification()
    }

    private fun toast(msg: String){
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show()
    }

    private fun getRandom(): String{
        val ran = Random()
        val num  = ran.nextInt(2)
        return num.toString()
    }

    private fun openAddNewGoalFragment(){
        val frag: DialogFragment = AddNewGoalFragment.newInstance()
        frag.show(supportFragmentManager,"")
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
        menuInflater.inflate(R.menu.home, menu)
        return true
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
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOkClicked(goalName: String) {
        val goal = GoalsModel(goalName,Date(),false,0.00,Date(),"", id = getRandom())
        appViewModel.addGoal(goal)
    }

    override fun onItemClicked(position: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSaveClicked(revenue: RevenueModel) {
        appViewModel.addRevenue(revenue)
    }

    override fun onDeleteClicked(revenue: RevenueModel?) {
        if(revenue != null){
            appViewModel.deleteRevenue(revenue)
            toast("Revenue deleted successfully")
        }

    }

    override fun onSaveGoal(goal: GoalsModel, position: Int) {
        appViewModel.updateGoal(goal,position)
        if(supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack()
        }
    }

    override fun onDeleteGoal(goal: GoalsModel) {
        appViewModel.deleteGoal(goal)
        if(supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack()
        }
    }

}
