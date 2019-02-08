package tech.arinzedroid.finny.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import tech.arinzedroid.finny.utils.PrefUtils

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this,HomeActivity::class.java))
        finish()
    }

    fun init(){
        //check if anonymously logged in
        if(PrefUtils(this).isAnonLogIn()){
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return
        }
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            startActivity(Intent(this,HomeActivity::class.java))
        }else{
            startActivity(Intent(this,SignInActivity::class.java))
        }
        finish()
    }
}
