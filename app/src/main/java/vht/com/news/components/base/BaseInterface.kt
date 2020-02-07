package vht.com.news.components.base

import androidx.annotation.AnimRes
import androidx.appcompat.app.AppCompatActivity

interface BaseInterface {
      fun getActiveActivity(): AppCompatActivity
      fun onBindView()
      fun onBaseResume()
      @AnimRes
      fun getEnterAnimation(): Int
}