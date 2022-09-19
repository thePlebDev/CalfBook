package com.elliottsoftware.calfbook.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

class SnackBarActions(private val snackBar: Snackbar): View. OnClickListener{

    override fun onClick(v: View){
        snackBar.dismiss()
    }
}