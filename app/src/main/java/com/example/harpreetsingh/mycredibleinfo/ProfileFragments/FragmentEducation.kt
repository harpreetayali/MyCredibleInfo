package com.example.harpreetsingh.mycredibleinfo.ProfileFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.harpreetsingh.mycredibleinfo.ProfilesEdits.EducationEdit
import com.example.harpreetsingh.mycredibleinfo.ProfilesEdits.PersonalEdit
import com.example.harpreetsingh.mycredibleinfo.R
import com.example.harpreetsingh.mycredibleinfo.UserDetails.EducationDetails
import com.example.harpreetsingh.mycredibleinfo.UserDetails.PersonDetails
import com.example.harpreetsingh.mycredibleinfo.UserProfile
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.fragment_education_detail.*
import kotlinx.android.synthetic.main.fragment_education_detail.view.*
import kotlinx.android.synthetic.main.fragment_pesonal_detail.*
import org.json.JSONObject

class FragmentEducation:Fragment()
{

        companion object {

        fun newInstance(): FragmentPersonal {
            return FragmentPersonal()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        var userProfile: UserProfile = activity as UserProfile
        fetchEducationDetails(userProfile.session!!.userID,userProfile)
        var view:View = inflater.inflate(R.layout.fragment_education_detail, container,false)

        var fabEdit = view.findViewById<View>(R.id.floatingEducationEdit)
        fabEdit.setOnClickListener {
            var educationEditIntent = Intent(userProfile, EducationEdit::class.java)
            educationEditIntent.putExtra("degree",userProfile.edu_degree_txt.text)
            educationEditIntent.putExtra("college",userProfile.edu_college_txt.text)
            educationEditIntent.putExtra("edu_start",userProfile.edu_start_txt.text)
            educationEditIntent.putExtra("edu_end",userProfile.edu_end_txt.text)
            educationEditIntent.putExtra("edu_location",userProfile.edu_location_txt.text)

            userProfile.finish()
            startActivity(educationEditIntent)
        }

        var addBtn = view.findViewById<Button>(R.id.addEduBtn)
        addBtn.setOnClickListener {
            startActivity(Intent(userProfile,EducationDetails::class.java))
        }

        return view

    }

    fun fetchEducationDetails(userID:String,ctx: Context)
    {
        val baseUrl = "http://139.59.65.145:9090/user/educationdetail/$userID"

        val rqstQue = Volley.newRequestQueue(ctx)
        val objReq = StringRequest(
                Request.Method.GET,
                baseUrl,
                Response.Listener
                {
                    response -> Log.i("JSONRESP",response.toString())
                    val jsonObj = JSONObject(response)
                    val respArray: JSONObject = jsonObj.getJSONObject("data")

                    //AppBarLayout Content
                    //user_name.text = respArray.get("name").toString()

                    //Personal Fragment Content
                    edu_degree_txt.text = respArray.get("degree").toString()
                    edu_start_txt.text = respArray.get("start_year").toString()
                    edu_end_txt.text = respArray.get("end_year").toString()
                    edu_college_txt.text = respArray.get("organisation").toString()
                    edu_location_txt.text = respArray.get("location").toString()



                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }
}