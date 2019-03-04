package tech.arinzedroid.finny.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import tech.arinzedroid.finny.dataModels.*
import tech.arinzedroid.finny.database.dao.*

@Database(entities = [GoalsModel::class,RevenueModel::class, ExpenseModel::class, WorkModel::class,
    SavingsModel::class,TasksData::class],version = 1)

abstract class AppDatabase: RoomDatabase() {

    abstract fun goalDao(): GoalDao
    abstract fun revenueDao(): RevenueDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun workDao(): WorkDao
    abstract fun savingsDao(): SavingsDao
    abstract fun tasksDao():TaskDao

    companion object {
        private var Instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if(Instance == null){
                synchronized(AppDatabase::class){
                    Instance = Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,
                            "Finny_DB").build()
                }
            }
            return Instance
        }

        fun destroyInstance(){
            Instance = null
        }
    }
}