package com.wewillapp.masterproject.utils.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.wewillapp.masterproject.R
import java.util.ArrayList

class DialogScrollView : ScrollView {

    companion object {
        val TAG = DialogScrollView::class.java.simpleName
        const val OFF_SET_DEFAULT = 1
        private const val SCROLL_DIRECTION_UP = 0
        private const val SCROLL_DIRECTION_DOWN = 1
    }

    private val fontSize = 16

    private val fontSizeSelect = 20

    private var views: LinearLayout? = null

    private var items: MutableList<String>? = null

    var offset = OFF_SET_DEFAULT

    private var displayItemCount: Int = 0

    private var selectedIndex = 1

    private var initialY: Int = 0

    private lateinit var scrollerTask: Runnable

    private var newCheck = 50

    private var itemHeight = 0

    private var selectedAreaBorder: IntArray? = null

    private var scrollDirection = -1

    internal var paint: Paint? = null

    internal var viewWidth: Int = 0

    val seletedItem: String
        get() = items!![selectedIndex]

    val seletedIndex: Int
        get() = selectedIndex - offset

    var onWheelViewListener: OnWheelViewListener? = null

    class OnWheelViewListener {
        internal fun onSelected(selectedIndex: Int, item: String) {}
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    private fun getItems(): List<String>? {
        return items
    }

    fun setItems(list: List<String>) {
        if (null == items) {
            items = ArrayList()
        }
        items!!.clear()
        items!!.addAll(list)

        // 前面和后面补全
        for (i in 0 until offset) {
            items!!.add(0, "")
            items!!.add("")
        }

        initData()

    }


    private fun init(context: Context) {
        this.isVerticalScrollBarEnabled = false

        views = LinearLayout(context)
        views!!.orientation = LinearLayout.VERTICAL
        this.addView(views)

        scrollerTask = Runnable {
            val newY = scrollY
            if (initialY - newY == 0) { // stopped
                val remainder = initialY % itemHeight
                val divided = initialY / itemHeight
                //                    Log.d(TAG, "initialY: " + initialY);
                //                    Log.d(TAG, "remainder: " + remainder + ", divided: " + divided);
                if (remainder == 0) {
                    selectedIndex = divided + offset

                    onSeletedCallBack()
                } else {
                    if (remainder > itemHeight / 2) {
                        this@DialogScrollView.post {
                            this@DialogScrollView.smoothScrollTo(0, initialY - remainder + itemHeight)
                            selectedIndex = divided + offset + 1
                            onSeletedCallBack()
                        }
                    } else {
                        this@DialogScrollView.post {
                            this@DialogScrollView.smoothScrollTo(0, initialY - remainder)
                            selectedIndex = divided + offset
                            onSeletedCallBack()
                        }
                    }


                }


            } else {
                initialY = scrollY
                this@DialogScrollView.postDelayed(scrollerTask, newCheck.toLong())
            }
        }


    }

    fun startScrollerTask() {

        initialY = scrollY
        this.postDelayed(scrollerTask, newCheck.toLong())
    }

    @SuppressLint("NewApi")
    private fun initData() {
        displayItemCount = offset * 2 + 1

        for (item in items!!) {
            views!!.addView(createView(item))
        }

        refreshItemView(0)
    }

    private fun createView(item: String): TextView {
        val tv = TextView(context)
        tv.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        tv.isSingleLine = true

        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
        tv.text = item
        tv.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
        val padding = dip2px(12f)
        tv.setPadding(padding, padding, padding, 42)
        if (0 == itemHeight) {
            itemHeight = getViewMeasuredHeight(tv)
            Log.d(TAG, "itemHeight: $itemHeight")
            views!!.layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                itemHeight * displayItemCount
            )
            val lp = this.layoutParams as LinearLayout.LayoutParams
            this.layoutParams = LinearLayout.LayoutParams(lp.width, itemHeight * displayItemCount)
        }
        return tv
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        refreshItemView(t)

