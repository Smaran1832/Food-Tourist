package com.whiteshade.foodtourist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.whiteshade.foodtourist.R

class ForgotPasswordActivity : AppCompatActivity() {
    /*Declaring the different variables used for this activity*/
    lateinit var etForgotMobile: EditText
    lateinit var etForgotPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnForgotNext: Button

    /*Life-cycle method of the activity*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*Linking the view*/
        setContentView(R.layout.activity_forgot_password)

        /*Initialising the views with the ones defined in the XML*/
        etForgotMobile = findViewById(R.id.etMobileNumber)
        etForgotPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnForgotNext = findViewById(R.id.btnChangePassword)

        /*Handling the click on the button using the setOnClickListener method*/
        btnForgotNext.setOnClickListener {

            /*Declaring the intent which sets up the route for the navigation of the activity*/
            val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)

            /*Declaring the bundle object which will carry the data*/
            val bundle = Bundle()

            /*Setting a value data which is activity specific. This will be used to identify from where the data was sent*/
            bundle.putString("data", "forgot")

            /*Putting the values in Bundle*/
            bundle.putString("mobile", etForgotMobile.text.toString())
            bundle.putString("email", etForgotPassword.text.toString())

            /*Putting the Bundle to be shipped with the intent*/
            intent.putExtra("details", bundle)

            /*Starting the new activity by sending the intent in the startActivity method*/
            startActivity(intent)
        }
    }
}