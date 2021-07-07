package com.example.easylife.bottomnavigationanim

import android.animation.Animator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.cardview.widget.CardView
import com.example.easylife.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentOne.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentOne : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var scroll:ScrollView
    lateinit var button:CardView
    var scrollYVal:Int = 0
    var isAnimat:Boolean = false
    val animatUp: Animator.AnimatorListener= object : Animator.AnimatorListener{
        override fun onAnimationStart(animation: Animator?) { isAnimat = true }
        override fun onAnimationEnd(animation: Animator?) {
            if (scrollYVal > 200){
                down()
            }
            isAnimat = false
        }
        override fun onAnimationCancel(animation: Animator?) {}
        override fun onAnimationRepeat(animation: Animator?) {}
    }
    val animatDown: Animator.AnimatorListener= object : Animator.AnimatorListener{
        override fun onAnimationStart(animation: Animator?) { isAnimat = true }
        override fun onAnimationEnd(animation: Animator?) {
            if (scrollYVal < 200){
                up()
            }
            isAnimat = false
        }
        override fun onAnimationCancel(animation: Animator?) {}
        override fun onAnimationRepeat(animation: Animator?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_one, container, false)


        button = view.findViewById(R.id.button)
        scroll = view.findViewById(R.id.scroll)
        button.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val intent= Intent(context,MapScreenActivity::class.java)
                startActivity(intent)
            }
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scroll.setOnScrollChangeListener(object :View.OnScrollChangeListener{
                override fun onScrollChange(
                    v: View?,
                    scrollX: Int,
                    scrollY: Int,
                    oldScrollX: Int,
                    oldScrollY: Int
                ) {
                    scrollYVal = scrollY
                    if(!isAnimat) {
                        if (scrollY > 200) {
                            down()
                        } else {
                            up()
                        }
                    }

                }

            })
        }
        return view
    }

    private fun up() {
        button.animate()
            .setListener(animatUp)
            .setDuration(200)
            .translationY(0f).start()
    }
    private fun down() {
        button.animate()
            .setListener(animatDown)
            .setDuration(200)
            .translationY(500f).start()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentOne.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentOne().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}