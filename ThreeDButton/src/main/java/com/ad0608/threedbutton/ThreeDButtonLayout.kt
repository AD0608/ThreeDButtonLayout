package com.ad0608.threedbutton

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.StateSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.toColorInt

class ThreeDButtonLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var originalElevation = 0f
    private var originalTranslationZ = 0f

    private var normalDrawable: Drawable?
    private var pressedDrawable: Drawable?

    private var verticalPaddingPx: Int = 0

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        verticalPaddingPx = dpToPx(context, 14)

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ThreeDButtonLayout,
            0, 0
        )
        val baseColor = typedArray.getColor(
            R.styleable.ThreeDButtonLayout_tdbl_buttonBaseColor,
            "#C70900".toColorInt()
        )

        val cornerRadius = typedArray.getDimension(
            R.styleable.ThreeDButtonLayout_tdbl_cornerRadius,
            dpToPx(context, 9).toFloat()
        )

        verticalPaddingPx = typedArray.getDimensionPixelSize(
            R.styleable.ThreeDButtonLayout_tdbl_verticalPadding,
            dpToPx(context, 14)
        )

        originalElevation = typedArray.getDimension(
            R.styleable.ThreeDButtonLayout_tdbl_elevation,
            8f
        )

        originalTranslationZ = typedArray.getDimension(
            R.styleable.ThreeDButtonLayout_tdbl_translationZ,
            4f
        )
        typedArray.recycle()

        val stateListDrawable = create3DButtonDrawable(baseColor , cornerRadiusPx = cornerRadius.toInt())
        background = stateListDrawable
        normalDrawable = getDrawableForStateSet(stateListDrawable, StateSet.WILD_CARD)
        pressedDrawable =
            getDrawableForStateSet(stateListDrawable, intArrayOf(android.R.attr.state_pressed))

        background = normalDrawable
        setPadding(0, verticalPaddingPx, 0, verticalPaddingPx)

        elevation = originalElevation
        translationZ = originalTranslationZ

        setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    background = pressedDrawable
                    originalElevation = elevation
                    originalTranslationZ = translationZ
                    isPressed = true
                    animateButtonDown()
                }

                MotionEvent.ACTION_MOVE -> {
                    val inside = event.x in 0f..view.width.toFloat() && event.y in 0f..view.height.toFloat()
                    if (!inside && isPressed) {
                        background = normalDrawable
                        isPressed = false
                        animateButtonUp()
                    } else if (inside && !isPressed) {
                        background = pressedDrawable
                        isPressed = true
                        animateButtonDown()
                    }
                }

                MotionEvent.ACTION_UP -> {
                    if (isPressed) {
                        background = normalDrawable
                        animateButtonUp {
                            isPressed = false
                            performClick()
                        }
                    }
                }

                MotionEvent.ACTION_CANCEL -> {
                    background = normalDrawable
                    animateButtonUp {
                        isPressed = false
                    }
                }
            }
            true
        }
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    private fun create3DButtonDrawable(
        @ColorInt baseColor: Int,
        cornerRadiusPx: Int
    ): StateListDrawable {
        val normalDrawable = createNormalLayerDrawable(baseColor, cornerRadiusPx)
        val pressedDrawable = createPressedDrawable(baseColor, cornerRadiusPx)

        return StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
            addState(StateSet.WILD_CARD, normalDrawable)
        }
    }

    private fun createNormalLayerDrawable(@ColorInt baseColor: Int, cornerRadiusPx: Int): LayerDrawable {
        val shadowColor = ColorUtils.blendARGB(baseColor, Color.BLACK, 0.4f)

        val shadow = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(shadowColor)
            cornerRadius = cornerRadiusPx.toFloat()
        }

        val main = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(baseColor)
            cornerRadius = cornerRadiusPx.toFloat()
        }

        return LayerDrawable(arrayOf(shadow, main)).apply {
            setLayerInset(1, 0, 0, 0, dpToPx(context, 3))
        }
    }

    private fun createPressedDrawable(@ColorInt baseColor: Int, cornerRadiusPx: Int): Drawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(baseColor)
            cornerRadius = cornerRadiusPx.toFloat()
        }
    }

    private fun getDrawableForStateSet(
        drawable: StateListDrawable,
        targetStates: IntArray
    ): Drawable? {
        for (i in 0 until drawable.stateCount) {
            val stateSet = drawable.getStateSet(i)
            if (stateSet.contentEquals(targetStates)) {
                return drawable.getStateDrawable(i)
            }
        }
        return null
    }

    private fun animateButtonDown() {
        val scaleX = ObjectAnimator.ofFloat(this, View.SCALE_X, 1f, 0.97f)
        val scaleY = ObjectAnimator.ofFloat(this, View.SCALE_Y, 1f, 0.97f)
        val elevationAnim = ObjectAnimator.ofFloat(this, "elevation", originalElevation, 2f)
        val translationZAnim =
            ObjectAnimator.ofFloat(this, "translationZ", originalTranslationZ, 0f)

        AnimatorSet().apply {
            playTogether(scaleX, scaleY, elevationAnim, translationZAnim)
            duration = 100
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    private fun animateButtonUp(onEnd: (() -> Unit)? = null) {
        val scaleX = ObjectAnimator.ofFloat(this, View.SCALE_X, scaleX, 1f)
        val scaleY = ObjectAnimator.ofFloat(this, View.SCALE_Y, scaleY, 1f)
        val elevationAnim = ObjectAnimator.ofFloat(this, "elevation", elevation, originalElevation)
        val translationZAnim =
            ObjectAnimator.ofFloat(this, "translationZ", translationZ, originalTranslationZ)

        AnimatorSet().apply {
            playTogether(scaleX, scaleY, elevationAnim, translationZAnim)
            duration = 100
            interpolator = AccelerateDecelerateInterpolator()
            onEnd?.let {
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        it()
                    }
                })
            }
            start()
        }
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
}
