package com.example.muslim_everyday

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.muslim_everyday.fragment.FirstTabFragment
import com.example.muslim_everyday.fragment.SecondTabFragment

class MyViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
//        val icStatus = MainMenu().findViewById<ImageView>(R.id.icStatus)
//        val icHome = MainMenu().findViewById<ImageView>(R.id.icHome)
//        val icBook = MainMenu().findViewById<ImageView>(R.id.icBook)
//
//        when (position) {
//            0 -> {
//                icBook.setImageResource(R.drawable.ic_book_black)
//                icHome.setImageResource(R.drawable.ic_home_black)
//                icStatus.setImageResource(R.drawable.ic_status_blue)
//            }
//            1 -> {
//                icBook.setImageResource(R.drawable.ic_book_black)
//                icHome.setImageResource(R.drawable.ic_home_blue)
//                icStatus.setImageResource(R.drawable.ic_status_black)
//            }
//            2 -> {
//                icBook.setImageResource(R.drawable.ic_book_blue)
//                icHome.setImageResource(R.drawable.ic_home_black)
//                icStatus.setImageResource(R.drawable.ic_status_black)
//            }
//            else -> {
//                icBook.setImageResource(R.drawable.ic_book_black)
//                icHome.setImageResource(R.drawable.ic_home_black)
//                icStatus.setImageResource(R.drawable.ic_status_black)
//            }
//        }

        return when (position) {
            0 -> {
                FirstTabFragment()
            }
            1 -> {
                SecondTabFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}