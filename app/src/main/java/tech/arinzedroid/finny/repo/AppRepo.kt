package tech.arinzedroid.finny.repo

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.firestore.FirebaseFirestore
import tech.arinzedroid.finny.R

class AppRepo(private val context: Context) {

    val firestoreDB = FirebaseFirestore.getInstance()

    fun login(username: String, password: String) {

    }

    fun googleSignIn(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        val googleClient = GoogleSignIn.getClient(context,gso)
    }
}