package com.wewillapp.masterproject.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wewillapp.masterproject.data.local.Preferences
import com.wewillapp.masterproject.utils.CheckPermission
import com.wewillapp.masterproject.utils.TokenExpired
import com.wewillapp.masterproject.utils.Utils
import com.wewillapp.masterproject.utils.dialog.DialogPresenter
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

abstract class BaseFragment : Fragment() {

    val toolbarViewModel: ToolbarViewModel by viewModel()

    val mUtils: Utils by inject()

    val mPreferences: Preferences by inject()

    val mCheckPermission: CheckPermission by inject { parametersOf(requireActivity()) }

    val mDialogPresenter: DialogPresenter by inject {  parametersOf(requireActivity())}

    val mTokenExpiredDisposable: TokenExpired by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // val view = inflater.inflate(layoutRes(), container, false)
        return view
    }
}
