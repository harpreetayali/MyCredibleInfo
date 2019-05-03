package com.example.harpreetsingh.mycredibleinfo.UserDetails

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_person_details.*
import android.content.Intent
import android.graphics.Bitmap
import android.widget.Toast
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.graphics.Bitmap.CompressFormat
import android.support.design.widget.TextInputLayout
import android.util.Base64
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.harpreetsingh.mycredibleinfo.R
import com.example.harpreetsingh.mycredibleinfo.Session
import org.json.JSONObject
import java.io.ByteArrayOutputStream


class PersonDetails : AppCompatActivity()
{

    private var session: Session? = null
    private var fullName: TextInputLayout? = null
    private var email: TextInputLayout? = null
    private var mobile: TextInputLayout? = null
    private var location: TextInputLayout? = null
    private var links: TextInputLayout? = null
    private var skills: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)

        session = Session(this@PersonDetails)

        fullName = findViewById(R.id.full_name)
        email = findViewById(R.id.email)
        mobile = findViewById(R.id.mobile)
        location = findViewById(R.id.location)
        links = findViewById(R.id.links)
        skills = findViewById(R.id.skills)


        profilePic.setOnClickListener{
            val galeryIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeryIntent, Context.CONTEXT_INCLUDE_CODE)
        }

        save_btn_personalDetail.setOnClickListener{

            savePersonalDetails()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Context.CONTEXT_INCLUDE_CODE && resultCode == Activity.RESULT_OK && null != data)
        {
            val selectedImage = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

            val selectedCursor = contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
            selectedCursor!!.moveToFirst()

            val columnIndex = selectedCursor.getColumnIndex(filePathColumn[0])
            val picturePath = selectedCursor.getString(columnIndex)
            selectedCursor.close()

            profilePic.setImageBitmap(BitmapFactory.decodeFile(picturePath))

            var bitmap:Bitmap = BitmapFactory.decodeFile(picturePath)


            uploadImage( convertImage(bitmap))
            Toast.makeText(applicationContext, picturePath, Toast.LENGTH_SHORT).show()

        }

    }

    fun convertImage(bitmap:Bitmap):String
    {
        val stream = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.JPEG, 70, stream)
        val byteFormat = stream.toByteArray()
        // get the base 64 string
        return Base64.encodeToString(byteFormat,Base64.DEFAULT)
    }
    fun uploadImage(image:String)
    {
        val baseUrl = "http://139.59.65.145:9090/user/personaldetail/pp/post"
        val jsonobj = JSONObject()
        jsonobj.put("photo",image)
        jsonobj.put("uid",session!!.userID)
        val rqstQue = Volley.newRequestQueue(this)
        val objReq = JsonObjectRequest(
                Request.Method.POST,
                baseUrl,
                jsonobj,
                Response.Listener
                {
                    response -> Toast.makeText(this,response.getString("status_message"),Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }

    fun savePersonalDetails()
    {

        val baseUrl = "http://139.59.65.145:9090/user/personaldetail/${session!!.userID}"

        val jsonobj =JSONObject()
        jsonobj.put("skills",skills!!.editText!!.text)
        jsonobj.put("mobile_no",mobile!!.editText!!.text)
        jsonobj.put("name",fullName!!.editText!!.text)
        jsonobj.put("links",links!!.editText!!.text)
        jsonobj.put("location",location!!.editText!!.text)
        jsonobj.put("email",email!!.editText!!.text)

        val rqstQue = Volley.newRequestQueue(this)
        val objReq = JsonObjectRequest(
                Request.Method.POST,
                baseUrl,
                jsonobj,
                Response.Listener
                {
                    response -> Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()
                                startActivity(Intent(this@PersonDetails, ProfessionalDetails::class.java))
                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }

}
