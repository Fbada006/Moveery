package com.droidafricana.moveery.utils

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.droidafricana.moveery.R
import kotlin.math.atan
import kotlin.math.pow
import kotlin.math.sqrt

/**Class to set the custom view for the movie rating*/
class RatingCustomView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        const val MIN_VALUE = 0
        const val MAX_VALUE = 100
    }

    private val center = PointF()
    private val circleRect = RectF()
    private val segment = Path()
    private val strokePaint = Paint()
    private val fillPaint = Paint()
    private var radius = 0
    private var fillColor = 0
    private var strokeColor = 0
    private var strokeWidth = 0f
    private var value = 0

    constructor(context: Context) : this(context, null)

    /**Set the color of the outer stroke*/
    fun setStrokeColor(strokeCol: Int) {
        strokeColor = strokeCol
        strokePaint.color = strokeCol
        invalidate()
    }

    /**What colour should fill the view depends on the rating*/
    fun setFillColor(fillColor: Int) {
        this.fillColor = fillColor
        fillPaint.color = fillColor
        invalidate()
    }

    /**Set the value that is actually supposed to fill the view*/
    fun setValue(value: Int) {
        adjustValue(value)
        setPaths()
        invalidate()
    }

    /**Adjust the value according to the value given to make sure it is within the parameters
     * of [MAX_VALUE] and [MIN_VALUE]*/
    private fun adjustValue(value: Int) {
        this.value = MAX_VALUE.coerceAtMost(MIN_VALUE.coerceAtLeast(value))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        center.x = width / 2.toFloat()
        center.y = height / 2.toFloat()
        radius = width.coerceAtMost(height) / 2 - strokeWidth.toInt()
        circleRect[center.x - radius, center.y - radius, center.x + radius] = center.y + radius
        setPaths()
    }

    private fun setPaths() {
        val y = center.y + radius - (2 * radius * value / 100 - 1)
        val x = center.x - sqrt(
            radius.toDouble().pow(2.0) - (y - center.y.toDouble()).pow(2.0)
        ).toFloat()
        val angle =
            Math.toDegrees(atan((center.y - y) / (x - center.x).toDouble())).toFloat()
        val startAngle = 180 - angle
        val sweepAngle = 2 * angle - 180
        segment.rewind()
        segment.addArc(circleRect, startAngle, sweepAngle)
        segment.close()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(segment, fillPaint)
        canvas.drawCircle(center.x, center.y, radius.toFloat(), strokePaint)
    }

    init {
        val a: TypedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RatingCustomView,
            0, 0
        )

        try {
            fillColor = a.getColor(R.styleable.RatingCustomView_fillColor, Color.WHITE)
            strokeColor = a.getColor(R.styleable.RatingCustomView_strokeColor, Color.BLACK)
            strokeWidth = a.getFloat(R.styleable.RatingCustomView_strokeWidth, 1f)
            value = a.getInteger(R.styleable.RatingCustomView_value, 0)
            adjustValue(value)
        } finally {
            a.recycle()
        }

        fillPaint.color = fillColor
        strokePaint.apply {
            color = strokeColor
            strokeWidth = strokeWidth
            style = Paint.Style.STROKE
        }
    }
}