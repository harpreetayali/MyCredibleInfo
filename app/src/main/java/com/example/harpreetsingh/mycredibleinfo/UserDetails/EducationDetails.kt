package com.example.harpreetsingh.mycredibleinfo.UserDetails

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.harpreetsingh.mycredibleinfo.R
import com.example.harpreetsingh.mycredibleinfo.Session
import com.example.harpreetsingh.mycredibleinfo.UserProfile
import kotlinx.android.synthetic.main.activity_education_details.*
import org.json.JSONObject

class EducationDetails : AppCompatActivity()
{
    private var session: Session? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education_details)

        session = Session(this@EducationDetails)


        save_btn_educationDetail.setOnClickListener{
            saveEducationalDetails()
        }

    }




    fun saveEducationalDetails()
    {

        val baseUrl = "http://139.59.65.145:9090/user/educationdetail/${session!!.userID}"

        val jsonobj = JSONObject()

        jsonobj.put("start_year",startYearEdu.selectedItem)
        jsonobj.put("degree",subject.editText!!.text)
        jsonobj.put("organisation",college_uni.editText!!.text)
        jsonobj.put("location",locationEducation.editText!!.text)
        jsonobj.put("end_year",endYearEdu.selectedItem)


        val rqstQue = Volley.newRequestQueue(this)
        val objReq = JsonObjectRequest(
                Request.Method.POST,
                baseUrl,
                jsonobj,
                Response.Listener
                {
                    response -> Toast.makeText(this,response.toString(), Toast.LENGTH_LONG).show()

                                startActivity(Intent(this@EducationDetails, UserProfile::class.java))
                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }


}
