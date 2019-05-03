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
import com.example.harpreetsingh.mycredibleinfo.ProfilesEdits.ProfessionalEdit
import com.example.harpreetsingh.mycredibleinfo.R
import com.example.harpreetsingh.mycredibleinfo.UserDetails.EducationDetails
import com.example.harpreetsingh.mycredibleinfo.UserDetails.ProfessionalDetails
import com.example.harpreetsingh.mycredibleinfo.UserProfile
import kotlinx.android.synthetic.main.activity_professional_edit.*
import kotlinx.android.synthetic.main.fragment_education_detail.*
import kotlinx.android.synthetic.main.fragment_professional_detail.*
import org.json.JSONObject

class FragmentProfessional:Fragment()
{
        companion object {

        fun newInstance(): FragmentPersonal {
            return FragmentPersonal()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        var userProfile: UserProfile = activity as UserProfile
        fetchProfessionalDetails(userProfile.session!!.userID,userProfile)
         var view:View = inflater.inflate(R.layout.fragment_professional_detail, container,false)

        var fabEdit = view.findViewById<View>(R.id.floatingProfessionalEdit)
        fabEdit.setOnClickListener {
            var professionalEditIntent = Intent(userProfile, ProfessionalEdit::class.java)
            professionalEditIntent.putExtra("organisation",userProfile.pro_organisation.text)
            professionalEditIntent.putExtra("designation",userProfile.pro_designation.text)
            professionalEditIntent.putExtra("pro_start",userProfile.pro_start_date.text)
            professionalEditIntent.putExtra("pro_end",userProfile.pro_end_date.text)


            userProfile.finish()
            startActivity(professionalEditIntent)
        }

        var addBtn = view.findViewById<Button>(R.id.addProBtn)
        addBtn.setOnClickListener {
            startActivity(Intent(userProfile, ProfessionalDetails::class.java).putExtra("Add","add"))
        }
        return view
    }

    fun fetchProfessionalDetails(userID:String,ctx: Context)
    {
        val baseUrl = "http://139.59.65.145:9090/user/professionaldetail/$userID"

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
                    pro_organisation.text = respArray.get("organisation").toString()
                    pro_designation.text = respArray.get("designation").toString()
                    pro_start_date.text = respArray.get("start_date").toString()
                    pro_end_date.text = respArray.get("end_date").toString()



                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }
}