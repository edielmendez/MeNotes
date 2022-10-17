package mx.com.emv.menotes.presentation.common

import android.app.AlertDialog
import androidx.fragment.app.Fragment

abstract class MeNotesBaseFragment: Fragment() {
    // - ABSTRACT ATTRIBUTES

    // - ABSTRACT FUNCTIONS
    abstract fun setUpToolBar()
    abstract fun setUpObservers()

    private val loader by lazy {
        LoaderFragment()
    }

    protected fun showDialogError(error: String) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("OK") { dialog, id ->
                    // User clicked OK button
                }
            }
            builder.setTitle("Mensaje")
            builder.setMessage(error)
            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()
    }

    protected fun showLoader(flag: Boolean){
        if (flag){
            if (!loader.isAdded){
                loader.show(parentFragmentManager, LoaderFragment.TAG)
            }
        }else{
            loader?.let {
                it.dismiss()
            }
        }
    }
}