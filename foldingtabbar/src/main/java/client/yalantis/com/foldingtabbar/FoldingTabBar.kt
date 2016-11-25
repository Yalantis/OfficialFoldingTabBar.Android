package client.yalantis.com.foldingtabbar

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.MenuRes
import android.support.v7.view.SupportMenuInflater
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuItemImpl
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import android.widget.LinearLayout


/**
 * Created by andrewkhristyan on 11/8/16.
 */
class FoldingTabBar : LinearLayout {

    private val ANIMATION_DURATION = 500L
    private val START_DELAY = 150L
    private val MAIN_ROTATION_START = 0f
    private val MAIN_ROTATION_END = 405f
    private val ITEM_ROTATION_START = 180f
    private val ITEM_ROTATION_END = 360f
    private val ROLL_UP_ROTATION_START = -45f
    private val ROLL_UP_ROTATION_END = 360f

    var onFoldingItemClickListener: OnFoldingItemSelectedListener? = null
    var onMainButtonClickListener: OnMainButtonClickedListener? = null

    private lateinit var mData: List<SelectedMenuItem>

    private var mExpandingSet: AnimatorSet = AnimatorSet()
    private var mRollupSet: AnimatorSet = AnimatorSet()
    private var isAnimating: Boolean = false

    private var mMenu: MenuBuilder

    private var mSize: Int = 0
    private var indexCounter = 0
    private var mainImageView: ImageView = ImageView(context)
    private var selectedImageView: ImageView? = null
    private var selectedIndex: Int = 0

