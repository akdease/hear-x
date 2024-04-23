package com.hearx.app.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.hearx.app.R

class HearXDialog(
    private val heading: String,
    private val description: String,
    private val buttonText: String,
    private val buttonAction: () -> Unit
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.txtHeading).setText(heading)
        view.findViewById<TextView>(R.id.txtDescription).setText(description)
        view.findViewById<Button>(R.id.btnDone).setText(buttonText)
        view.findViewById<Button>(R.id.btnDone).setOnClickListener {
            buttonAction.invoke()
        }

        isCancelable = false
    }
}