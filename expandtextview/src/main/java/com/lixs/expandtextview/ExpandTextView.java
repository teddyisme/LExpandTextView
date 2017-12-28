package com.lixs.expandtextview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author XinSheng
 * @description
 * @date 2017/11/30
 */
public class ExpandTextView extends RelativeLayout implements View.OnClickListener {
    private TextView textView;
    private TextView button;
    private LinearLayout btnLl;
    private ImageView image;
    private boolean isExpand = false;
    private Context mContent;
    private boolean isShowBtn = false;
    private boolean expandTextLeft = true;

    private String expandBtnText = "展开";
    private String foldBtnText = "收起";

    private Integer maxLine;
    private Integer minLine;

    private Integer textSpace;
    private Float textMulti;
    private Float textScaleX;

    public ExpandTextView(Context context) {
        super(context);
        init(context, null);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    private void init(final Context context, AttributeSet attrs) {
        mContent = context;
        LayoutInflater.from(context).inflate(R.layout.custom_expand_text, this, true);
        textView = findViewById(R.id.expand_tv);
        button = findViewById(R.id.expand_btn);
        image = findViewById(R.id.image);
        btnLl = findViewById(R.id.btn_ll);

        initAttrs(context, attrs);

        this.setOnClickListener(this);
        resetHeight(true);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ExpandTextView);
        if (attributes != null) {
            int expandTextColor = attributes.getResourceId(R.styleable.ExpandTextView_expand_text_color, R.color.expand_text_color);
            button.setTextColor(getResources().getColor(expandTextColor));
            String expandText = attributes.getString(R.styleable.ExpandTextView_expand_btn_text);
            String foldText = attributes.getString(R.styleable.ExpandTextView_expand_btn_fold_text);
            if (!TextUtils.isEmpty(expandText)) {
                expandBtnText = expandText;
            }
            if (!TextUtils.isEmpty(foldText)) {
                foldBtnText = foldText;
            }
            textSpace = attributes.getInteger(R.styleable.ExpandTextView_expand_text_line_space, 2);
            textMulti = attributes.getFloat(R.styleable.ExpandTextView_expand_text_line_multi, 1.8f);
            maxLine = attributes.getInteger(R.styleable.ExpandTextView_expand_text_max_line, 100);
            minLine = attributes.getInteger(R.styleable.ExpandTextView_expand_text_min_line, 1);
            int expandBtnDrawable = attributes.getResourceId(R.styleable.ExpandTextView_expand_btn_drawable, R.mipmap.icon_expand);
            image.setImageResource(expandBtnDrawable);
            boolean isExpand = attributes.getBoolean(R.styleable.ExpandTextView_expand_is_expand_init, false);
            expandTextLeft = attributes.getBoolean(R.styleable.ExpandTextView_expand_is_expand_btn_left, true);
            String content = attributes.getString(R.styleable.ExpandTextView_expand_text_content);
            setText(content, isExpand);
            attributes.recycle();
        }
    }

    public void setText(String text, boolean isShowuBtn) {
        isShowBtn = isShowuBtn;
        textView.setText(text);
        if (isShowuBtn) {
            button.setText(expandBtnText);
        }
        btnLl.setVisibility(isShowuBtn ? VISIBLE : GONE);
        textView.setLineSpacing(DensityUtils.dp2px(mContent, textSpace), isShowuBtn ? textMulti : 1f);
        LayoutParams tp = (LayoutParams) btnLl.getLayoutParams();
        if (expandTextLeft) {
            tp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else {
            tp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        btnLl.setLayoutParams(tp);
        resetHeight(isShowuBtn);
    }

    private void resetHeight(boolean isreset) {
        setHeight(isreset ? minLine : maxLine);
    }

    private void setHeight(final int height) {
        post(new Runnable() {
            @Override
            public void run() {
                textView.setMaxLines(height);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!isShowBtn) {
            return;
        }
        if (isExpand) {
            resetHeight(true);
            button.setText(expandBtnText);
        } else {
            resetHeight(false);
            button.setText(foldBtnText);
        }
        isExpand = !isExpand;
        startAnimator(isExpand);
    }


    private void startAnimator(boolean isExpand) {
        Animation animation = new RotateAnimation(isExpand ? 0f : 180f, isExpand ? 180f : 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(400);
        animation.setRepeatCount(0);
        animation.setFillAfter(true);
        image.startAnimation(animation);
    }
}