    private var itemsPadding: Int = 0
    private var drawableResource: Int = 0
    private var selectionColor: Int = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleRes: Int)
    : super(context, attrs, defStyleRes) {
        mMenu = MenuBuilder(context)
        gravity = Gravity.CENTER

        if (background == null) {
            setBackgroundResource(R.drawable.background_tabbar)
        }
        val a: TypedArray = initAttrs(attrs, defStyleRes)

        mSize = getSizeDimension()
        initViewTreeObserver(a)
    }

    /**
     * Initializing attributes
     */
    private fun initAttrs(attrs: AttributeSet?, defStyleRes: Int) =
            context.obtainStyledAttributes(attrs,
                    R.styleable.FoldingTabBar, 0,
                    defStyleRes)

    /**
     * Here is size of our FoldingTabBar. Simple
     */
    private fun getSizeDimension(): Int = resources.getDimensionPixelSize(R.dimen.ftb_size_normal)

    /**
     * This is the padding for menu items
     */
    private fun getItemsPadding(): Int = resources.getDimensionPixelSize(R.dimen.ftb_item_padding)

    /**
     * When folding tab bar pre-draws we should initialize
     * inflate our menu, and also add menu items, into the
     * FoldingTabBar, also here we are initializing animators
     * and animation sets
     */
    private fun initViewTreeObserver(a: TypedArray) {
        viewTreeObserver.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                viewTreeObserver.removeOnPreDrawListener(this)
                isAnimating = true
                initAttributesValues(a)
                initExpandAnimators()
                initRollUpAnimators()
                select(selectedIndex)
                return true
            }
        })
    }

    /**
     * Here we are initializing default values
     * Also here we are binding new attributes into this values
     *
     * @param a - incoming typed array with attributes values
     */
    private fun initAttributesValues(a: TypedArray) {
        drawableResource = R.drawable.ic_action_plus
        itemsPadding = getItemsPadding()
        selectionColor = R.color.ftb_selected_dot_color
        if (a.hasValue(R.styleable.FoldingTabBar_mainImage)) {
            drawableResource = a.getResourceId(R.styleable.FoldingTabBar_mainImage, 0)
        }
        if (a.hasValue(R.styleable.FoldingTabBar_itemPadding)) {
            itemsPadding = a.getDimensionPixelSize(R.styleable.FoldingTabBar_itemPadding, 0)
        }
        if (a.hasValue(R.styleable.FoldingTabBar_selectionColor)) {
            selectionColor = a.getResourceId(R.styleable.FoldingTabBar_selectionColor, 0)
        }
        if (a.hasValue(R.styleable.FoldingTabBar_menu)) {
            inflateMenu(a.getResourceId(R.styleable.FoldingTabBar_menu, 0))
        }
    }

    /**
     * Expand animation. Whole animators
     */
    private fun initExpandAnimators() {
        mExpandingSet.duration = ANIMATION_DURATION

        val destWidth = childCount.times(mSize)

        val rotationSet = AnimatorSet()
        val scalingSet = AnimatorSet()

        val scalingAnimator = ValueAnimator.ofInt(mSize, destWidth).apply {
            addUpdateListener(scaleAnimator)
            addListener(rollUpListener)
        }

        val rotationAnimator = ValueAnimator.ofFloat(MAIN_ROTATION_START, MAIN_ROTATION_END).apply {
            addUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Float
                mainImageView.rotation = value
            }
        }

        mData.forEach { item ->
            ValueAnimator.ofFloat(ITEM_ROTATION_START, ITEM_ROTATION_END).apply {
                addUpdateListener {
                    val fraction = it.animatedFraction
                    item.scaleX = fraction
                    item.scaleY = fraction
                    item.rotation = it.animatedValue as Float
                }
                addListener(expandingListener)
                rotationSet.playTogether(this)
            }
        }

        scalingSet.playTogether(scalingAnimator, rotationAnimator)
        scalingSet.interpolator = CustomBounceInterpolator()
        rotationSet.interpolator = BounceInterpolator()

        rotationSet.startDelay = START_DELAY
        mExpandingSet.playTogether(scalingSet, rotationSet)
    }

    /**
     * Roll-up animators. Whole roll-up animation
     */
    private fun initRollUpAnimators() {
        mRollupSet.duration = ANIMATION_DURATION

        val destWidth = mMenu.size().times(mSize)

        val rotationSet = AnimatorSet()

        val scalingAnimator = ValueAnimator.ofInt(destWidth, mSize)
        val rotationAnimator = ValueAnimator.ofFloat(ROLL_UP_ROTATION_START, ROLL_UP_ROTATION_END)

        scalingAnimator.addUpdateListener(scaleAnimator)
        mRollupSet.addListener(rollUpListener)

        rotationAnimator.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Float
            mainImageView.rotation = value
        }

        val scalingSet = AnimatorSet().apply {
            playTogether(scalingAnimator, rotationAnimator)
            interpolator = CustomBounceInterpolator()
        }
        rotationSet.interpolator = BounceInterpolator()


        mRollupSet.playTogether(scalingSet, rotationSet)
    }

    /**
     * Menu inflating, we are getting list of visible items,
     * and use them in method @link initAndAddMenuItem
     * Be careful, don't use non-odd number of menu items
     * FTB works not good for such menus. Anyway you will have an exception
     *
     * @param resId your menu resource id
     */
    private fun inflateMenu(@MenuRes resId: Int) {
        getMenuInflater().inflate(resId, mMenu)
        if (mMenu.visibleItems.size % 2 != 0) {
            throw OddMenuItemsException()
        }
        mData = mMenu.visibleItems.map {
            initAndAddMenuItem(it)
        }
        initMainButton(mMenu.visibleItems.size / 2)
    }

    /**
     * Here we are resolving sizes of your Folding tab bar.
     * Depending on
     * @param measureSpec we can understand what kind of parameters
     * do you using in your layout file
     * In case if you are using wrap_content, we are using @dimen/ftb_size_normal
     * by default
     *
     * In case if you need some custom sizes, please use them)
     */
    private fun resolveAdjustedSize(desiredSize: Int, measureSpec: Int): Int {
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)

        return when (specMode) {
            View.MeasureSpec.UNSPECIFIED ->
                desiredSize
            View.MeasureSpec.AT_MOST ->
                Math.min(desiredSize, specSize)
            View.MeasureSpec.EXACTLY ->
                specSize
            else ->
                desiredSize
        }
    }

    /**
     * Here we are overriding onMeasure and here we are making our control
     * squared
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!isAnimating) {
            val preferredSize = getSizeDimension()
            mSize = resolveAdjustedSize(preferredSize, widthMeasureSpec)
            setMeasuredDimension(mSize, mSize)
        }
    }

    /**
     * Here we are saving view state
     */
    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        return SavedState(superState).apply {
            selection = selectedIndex
        }
    }

    /**
     * Here we are restoring view state (state, selection)
     */
    override fun onRestoreInstanceState(state: Parcelable?) {
        (state as SavedState).let {
            super.onRestoreInstanceState(it.superState)
            selectedIndex = it.selection
        }
    }

    val scaleAnimator = ValueAnimator.AnimatorUpdateListener { valueAnimator ->
        layoutParams = layoutParams.apply {
            width = valueAnimator.animatedValue as Int
        }
    }

    /**
     * Main button (+/x) initialization
     * Adding listener to the main button click
     */
    private fun initMainButton(mainButtonIndex: Int) {
        mainImageView.setImageResource(drawableResource)
        mainImageView.layoutParams = ViewGroup.LayoutParams(mSize, mSize)
        mainImageView.setOnClickListener {
            onMainButtonClickListener?.onMainButtonClicked()
            animateMenu()
        }
        addView(mainImageView, mainButtonIndex)
        mainImageView.setPadding(itemsPadding, itemsPadding, itemsPadding, itemsPadding)
    }

    /**
     * @param menuItem object from Android Sdk. This is same menu item
     * that you are using e.g in NavigationView or any kind of native menus
     */
    private fun initAndAddMenuItem(menuItem: MenuItemImpl): SelectedMenuItem {
        return SelectedMenuItem(context, selectionColor).apply {
            setImageDrawable(menuItem.icon)
            layoutParams = ViewGroup.LayoutParams(mSize, mSize)
            setPadding(itemsPadding, itemsPadding, itemsPadding, itemsPadding)
            visibility = View.GONE
            isActivated = menuItem.isChecked
            addView(this, indexCounter)

            setOnClickListener {
                onFoldingItemClickListener?.onFoldingItemSelected(menuItem) ?:
                        Log.e("FoldingTabBar", "FoldingItemClickListener is null")
                menuItem.isChecked = true

                selectedImageView?.isActivated = false
                selectedImageView = this
                selectedIndex = indexOfChild(this)
                animateMenu()
            }

            indexCounter++
        }
    }

    fun select(position: Int) {
        selectedImageView = (getChildAt(position) as SelectedMenuItem).apply {
            isActivated = true
        }
    }


    /**
     * measuredWidth - mSize = 0 we can understand that our menu is closed
     * But on some devices I've found a case when we don't have exactly 0. So
     * now we defined some range to understand what is the state of our menu
     */
    private fun animateMenu() {
        if ((measuredWidth - mSize) in -2..2) {
            expand()
        } else {
            rollUp()
        }
    }

    /**
     * These two public functions can be used to open our menu
     * externally
     */
    fun expand() {
        mExpandingSet.start()
    }

    fun rollUp() {
        mRollupSet.start()
    }

    /**
     * Getting SupportMenuInflater to get all visible items from
     * menu object
     */
    private fun getMenuInflater(): MenuInflater = SupportMenuInflater(context)

    /**
     * Here we should hide all items, and deactivate menu item
     */
    private val rollUpListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animator: Animator) {
            mData.forEach {
                it.visibility = View.GONE
            }
            selectedImageView?.isActivated = false
        }

        override fun onAnimationEnd(animator: Animator) {
        }

        override fun onAnimationCancel(animator: Animator) {
        }

        override fun onAnimationRepeat(animator: Animator) {
        }
    }

    /**
     * This listener we need to show our Menu items
     * And also after animation was finished we should activate
     * our SelectableImageView
     */
    private val expandingListener = object : Animator.AnimatorListener {

        override fun onAnimationStart(animator: Animator) {
            mData.forEach {
                it.visibility = View.VISIBLE
            }
        }

        override fun onAnimationEnd(animator: Animator) {
            selectedImageView?.isActivated = true
        }

        override fun onAnimationCancel(animator: Animator) {
        }

        override fun onAnimationRepeat(animator: Animator) {
        }
    }

    /**
     * Listener for handling events on folding items.
     */
    interface OnFoldingItemSelectedListener {
        /**
         * Called when an item in the folding tab bar menu is selected.

         * @param item The selected item
         * *
         * *
         * @return true to display the item as the selected item
         */
        fun onFoldingItemSelected(item: MenuItem): Boolean
    }

    /**
     * Listener for handling events on folding main button
     */
    interface OnMainButtonClickedListener {
        /**
         * Called when the main button was pressed
         */
        fun onMainButtonClicked()
    }

    /**
     * We have to save state and selection of our View
     */
    internal class SavedState : View.BaseSavedState {
        var selection: Int = 0

        internal constructor(superState: Parcelable) : super(superState)

        private constructor(inp: Parcel) : super(inp) {
            selection = inp.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(selection)
        }

        companion object {
            @JvmField val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

}