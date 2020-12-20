package com.mycodeflow.academyproject

import android.content.Context
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    internal var dataProvider: DataProvider? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appContext = context.applicationContext
        if (appContext is DataProvider){
            dataProvider = appContext
        }
    }

    override fun onDetach() {
        dataProvider = null
        super.onDetach()
    }
}