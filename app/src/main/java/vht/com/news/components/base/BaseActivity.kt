package vht.com.news.components.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import vht.com.news.base.BaseApplication
import vht.com.news.components.Loading
import vht.com.news.utils.Utils
import vht.com.news.viewModel.BaseViewModel
import java.util.HashMap

abstract class BaseActivity : AppCompatActivity(),BaseInterface{

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(getRootLayoutId())
         BaseApplication.activeActivity = this
         onBindView()
     }
    @SuppressLint("UseSparseArrays")
    internal var containers = HashMap<Int, ArrayList<String>>()
     override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
         super.onCreate(savedInstanceState, persistentState)
         BaseApplication.activeActivity = this
     }

     override fun onResume() {
         super.onResume()
         BaseApplication.activeActivity = this
     }

     override fun getActiveActivity(): AppCompatActivity {
         return BaseApplication.activeActivity!!
     }
    override fun getEnterAnimation(): Int {
        return 0
    }

     override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
         super.setContentView(view, params)
          //onBindView()
     }
     abstract fun getRootLayoutId(): Int

     override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm?.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

     //----------------
     abstract fun getFragmentContainerResId(): Int
     fun addFragment(containerId: Int, fragment: BaseFragment) {

         val manager = supportFragmentManager

         val ft = manager.beginTransaction()

         val tag = fragment.javaClass.canonicalName

         var tags = containers[containerId]

         if (tags == null) {

             tags = ArrayList()

//            containers[containerId] = tags

             containers[containerId] = tags

             tags.add(tag!!)

             ft.setCustomAnimations(fragment.getEnterAnimation(), 0, 0, 0)
             ft.add(containerId, fragment, tag)
             ft.commit()

         } else {

             val top = getTopFragment(containerId)

             top?.onPause()

             tags.add(tag!!)

             val transaction = supportFragmentManager
                 .beginTransaction()

             transaction
                 .setCustomAnimations(
                     fragment.getEnterAnimation(), 0, 0, 0
                 )
                 //.add(containerId, fragment, tag).commit()
                 .add(containerId, fragment, tag).commitAllowingStateLoss()
         }
     }

     fun replaceFragment(containerId: Int, fragment: BaseFragment, clearStack: Boolean) {

         //  if (supportFragmentManager != null) {

         val tag = fragment.javaClass.canonicalName

         val tags = containers[containerId]

         if (tags != null) {

             if (clearStack) {

                 popAllBackStack(containerId)

                 addFragment(containerId, fragment)

             } else {

                 var isExist = false

                 for (i in tags.indices) {

                     val entry = supportFragmentManager.findFragmentByTag(tags[i]) as BaseFragment?

                     if (null != entry && null != entry.javaClass.canonicalName && entry.run {
                             javaClass.canonicalName == tag
                         }
                     ) {

                         isExist = true

                         break
                     }
                 }
                 if (isExist) {

                     if (tags.size > 1) {

                         var top = getTopFragment(containerId)

                         while (!(null != top && null != top.javaClass.canonicalName && top.javaClass.canonicalName == tag)
                         ) {
                             backStack(containerId)
                             top = getTopFragment(containerId)
                         }
                     }
                 } else {
                     if (tags.size > 1) {
                         val top = getTopFragment(containerId)
                         addFragment(containerId, fragment)
                         if (top != null && !Utils.isEmpty(top.javaClass.canonicalName))
                             removeFragment(containerId, top.javaClass.canonicalName!!)
                     } else {
                         popAllBackStack(containerId)
                         addFragment(containerId, fragment)
                     }
                 }
             }
         } else {

             addFragment(containerId, fragment)
         }
         //  }
     }

