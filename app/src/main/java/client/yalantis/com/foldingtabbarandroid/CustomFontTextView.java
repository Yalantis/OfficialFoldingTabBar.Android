package client.yalantis.com.foldingtabbarandroid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by andrewkhristyan on 12/9/16.
 */

public class CustomFontTextView extends TextView {

    public CustomFontTextView(Context context) {
        this(context, null, 0);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);

        String textType = attributeArray.getString(R.styleable.CustomFontTextView_font);
        if (textType != null) {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), textType);
            setTypeface(typeface);
        }
    }

}
