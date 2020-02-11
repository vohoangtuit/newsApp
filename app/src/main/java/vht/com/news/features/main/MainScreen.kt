package vht.com.news.features.main

import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.view_tab_menu_bottom.*
import vht.com.news.R
import vht.com.news.components.ViewPagerAdapter
import vht.com.news.features.general.fragment.GeneralFragment
import vht.com.news.features.preference.PreferenceScreen
import vht.com.news.features.profile.ProfileScreen
import vht.com.news.features.top.TopScreen

class MainScreen : GeneralFragment(){
    override fun getRootLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun onBindView() {
        initTabLayout();
    }

    override fun onBaseResume() {
        showToolbar(true)
    }
    fun initTabLayout(){
        val adapter = ViewPagerAdapter(getActiveActivity().supportFragmentManager)
        adapter.addFragment(TopScreen(), "")
        adapter.addFragment(PreferenceScreen(), "")
        adapter.addFragment(ProfileScreen(), "")
        viewPager.adapter = adapter
        viewPager.setSwipePagingEnabled(false)
        tabs.setupWithViewPager(viewPager)
        viewPager.offscreenPageLimit=3
        updateSatusTab(0)
        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                updateSatusTab(position)
            }
            override fun onPageSelected(position: Int) {

            }

        })
    }
    fun updateSatusTab(position:Int){
        unAllSelecedTab()
        if(position==0){
            act_main_tab_iv_top.isSelected=true
            act_main_tab_tv_top.isSelected=true
        }else if(position==1){
            act_main_tab_iv_preferences.isSelected=true
            act_main_tab_tv_preferences.isSelected=true
        }else if(position==2){
            act_main_tab_iv_profile.isSelected=true
            act_main_tab_tv_profile.isSelected=true
        }
    }
    fun unAllSelecedTab(){
        act_main_tab_iv_top.isSelected=false
        act_main_tab_tv_top.isSelected=false

        act_main_tab_iv_preferences.isSelected=false
        act_main_tab_tv_preferences.isSelected=false

        act_main_tab_iv_profile.isSelected=false
        act_main_tab_tv_profile.isSelected=false
    }
}