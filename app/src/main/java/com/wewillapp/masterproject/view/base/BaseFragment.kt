package com.wewillapp.masterproject.view.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.wewillapp.masterproject.di.Injectable
import dagger.android.support.DaggerFragment
import javax.inject.Inject


abstract class BaseFragment: DaggerFragment(), Injectable {

    private var activity: AppCompatActivity? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //val view = inflater.inflate(layoutRes(), container, false)
        return view
    }

    fun setTitleToolBar(textViewTitle: TextView, messageTitle:String) {
        textViewTitle.text = messageTitle
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