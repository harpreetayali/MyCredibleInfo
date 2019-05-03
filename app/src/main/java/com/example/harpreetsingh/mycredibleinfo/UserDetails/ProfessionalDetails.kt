package com.example.harpreetsingh.mycredibleinfo.UserDetails

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.harpreetsingh.mycredibleinfo.R
import com.example.harpreetsingh.mycredibleinfo.Session
import com.example.harpreetsingh.mycredibleinfo.UserProfile
import kotlinx.android.synthetic.main.activity_professional_details.*
import org.json.JSONObject

class ProfessionalDetails : AppCompatActivity()
{
    private var session: Session? = null
    var isChecked:Boolean?=null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professional_details)

        session = Session(this@ProfessionalDetails)

        save_btn_professionalDetail.setOnClickListener{
            saveProfessionalDetails()
        }

        checkBox.setOnCheckedChangeListener { compoundButton, b ->

            if (b)
            {
                isChecked = true
                endMonth.visibility = View.GONE
                endYear.visibility = View.GONE
            }
            else
            {
                isChecked = false
                endMonth.visibility = View.VISIBLE
                endYear.visibility = View.VISIBLE
            }
        }
    }

    fun saveProfessionalDetails()
    {

        val baseUrl = "http://139.59.65.145:9090/user/professionaldetail/${session!!.userID}"

        val jsonobj = JSONObject()

        if (this!!.isChecked!!)
        {
            jsonobj.put("end_date","Present")
        }
        else
        {
            jsonobj.put("end_date","${endMonth.selectedItem}-${endYear.selectedItem}")
        }

        jsonobj.put("organisation",organisation.editText!!.text)
        jsonobj.put("designation",designation.editText!!.text)
        jsonobj.put("start_date","${startMonth.selectedItem}-${startYear.selectedItem}")

        //Toast.makeText(this,jsonobj.toString(), Toast.LENGTH_LONG).show()

        val rqstQue = Volley.newRequestQueue(this)
        val objReq = JsonObjectRequest(
                Request.Method.POST,
                baseUrl,
                jsonobj,
                Response.Listener
                {
                    response -> Toast.makeText(this,response.toString(), Toast.LENGTH_LONG).show()
                                if (intent.getStringExtra("Add")=="add")
                                {
                                    startActivity(Intent(this@ProfessionalDetails, UserProfile::class.java))

                                }
                                else {
                                    startActivity(Intent(this@ProfessionalDetails, EducationDetails::class.java))
                                }
                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }
}
