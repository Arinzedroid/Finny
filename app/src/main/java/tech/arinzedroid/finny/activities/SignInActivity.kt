package tech.arinzedroid.finny.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_signn.*
import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.interfaces.OnDialogButtonClicked
import tech.arinzedroid.finny.utils.PrefUtils
import tech.arinzedroid.finny.utils.ShowUtils


class SignInActivity : AppCompatActivity() {

    private val SIGNIN :Int = 10
    private lateinit var googleClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var  view: View
    private lateinit var prefutils: PrefUtils


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signn)

        view = findViewById(R.id.login_btn)
        auth = FirebaseAuth.getInstance()
        prefutils = PrefUtils(this)
                //.requestIdToken(getString(R.string.default_web_client_id))
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

                .requestEmail()
                .build()
         googleClient = GoogleSignIn.getClient(this,gso)

        login_btn.setOnClickListener {
            validate()
        }

        skip.setOnClickListener{
            skip()
        }

        google_sign_in.setOnClickListener{
            signIn()
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
       println("firebaseAuthWithGoogle: ${acct.id}!!")

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                       println("signInWithCredential:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                       println("signInWithCredential:failure ${task.exception}")
                        Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                        //updateUI(null)
                    }

                    // ...
                }
    }

    private fun updateUI(user:FirebaseUser?){
        if (user != null) {
            prefutils.anonLogIn(false)
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }

    private fun signIn(){
      val signIntent = googleClient.signInIntent
        startActivityForResult(signIntent, SIGNIN)
    }

    private fun skip(){
        ShowUtils.showDialog(this, resources.getString(R.string.skip_msg),
                "Skip",object : OnDialogButtonClicked {
            override fun onYesClicked() {
                prefutils.anonLogIn(true)
                startActivity(Intent(this@SignInActivity,HomeActivity::class.java))
                finish()
            }

            override fun onNoClicked() {

            }

            override fun onCancelClicked() {

            }

        })
    }

    private fun validate(){
        if(TextUtils.isEmpty(username_et.text)){
            username_et.error = "Email cannot be empty"
            return
        }else{
            username_et.error = ""
        }

        if(TextUtils.isEmpty(password_et.text)){
            password_et.error = "Password cannot be empty"
            return
        }else{
            password_et.error = ""
        }

        process()
    }

    private fun process(){

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == SIGNIN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                account?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                println("Google sign in failed, Error >>> $e")
                Snackbar.make(view, "Google sign in failed.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }


}