//    fun getTopFragment(): BaseFragment {
//        return this.getTopFragment(getFragmentContainerResId())!!
//    }

     fun getTopFragment(@IdRes containerId: Int): BaseFragment? {
         try {
             val tags = containers[containerId]
             if (tags != null && tags.size > 0)
                 return supportFragmentManager.findFragmentByTag(tags[tags.size - 1]) as BaseFragment?
         } catch (e: Exception) {
             e.printStackTrace()
         }
         return null
     }

     private fun removeFragment(@IdRes containerId: Int, tag: String) {

         val tags = containers[containerId]

         if (tags != null) {

             var removed = getTopFragment(containerId)

             if (removed != null && null != removed.javaClass.canonicalName && removed.javaClass.canonicalName!! == tag
             ) {

                 backStack(containerId)

             } else {

                 for (i in tags.indices) {

                     removed = supportFragmentManager.findFragmentByTag(tags[i]) as BaseFragment?

                     if (null != removed && null != removed.javaClass.canonicalName && removed.javaClass.canonicalName!! == tag
                     ) {

                         val transaction = supportFragmentManager.beginTransaction().remove(removed)

                         transaction.commit()

                         tags.removeAt(i)

                         break
                     }
                 }
             }
         }
     }

     private fun popAllBackStack(@IdRes containerId: Int) {
         if (supportFragmentManager != null) {
             try {
                 val tags = containers[containerId]
                 if (tags != null) {

                     val transaction = supportFragmentManager
                         .beginTransaction()

                     val fragments = ArrayList<BaseFragment>()

                     for (tag in tags)
                         fragments.add(supportFragmentManager.findFragmentByTag(tag) as BaseFragment)

                     for (fragment in fragments) {
                         transaction.remove(fragment)
                     }

                     transaction.commit()

//                    val removingTagsArray = arrayOfNulls<String>(tags.setSize)

                     tags.toTypedArray()

                     clearStack(containerId)
                 }
             } catch (e: Exception) {
                 e.printStackTrace()
             }
         }
     }

     private fun clearStack(@IdRes containerId: Int) {
         val tags = containers[containerId]
         tags?.clear()
     }

     fun backStack(@IdRes containerId: Int) {
         if (supportFragmentManager != null) {
             val tags = containers[containerId]
             if (tags != null) {
                 if (tags.size <= 1) {
                     finish()
                 } else {
                     val transaction = supportFragmentManager
                         .beginTransaction()
                     val removeFragment = getTopFragment(containerId)
                     if (removeFragment != null) {
                         removeFragment.onPause()
                         tags.removeAt(tags.size - 1)
                         transaction.remove(removeFragment)
                         transaction.commit()
                     }
                     val finishedResult = removeFragment!!.getFinishedResult()
                     val requestCode = removeFragment.getRequestCode()
                     val finishedResultCode = removeFragment.getFinishedResultCode()
                     val fragment = getTopFragment(containerId)
                     if (fragment != null) {
                         if (fragment.view != null) {
                             val view = fragment.view
                             //animateBackIn(view, fragment.getBackInAnimation());
                         }
                         fragment.onResume()
                         if (finishedResult != null) {
                             resumeWithResult(
                                 fragment,
                                 requestCode,
                                 finishedResultCode,
                                 finishedResult
                             )
                         }


                     }
                 }
             }
         }
     }

     private fun resumeWithResult(
         callBackFragment: BaseFragment,
         requestCode: Int,
         finishedResultCode: Int,
         finishedResult: Intent
     ) {
         callBackFragment.onPostResumeWithResult(requestCode, finishedResultCode, finishedResult)
     }

     fun getBackStackCount(): Int {
//        val tags = containers[getFragmentContainerResId()]
//        return tags?.setSize ?: 0
         val tags = containers[getFragmentContainerResId()]
         return tags?.size ?: 0
     }

     fun backStack() {
         backStack(getFragmentContainerResId())
     }

     fun addFragmentForResult(
         @IdRes containerId: Int, requestCode: Int,
         toFragment: BaseFragment
     ) {
         toFragment.setRequestCode(requestCode)
         addFragment(containerId, toFragment)
     }

     fun popFragment(@IdRes containerId: Int) {
         backStack(containerId)
     }

     override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
         //  super.onSaveInstanceState(outState, outPersistentState)
     }

     @SuppressLint("MissingSuperCall")
     override fun onSaveInstanceState(outState: Bundle) {
         // super.onSaveInstanceState(outState)
     }
}