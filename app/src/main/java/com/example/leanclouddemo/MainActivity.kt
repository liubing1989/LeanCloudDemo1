package com.example.leanclouddemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cn.leancloud.LCUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var model: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model =ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application)).get(MyViewModel::class.java)
        setContentView(R.layout.activity_main)
        floatingActionButton.setOnClickListener {
            val v = LayoutInflater.from(this).inflate(R.layout.new_word_dialog, null)
            AlertDialog.Builder(this)
                .setTitle("添加")
                .setView(v)
                .setPositiveButton(
                    "确定"
                ) { dialog, _ ->
                    val newWord =
                        v.findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
                    model.addWord(newWord)
                }
                .setNegativeButton(
                    "取消"
                ) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()

        }
        val myAdapter =MyAdapter()
        recyclew.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity,DividerItemDecoration.VERTICAL))
            adapter = myAdapter
        }
        model.dataListLive.observe(this, Observer {
            myAdapter.updateDataList(it)
            myAdapter.notifyDataSetChanged()
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuLogOut) {
            LCUser.logOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}