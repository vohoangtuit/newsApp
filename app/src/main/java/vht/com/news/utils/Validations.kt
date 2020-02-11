package vht.com.news.utils
import androidx.annotation.NonNull

object Validations {
    fun minLengthPassword(@NonNull name : String) : Boolean{
        if (name.length>5){
            return true
        }
        return false
    }

    fun isValidEmail(@NonNull value : String) : Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }

}

