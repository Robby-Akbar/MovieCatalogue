package monster.myapp.moviecatalogue.core.utils

import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

/**
 * Created by robby on 14/05/21.
 */
class MyGestureListener(private val callback: SwipeGestureListener) :
    GestureDetector.SimpleOnGestureListener() {

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    override fun onFling(
        downEvent: MotionEvent?,
        moveEvent: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val diffX = moveEvent?.x?.minus(downEvent!!.x) ?: 0.0F
        val diffY = moveEvent?.y?.minus(downEvent!!.y) ?: 0.0F

        return if (abs(diffX) > abs(diffY)) {
            // this is a left or right swipe
            if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    callback.onSwipeRight()
                } else {
                    callback.onLeftSwipe()
                }
                true
            } else {
                super.onFling(downEvent, moveEvent, velocityX, velocityY)
            }
        } else {
            // this is either a bottom or top swipe.
            if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    callback.onSwipeTop()
                } else {
                    callback.onSwipeBottom()
                }
                true
            } else {
                super.onFling(downEvent, moveEvent, velocityX, velocityY)
            }
        }
    }
}