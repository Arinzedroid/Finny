package tech.arinzedroid.finny.tasks

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerTask(context: Context, params: WorkerParameters):
        Worker(context,params){

    override fun doWork(): Result {
        return try{
            println("inside revenue workManager ID >>> ${this.id}")

            //initiate task with the id of the workRequest that stated this work
            AllGenericTasks().handleWorkerTask(this.id)
            Result.success()
        }catch (ex: Exception){
            Result.failure()
        }
    }

}