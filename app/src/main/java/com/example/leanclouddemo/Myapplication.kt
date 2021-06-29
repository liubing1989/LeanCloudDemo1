package com.example.leanclouddemo

import android.app.Application
import cn.leancloud.LCObject
import cn.leancloud.LeanCloud


class Myapplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LeanCloud.initialize(this, "InDhC7N6EhHTUHhmYEgPsDYX-gzGzoHsz", "VPuinsheicVpDzyapck65LBH",
            "https://indhc7n6.lc-cn-n1-shared.com")
//        val testObject = LCObject("TestObject")
//        testObject.put("words", "Hello world!")
//        testObject.saveInBackground().blockingSubscribe()
    }
}