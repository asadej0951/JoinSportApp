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
import com.wewillapp.masterproject.utils.TokenExpired
import com.wewillapp.masterproject.utils.Utils
import com.wewillapp.masterproject.utils.dialog.DialogPresenter
import com.wewillapp.masterproject.utils.imageManagement.ImageViewUtils
import dagger.android.support.DaggerFragment
import javax.inject.Inject


abstract class BaseFragment: DaggerFragment(), Injectable {

    private var activity: AppCompatActivity? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mUtils: Utils

    @Inject
    lateinit var mDialogPresenter: DialogPresenter

    @Inject
    lateinit var mImageViewUtils: ImageViewUtils

    @Inject
    lateinit var toolbarViewModel: ToolbarViewModel

    @Inject
    lateinit var mTokenExpiredDisposable: TokenExpired


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