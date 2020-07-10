package com.wewillapp.masterproject.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wewillapp.masterproject.utils.TokenExpired
import com.wewillapp.masterproject.utils.Utils
import com.wewillapp.masterproject.utils.dialog.DialogPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var mUtils: Utils

    @Inject
    lateinit var mDialogPresenter: DialogPresenter

    @Inject
    lateinit var toolbarViewModel: ToolbarViewModel

    @Inject
    lateinit var mTokenExpiredDisposable: TokenExpired

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // val view = inflater.inflate(layoutRes(), container, false)
        return view
    }
}
