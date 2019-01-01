package com.example.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.MotionEvent.INVALID_POINTER_ID
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Joystick.newInstance] factory method to
 * create an instance of this fragment.
 */
class Joystick : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_joystick, container, false)
        val knob = view.findViewById<RelativeLayout>(R.id.knob)
        /*knob.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val X = event?.rawX?.toInt()
                val Y = event?.rawY?.toInt()
                var _xDelta = 0
                var _yDelta = 0
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val lParams = view?.layoutParams as FrameLayout.LayoutParams
                        var _xDelta = X?.minus(lParams.leftMargin)
                        var _yDelta = Y?.minus(lParams.topMargin)
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val layoutParams = view?.layoutParams as FrameLayout.LayoutParams
                        layoutParams.leftMargin = X?.minus(_xDelta)!!
                        layoutParams.topMargin = Y?.minus(_yDelta)!!
                        layoutParams.rightMargin = -250
                        layoutParams.bottomMargin = -250
                        view.layoutParams = layoutParams
                    }
                }
                v?.invalidate()
                return v?.onTouchEvent(event) ?: true
            }
        })*/
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Joystick.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Joystick().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


// The ‘active pointer’ is the one currently moving our object.
private var mActivePointerId = INVALID_POINTER_ID

fun onTouchDrag(view: View?, event: MotionEvent): Boolean {
    val X = event.rawX.toInt()
    val Y = event.rawY.toInt()
    var _xDelta = 0
    var _yDelta = 0
    when (event.action and MotionEvent.ACTION_MASK) {
        MotionEvent.ACTION_DOWN -> {
            val lParams = view?.layoutParams as RelativeLayout.LayoutParams
            var _xDelta = X - lParams.leftMargin
            var _yDelta = Y - lParams.topMargin
        }
        MotionEvent.ACTION_UP -> {
        }
        MotionEvent.ACTION_POINTER_DOWN -> {
        }
        MotionEvent.ACTION_POINTER_UP -> {
        }
        MotionEvent.ACTION_MOVE -> {
            val layoutParams = view?.layoutParams as RelativeLayout.LayoutParams
            layoutParams.leftMargin = X - _xDelta
            layoutParams.topMargin = Y - _yDelta
            layoutParams.rightMargin = -250
            layoutParams.bottomMargin = -250
            view.layoutParams = layoutParams
        }
    }
    view?.invalidate()
    return true
}