package com.example.harpreetsingh.mycredibleinfo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

import org.json.JSONObject

class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var url = "http://139.59.65.145:9090/user/login"
        var jsonobj = JSONObject()

        login.setOnClickListener {  jsonobj.put("email",email.text)
            jsonobj.put("password",password.text)
            var rqstQue = Volley.newRequestQueue(this)
            var objReq = JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonobj,
                    Response.Listener {
                response -> Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()
            },
                    Response.ErrorListener {
                        error -> Log.i("Error",error.toString())
                    }
            )
            rqstQue.add(objReq) }

    }
}
