package vht.com.news.features.general.dialog
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.base_message_dialog.*
import vht.com.news.R

open class BaseMessageDialog(context: AppCompatActivity,title: String,message: String): GeneralDialog(context),View.OnClickListener{
    private var getTitle =title
    private var getMessage =message
    override fun getRootLayoutId(): Int {
        return R.layout.base_message_dialog    }

    override fun initContentView() {
        base_notify_tv_title.text= getTitle
        base_notify_tv_content.text=getMessage
        base_notify_tv_ok.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.base_notify_tv_ok ->{
                dismiss()
            }
        }
    }

}