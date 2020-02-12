package vht.com.news.features.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile.*
import vht.com.news.R
import vht.com.news.components.base.BaseFragment
import vht.com.news.features.general.fragment.GeneralFragment
import vht.com.news.models.UserModel
import vht.com.news.realmDB.RealmDBManager
import vht.com.news.utils.Validations

class ProfileScreen: GeneralFragment(), View.OnClickListener{

    override fun getRootLayoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun onBindView() {
        getProFile()
        clearTextError()
        frag_profile_tv_logout.setOnClickListener(this)
        frag_profile_tv_register.setOnClickListener(this)
        checkKeyBordShowing(frag_profile_view_parent)
    }

    override fun onBaseResume() {
    }

    fun clearTextError(){
        frag_profile_tv_error_username.text=""
        frag_profile_tv_error_email.text=""
        frag_profile_tv_error_password.text=""
        frag_profile_tv_error_confirm_password.text=""

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.frag_profile_tv_register -> {
                if(isValidData()){
                    savaProfile()
                }
            }
            R.id.frag_profile_tv_logout -> {
                handleLogout()
            }

        }
    }
    @SuppressLint("SetTextI18n")
    fun getProFile(){
        val userModel =RealmDBManager.getInstance().getData(UserModel::class.java)
        if(userModel==null){
            frag_profile_view_profile.visibility=View.GONE
            frag_profile_view_register.visibility=View.VISIBLE
        }else{
            frag_profile_view_profile.visibility=View.VISIBLE
            frag_profile_view_register.visibility=View.GONE

            frag_profile_tv_profile.text =userModel.username+"\n"+userModel.email
        }
    }
    fun isValidData(): Boolean{
        clearTextError()
        var validation =true
        if(frag_profile_ed_username.text.isEmpty()){
            frag_profile_tv_error_username.text= getString(R.string.string_error_please_enter_username)
            validation =false
        }
        if(frag_profile_ed_username.text.isNotEmpty()&&frag_profile_ed_username.text.length<10){
            frag_profile_tv_error_username.text= getString(R.string.string_error_username_more_than_character)
            validation =false
        }

        if(frag_profile_ed_email.text.isEmpty()){
            frag_profile_tv_error_email.text= getString(R.string.string_error_please_enter_email)
            validation =false
        }

        if(frag_profile_ed_email.text.isNotEmpty()){
            if(!Validations.isValidEmail(frag_profile_ed_email.text.toString())){
                frag_profile_tv_error_email.text=getString(R.string.string_error_please_email_invalid)
                validation =false
            }
        }

        if(frag_profile_ed_password.text.isEmpty()){
            frag_profile_tv_error_password.text= getString(R.string.string_error_please_enter_password)
            validation =false
        }
        if(frag_profile_ed_password.text.isNotEmpty()){
            if(!Validations.minLengthPassword(frag_profile_ed_password.text.toString())){
                frag_profile_tv_error_password.text=getString(R.string.string_error_password_invalid)
                validation =false
            }
        }
        if(frag_profile_ed_confirm_password.text.isEmpty()){
            frag_profile_tv_error_confirm_password.text= getString(R.string.string_error_please_enter_password)
            validation =false
        }
        if(frag_profile_ed_confirm_password.text.isNotEmpty()){
            if(!Validations.minLengthPassword(frag_profile_ed_password.text.toString())){
                frag_profile_tv_error_confirm_password.text=getString(R.string.string_error_password_invalid)
                validation =false
            }
        }

        if(frag_profile_ed_password.text.isNotEmpty()&&frag_profile_ed_confirm_password.text.isNotEmpty()){
            if(!frag_profile_ed_password.text.toString().equals(frag_profile_ed_confirm_password.text.toString())){
                frag_profile_tv_error_password.text=getString(R.string.string_error_password_not_math)
                frag_profile_tv_error_confirm_password.text=getString(R.string.string_error_password_not_math)
                validation =false
            }
        }
        return validation
    }

    fun savaProfile(){
        val username: String =frag_profile_ed_username.text.toString()
        val email: String =frag_profile_ed_email.text.toString()
        val password: String =frag_profile_ed_password.text.toString()

        val userModel = UserModel(username,email,password)
        RealmDBManager.getInstance().addData(userModel)
        getProFile()
    }

    fun handleLogout(){
        RealmDBManager.getInstance().deleteAllData()
        getProFile()
    }

}