package com.example.muslim_everyday.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.muslim_everyday.R

class SecondTabFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val icFirst = container?.findViewById<ImageView>(R.id.icFirst)
        val icSecond = container?.findViewById<ImageView>(R.id.icSecond)

        icFirst?.setImageResource(R.drawable.ic_arrow_up_transparent)
        icSecond?.setImageResource(R.drawable.ic_arrow_up)

        return inflater.inflate(R.layout.fragment_second_tab, container, false)
    }
}