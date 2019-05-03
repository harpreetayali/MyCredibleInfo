package com.example.harpreetsingh.mycredibleinfo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.harpreetsingh.mycredibleinfo.UserDetails.PersonDetails
import kotlinx.android.synthetic.main.activity_login.*

import org.json.JSONObject


class Login : AppCompatActivity()
{
    private var session: Session? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        session = Session(this@Login)
        if (session!!.loggedIn()) {

            startActivity(Intent(this@Login, UserProfile::class.java))
            Toast.makeText(this,session!!.userID+"::"+session!!.professionID+"::"+session!!.educationID,Toast.LENGTH_LONG).show()
            this@Login.finish()
        }


        var baseUrl = "http://139.59.65.145:9090"


        //Login Button
        login.setOnClickListener { login(baseUrl) }

        //Sign Up Button
        signup.setOnClickListener{ signUp(baseUrl)}

    }



    fun login(url:String)
    {
        var jsonobj = JSONObject()
        jsonobj.put("email",email.text)
        jsonobj.put("password",password.text)
        var rqstQue = Volley.newRequestQueue(this)
        var objReq = JsonObjectRequest(
                Request.Method.POST,
                "$url/user/login",
                jsonobj,
                Response.Listener
                {
                    response -> var respArray:JSONObject = response.getJSONObject("data")
                                var respEmail = respArray.get("email").toString()
                                var respUid = respArray.get("id").toString()


                                session!!.setLoggedIn(true)
                                session!!.userID = respUid
                                session!!.userEmail = respEmail
                        this.finish()
                    startActivity(Intent(this@Login,UserProfile::class.java))
                                Toast.makeText(this,respEmail+respUid,Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }

    fun signUp(url: String)
    {
        var jsonobj = JSONObject()
        jsonobj.put("email",email.text)
        jsonobj.put("password",password.text)
        var rqstQue = Volley.newRequestQueue(this)
        var objReq = JsonObjectRequest(
                Request.Method.POST,
                "$url/user/signup",
                jsonobj,
                Response.Listener {
                    response -> Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()
                    var respArray:JSONObject = response.getJSONObject("data")
                    var respEmail = respArray.get("email").toString()
                    var respUid = respArray.get("id").toString()


                    session!!.setLoggedIn(true)
                    session!!.userID = respUid
                    session!!.userEmail = respEmail
                    startActivity(Intent(this@Login, PersonDetails::class.java))
                },
                Response.ErrorListener {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }
}
