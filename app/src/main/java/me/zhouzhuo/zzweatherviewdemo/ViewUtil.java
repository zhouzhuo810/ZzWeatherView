package me.zhouzhuo.zzweatherviewdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ViewUtil {
    public static final int INVALID = Integer.MIN_VALUE;

    public static void setAbsListViewHeight(AbsListView absListView, int lineNumber, int verticalSpace) {
        int totalHeight = getAbsListViewHeight(absListView, lineNumber, verticalSpace);
        ViewGroup.LayoutParams params = absListView.getLayoutParams();
        params.height = totalHeight;
        ((MarginLayoutParams) params).setMargins(0, 0, 0, 0);
        absListView.setLayoutParams(params);
    }

    public static int getAbsListViewHeight(AbsListView absListView, int lineNumber, int verticalSpace) {
        int totalHeight = 0;
        int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        absListView.measure(w, h);
        ListAdapter mListAdapter = absListView.getAdapter();
        if (mListAdapter == null) {
            return totalHeight;
        }
        int count = mListAdapter.getCount();
        if (absListView instanceof ListView) {
            for (int i = 0; i < count; i++) {
                View listItem = mListAdapter.getView(i, null, absListView);
                listItem.measure(w, h);
                totalHeight += listItem.getMeasuredHeight();
            }
            if (count == 0) {
                totalHeight = verticalSpace;
            } else {
                totalHeight = totalHeight + (((ListView) absListView).getDividerHeight() * (count - 1));
            }
        } else if (absListView instanceof GridView) {
            int remain = count % lineNumber;
            if (remain > 0) {
                remain = 1;
            }
            if (mListAdapter.getCount() == 0) {
                totalHeight = verticalSpace;
            } else {
                View listItem = mListAdapter.getView(0, null, absListView);
                listItem.measure(w, h);
                int line = count / lineNumber + remain;
                totalHeight = line * listItem.getMeasuredHeight() + (line - 1) * verticalSpace;
            }
        }
        return totalHeight;
    }

    public static void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
    }

    public static int scaleValue(Context context, float value) {
        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        if (width > height) {
            width = mDisplayMetrics.heightPixels;
            height = mDisplayMetrics.widthPixels;
        }
        if (mDisplayMetrics.scaledDensity >= AppConfig.UI_DENSITY) {
            if (width > AppConfig.UI_WIDTH) {
                value = value * (1.3f - 1.0f / mDisplayMetrics.scaledDensity);
            } else if (width < AppConfig.UI_WIDTH) {
                value = value * (1.0f - 1.0f / mDisplayMetrics.scaledDensity);
            }
        } else {
            float offset = AppConfig.UI_DENSITY - mDisplayMetrics.scaledDensity;
            if (offset > 0.5f) {
                value = value * 0.9f;
            } else {
                value = value * 0.95f;
            }
        }
        return scale(mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels, value);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null) {
            mResources = Resources.getSystem();
        } else {
            mResources = context.getResources();
        }
        DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }

    public static int scaleTextValue(Context context, float value) {
        return scaleValue(context, value);
    }

    public static int scale(int displayWidth, int displayHeight, float pxValue) {
        if (pxValue == 0) {
            return 0;
        }
        float scale = 1;
        try {
            int width = displayWidth;
            int height = displayHeight;
            if (width > height) {
                width = displayHeight;
                height = displayWidth;
            }
            float scaleWidth = (float) width / AppConfig.UI_WIDTH;
            float scaleHeight = (float) height / AppConfig.UI_HEIGHT;
            scale = Math.min(scaleWidth, scaleHeight);
        } catch (Exception e) {
        }
        return Math.round(pxValue * scale + 0.5f);
    }

    public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }

    public static void scaleContentView(ViewGroup contentView) {
        ViewUtil.scaleView(contentView);
        if (contentView.getChildCount() > 0) {
            for (int i = 0; i < contentView.getChildCount(); i++) {
                View view = contentView.getChildAt(i);
                if (view instanceof ViewGroup) {
                    scaleContentView((ViewGroup) (view));
                } else {
                    scaleView(contentView.getChildAt(i));
                }
            }
        }
    }

    @SuppressLint("NewApi")
    public static void scaleView(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            setTextSize(textView, textView.getTextSize());
        }
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) view.getLayoutParams();
        if (null != params) {
            int width = INVALID;
            int height = INVALID;
            if (params.width != ViewGroup.LayoutParams.WRAP_CONTENT
                    && params.width != ViewGroup.LayoutParams.MATCH_PARENT) {
                width = params.width;
            }
            if (params.height != ViewGroup.LayoutParams.WRAP_CONTENT
                    && params.height != ViewGroup.LayoutParams.MATCH_PARENT) {
                height = params.height;
            }
            setViewSize(view, width, height);
            setPadding(view, view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        }
        if (view.getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams mMarginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
            if (mMarginLayoutParams != null) {
                setMargin(view, mMarginLayoutParams.leftMargin, mMarginLayoutParams.topMargin, mMarginLayoutParams.rightMargin, mMarginLayoutParams.bottomMargin);
            }
        }
        if (VERSION.SDK_INT >= 16) {
            int minWidth = scaleValue(view.getContext(), view.getMinimumWidth());
            int minHeight = scaleValue(view.getContext(), view.getMinimumHeight());
            view.setMinimumWidth(minWidth);
            view.setMinimumHeight(minHeight);
        }
    }

    public static void setTextSize(TextView textView, float sizePixels) {
        float scaledSize = scaleTextValue(textView.getContext(), sizePixels);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledSize);
    }

    public static void setViewSize(View view, int widthPixels, int heightPixels) {
        int scaledWidth = scaleValue(view.getContext(), widthPixels);
        int scaledHeight = scaleValue(view.getContext(), heightPixels);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            return;
        }
        if (widthPixels != INVALID) {
            params.width = scaledWidth;
        }
        if (heightPixels != INVALID && heightPixels != 1) {
            params.height = scaledHeight;
        }
        view.setLayoutParams(params);
    }

    public static void setPadding(View view, int left, int top, int right, int bottom) {
        int scaledLeft = scaleValue(view.getContext(), left);
        int scaledTop = scaleValue(view.getContext(), top);
        int scaledRight = scaleValue(view.getContext(), right);
        int scaledBottom = scaleValue(view.getContext(), bottom);
        view.setPadding(scaledLeft, scaledTop, scaledRight, scaledBottom);
    }

    public static void setMargin(View view, int left, int top, int right, int bottom) {
        int scaledLeft = scaleValue(view.getContext(), left);
        int scaledTop = scaleValue(view.getContext(), top);
        int scaledRight = scaleValue(view.getContext(), right);
        int scaledBottom = scaleValue(view.getContext(), bottom);
        if (view.getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams mMarginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
            if (mMarginLayoutParams != null) {
                if (left != INVALID) {
                    mMarginLayoutParams.leftMargin = scaledLeft;
                }
                if (right != INVALID) {
                    mMarginLayoutParams.rightMargin = scaledRight;
                }
                if (top != INVALID) {
                    mMarginLayoutParams.topMargin = scaledTop;
                }
                if (bottom != INVALID) {
                    mMarginLayoutParams.bottomMargin = scaledBottom;
                }
                view.setLayoutParams(mMarginLayoutParams);
            }
        }
    }
}
