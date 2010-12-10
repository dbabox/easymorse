package demos.animation;

import android.util.Log;
import android.view.animation.Interpolator;

public class MyInterpolator implements Interpolator {

	@Override
	public float getInterpolation(float input) {
		Log.d("anim", ">>"+input);
		return input;
	}

}