        if (t > oldt) {
            //            Log.d(TAG, "向下滚动");
            scrollDirection = SCROLL_DIRECTION_DOWN
        } else {
            //            Log.d(TAG, "向上滚动");
            scrollDirection = SCROLL_DIRECTION_UP

        }


    }

    private fun refreshItemView(y: Int) {
        var position = y / itemHeight + offset
        val remainder = y % itemHeight
        val divided = y / itemHeight

        if (remainder == 0) {
            position = divided + offset
        } else {
            if (remainder > itemHeight / 2) {
                position = divided + offset + 1
            }

        }

        val childSize = views!!.childCount
        for (i in 0 until childSize) {
            val itemView = views!!.getChildAt(i) as TextView
            itemView.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
            if (null == itemView) {
                return
            }
            val face = Typeface.createFromAsset(
                context!!.assets,
                "fonts/sf_pro_medium.otf"
            )
            itemView.typeface = face

            if (position == i) {
                itemView.textSize = fontSizeSelect.toFloat()
                itemView.setTextColor(resources.getColor(R.color.bgBtnRed)) // item text color select
            } else {
                itemView.textSize = fontSize.toFloat()
                itemView.setTextColor(resources.getColor(R.color.colorPrimary))
            }
            itemView.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
        }
    }

    private fun obtainSelectedAreaBorder(): IntArray {
        if (null == selectedAreaBorder) {
            selectedAreaBorder = IntArray(2)
            selectedAreaBorder!![0] = itemHeight * offset
            selectedAreaBorder!![1] = itemHeight * (offset + 1)
        }
        return selectedAreaBorder as IntArray
    }

    override fun setBackgroundDrawable(background: Drawable?) {
        var background = background

        if (viewWidth == 0) {
            viewWidth = (context as Activity).windowManager.defaultDisplay.width
            Log.d(TAG, "viewWidth: $viewWidth")
        }

        if (null == paint) {
            paint = Paint()
            paint!!.color = resources.getColor(R.color.transparent)
            paint!!.strokeWidth = dip2px(1f).toFloat()


        }

        background = object : Drawable() {
            override fun draw(canvas: Canvas) {
                canvas.drawLine(
                    (viewWidth * 1 / 6).toFloat(), obtainSelectedAreaBorder()[0].toFloat(),
                    (viewWidth * 5 / 6).toFloat(), obtainSelectedAreaBorder()[0].toFloat(), paint!!
                )

                canvas.drawLine(
                    (viewWidth * 1 / 6).toFloat(), obtainSelectedAreaBorder()[1].toFloat(),
                    (viewWidth * 5 / 6).toFloat(), obtainSelectedAreaBorder()[1].toFloat(), paint!!
                )
            }

            override fun setAlpha(alpha: Int) {

            }

            override fun setColorFilter(cf: ColorFilter?) {

            }

            @SuppressLint("WrongConstant")
            override fun getOpacity(): Int {
                return 0
            }
        }


        super.setBackgroundDrawable(background)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.d(TAG, "w: $w, h: $h, oldw: $oldw, oldh: $oldh")
        viewWidth = w
        setBackgroundDrawable(null)
    }

    /**
     * 选中回调
     */
    private fun onSeletedCallBack() {
        if (null != onWheelViewListener) {
            onWheelViewListener!!.onSelected(selectedIndex, items!![selectedIndex])
        }

    }

    fun setSeletion(position: Int) {
        selectedIndex = position + offset
        this.post { this@DialogScrollView.smoothScrollTo(0, position * itemHeight) }


    }


    override fun fling(velocityY: Int) {
        super.fling(velocityY / 3)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP) {

            startScrollerTask()
        }
        return super.onTouchEvent(ev)
    }

    private fun dip2px(dpValue: Float): Int {
        val scale = context!!.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    private fun getViewMeasuredHeight(view: View): Int {
        val width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val expandSpec =
            View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2, View.MeasureSpec.AT_MOST)
        view.measure(width, expandSpec)
        return view.measuredHeight
    }
}