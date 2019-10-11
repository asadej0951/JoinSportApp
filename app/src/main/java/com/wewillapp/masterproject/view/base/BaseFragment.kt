package com.wewillapp.masterproject.view.base

import android.content.Context
import dagger.android.support.DaggerFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.wewillapp.masterproject.di.Injectable


abstract class BaseFragment: DaggerFragment(), Injectable {
    private var activity: AppCompatActivity? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //val view = inflater.inflate(layoutRes(), container, false)
        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AppCompatActivity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    fun getBaseActivity(): AppCompatActivity? {
        return activity
    }

}