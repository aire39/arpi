package techcurb.listener;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by seanx_000 on 5/24/2016.
 *
 */
public class onSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    public onSwipeTouchListener(Context context) {
        gestureDetector = new GestureDetector(context, new swipeGesture());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class swipeGesture extends GestureDetector.SimpleOnGestureListener {

        private final Integer SWIPE_DISTANCE_THRESHOLD = 100;
        private final Integer SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            Log.i("motion", Float.toString(e.getX()));
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            super.onFling(e1, e2, velocityX, velocityY);

            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();

            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if(distanceX > 0)
                    onSwipeRight();
                else
                    onSwipeLeft();
            }

            return true;
        }


    }

    public void onSwipeLeft() {

    }

    public void onSwipeRight() {

    }
}
