package com.aositeluoke.quantityviewdemo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 类描述:商品详情规格对话框和购物车常用的数量控件
 * 作者:xues
 * 时间:2017年07月19日
 */

public class QuantityView extends LinearLayout {

    private static final String TAG = "QuantityView";

    //加减按钮公共属性
    private int opPadding;
    private int opWidth;
    private int opTextSize;
    private ColorStateList opTextColor;

    //减号按钮属性
    private Drawable subBackground;
    private String subText;
    //加号按钮属性
    private Drawable addBackground;
    private String addText;

    //输入框属性
    private int inputTextSize, inputTextWidth;
    private int inputTextColor;
    private Drawable inputBackground;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            afterTextChangedExecute = true;
            if (TextUtils.isEmpty(s)) {
                return;
            }
            setNum(Integer.parseInt(s.toString()));
        }
    };

    //当前数量,最大值,最小值,是否可输入,点击加减号是否立刻修改输入框的值
    private int num;
    private int maxNum;
    private int minNum;
    private boolean canInput, isNowChange = true;

    //分割线属性
    private int dividerWidth;
    private int dividerColor;


    //所有控件
    private ImageView addImageView, subImageView;
    private TextView addTextView, subTextView;
    private EditText editText;
    private View clickView, leftDivider, rightDivider;

    private boolean afterTextChangedExecute = false;//afterTextChanged执行了

    public QuantityView(Context context) {
        this(context, null);
    }

    public QuantityView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuantityView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.QuantityView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {
                //加减按钮公共属性
                case R.styleable.QuantityView_op_width:
                    opWidth = a.getLayoutDimension(attr, 0);
                    break;
//                case R.styleable.QuantityView_op_padding:
//                    opPadding = a.getDimensionPixelSize(attr, 0);
//                    break;
                case R.styleable.QuantityView_op_text_color:
                    opTextColor = a.getColorStateList(attr);
                    break;
                case R.styleable.QuantityView_op_text_size:
                    opTextSize = a.getDimensionPixelSize(attr, 15);
                    break;
                //减号按钮属性
                case R.styleable.QuantityView_sub_background:
                    subBackground = a.getDrawable(attr);
                    break;
                case R.styleable.QuantityView_sub_text:
                    subText = a.getString(attr);
                    break;
                //加号按钮属性
                case R.styleable.QuantityView_add_background:
                    addBackground = a.getDrawable(attr);
                    break;
                case R.styleable.QuantityView_add_text:
                    addText = a.getString(attr);
                    break;

                //分割线属性
                case R.styleable.QuantityView_divider_color:
                    dividerColor = a.getColor(attr, 0);
                    break;

                case R.styleable.QuantityView_divider_width:
                    dividerWidth = a.getLayoutDimension(attr, 0);
                    break;
                //输入框属性
                case R.styleable.QuantityView_input_background:
                    inputBackground = a.getDrawable(attr);
                    break;
                case R.styleable.QuantityView_input_text_color:
                    inputTextColor = a.getColor(attr, Color.parseColor("#333333"));
                    break;
                case R.styleable.QuantityView_input_text_size:
                    inputTextSize = a.getDimensionPixelSize(attr, 15);
                    break;
                case R.styleable.QuantityView_input_text_width:
                    inputTextWidth = a.getLayoutDimension(attr, 0);
                    break;
                //其他公共属性
                case R.styleable.QuantityView_can_input:
                    canInput = a.getBoolean(attr, true);
                    break;
                case R.styleable.QuantityView_num:
                    num = a.getInt(attr, 0);
                    break;
                case R.styleable.QuantityView_min_num:
                    minNum = a.getInt(attr, 1);
                    break;
                case R.styleable.QuantityView_max_num:
                    maxNum = a.getInt(attr, 200);
                    break;
                case R.styleable.QuantityView_is_now_change:
                    isNowChange = a.getBoolean(attr, true);
                    break;
            }
        }
        a.recycle();
        initView();
    }

    private void initView() {
        setFocusable(true);
        setFocusableInTouchMode(true);
//        inputTextSize = dip2px(inputTextSize);
//        opPadding = dip2px(opPadding);
        //减号按钮
//        LayoutParams subParams = new LayoutParams(opWidth, ViewGroup.LayoutParams.MATCH_PARENT);
//        subImageView = new ImageView(getContext());
//        subImageView.setImageDrawable(subSrc);
//        subImageView.setPadding(opPadding, opPadding, opPadding, opPadding);
//        subImageView.setBackground(subBackground);
//        subImageView.setLayoutParams(subParams);
//        subImageView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isNowChange) {
//                    num--;
//                    setNum(num);
//                } else {
//                    if (onSubAddClickCallBack != null) {
//
//                        onSubAddClickCallBack.onClickCallBack(QuantityView.this, num - 1);
//                    }
//                }
//
//            }
//        });

        LayoutParams subParams = new LayoutParams(opWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        subTextView = new TextView(getContext());
//        subTextView.setPadding(opPadding, opPadding, opPadding, opPadding);
        subTextView.setBackgroundDrawable(subBackground);
        subTextView.setLayoutParams(subParams);
        subTextView.setClickable(true);
        subTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, opTextSize);
        subTextView.setTextColor(opTextColor);
        subTextView.setGravity(Gravity.CENTER);
        subTextView.setText(subText);
        subTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNowChange) {
                    num--;
                    setNum(num);
                } else {
                    if (onSubAddClickCallBack != null) {

                        onSubAddClickCallBack.onClickCallBack(QuantityView.this, num - 1);
                    }
                }

            }
        });

        //加号按钮
//        LayoutParams addParams = new LayoutParams(opWidth, ViewGroup.LayoutParams.MATCH_PARENT);
//        addImageView = new ImageView(getContext());
//        addImageView.setImageDrawable(addSrc);
//        addImageView.setPadding(opPadding, opPadding, opPadding, opPadding);
//        addImageView.setBackground(addBackground);
//        addImageView.setLayoutParams(addParams);
//
//        addImageView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isNowChange) {
//                    if (!TextUtils.isEmpty(editText.getText() + ""))
//                        num++;
//                    setNum(num);
//                } else {
//                    if (onSubAddClickCallBack != null) {
//                        onSubAddClickCallBack.onClickCallBack(QuantityView.this, num + 1);
//                    }
//                }
//
//            }
//        });

        LayoutParams addParams = new LayoutParams(opWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        addTextView = new TextView(getContext());
//        addTextView.setPadding(opPadding, opPadding, opPadding, opPadding);
        addTextView.setBackgroundDrawable(addBackground);
        addTextView.setLayoutParams(addParams);
        addTextView.setClickable(true);
        addTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, opTextSize);
        addTextView.setTextColor(opTextColor);
        addTextView.setGravity(Gravity.CENTER);
        addTextView.setText(addText);
        addTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNowChange) {
                    if (!TextUtils.isEmpty(editText.getText() + ""))
                        num++;
                    setNum(num);
                } else {
                    if (onSubAddClickCallBack != null) {
                        onSubAddClickCallBack.onClickCallBack(QuantityView.this, num + 1);
                    }
                }

            }
        });

        //分割线
        LayoutParams leftDividerParams = new LayoutParams(dividerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        leftDivider = new View(getContext());
        leftDivider.setBackgroundColor(dividerColor);
        leftDivider.setLayoutParams(leftDividerParams);
        LayoutParams rightDividerParams = new LayoutParams(dividerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        rightDivider = new View(getContext());
        rightDivider.setBackgroundColor(dividerColor);
        rightDivider.setLayoutParams(rightDividerParams);


        //输入布局
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
        LayoutParams inputParams = new LayoutParams(inputTextWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        inputParams.gravity = Gravity.CENTER;
        frameLayout.setLayoutParams(inputParams);
        //输入框
        FrameLayout.LayoutParams editTextParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        editText = new EditText(getContext());
        editText.setPadding(0, 0, 0, 0);
        editText.setBackgroundColor(getResources().getColor(android.R.color.white));
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setSingleLine();
        editText.setGravity(Gravity.CENTER);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, inputTextSize);
        editText.setTextColor(inputTextColor);
        editText.setLayoutParams(editTextParams);
        editText.addTextChangedListener(textWatcher);
        editText.setClickable(true);
        editText.setEnabled(canInput);

        FrameLayout.LayoutParams clickViewParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        clickView = new View(getContext());
        clickView.setLayoutParams(clickViewParams);
        clickView.setClickable(true);
        clickView.setVisibility(canInput ? GONE : VISIBLE);
        frameLayout.addView(editText);
        frameLayout.addView(clickView);


        //数量控件点击按钮
        this.setClickable(true);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick:显示对话框界面");
            }
        });

        addView(subTextView);
        addView(leftDivider);
        addView(frameLayout);
        addView(rightDivider);
        addView(addTextView);
        setNum(num);
    }

    public int getNum() {
        return num;
    }

    public int getMinNum() {
        return minNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    /**
     * 设置当前数量
     *
     * @param newNum
     */
    public QuantityView setNum(int newNum) {
//        设置的数量小于最小值，使用最小值作为当前数量，大于最大值，使用最大值作为当前数量，修改按钮是否可用
        num = newNum;
        if (newNum >= maxNum) {
            num = maxNum;
            addTextView.setEnabled(false);
        } else {
            addTextView.setEnabled(true);
        }
        if (newNum <= minNum) {
            num = minNum;
            subTextView.setEnabled(false);
        } else {
            subTextView.setEnabled(true);
        }
        //设置之前先移除，再添加避免，重复触发afterTextChanged
        editText.removeTextChangedListener(textWatcher);
        editText.setText(num + "");
        editText.setSelection((num + "").length());
        editText.addTextChangedListener(textWatcher);
        if (onNumChangeListener != null)
            onNumChangeListener.onNuberChange(QuantityView.this
                    , editText, num);

        return this;
    }

    /**
     * 设置最小值
     *
     * @param minNum 最小值
     * @return
     */
    public QuantityView setMinNum(int minNum) {
        this.minNum = minNum;
        setNum(num);
        return this;
    }

    /**
     * @param maxNum 设置最大值
     * @return
     */
    public QuantityView setMaxNum(int maxNum) {
        this.maxNum = maxNum;
        setNum(num);
        return this;
    }

    /**
     * 设置是否可输入
     *
     * @param canInput
     * @return
     */
    public QuantityView setCanInput(boolean canInput) {
        this.canInput = canInput;
        editText.setEnabled(canInput);
        clickView.setVisibility(canInput ? GONE : VISIBLE);
        return this;
    }

    /**
     * @param nowChange 设置是否立即改变输入框的内容
     * @return
     */
    public QuantityView setNowChange(boolean nowChange) {
        isNowChange = nowChange;
        return this;
    }

    /**
     * 设置中间的点击事件
     *
     * @param onClickListener
     * @return
     */
    public QuantityView setCenterClickListener(@NonNull OnClickListener onClickListener) {
        clickView.setOnClickListener(onClickListener);
        return this;
    }


    /**
     * dp转像素
     *
     * @param dpValue
     * @return
     */
    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    //数量改变监听
    private OnNumChangeListener onNumChangeListener;

    public QuantityView setOnNumChangeListener(OnNumChangeListener onNumChangeListener) {
        this.onNumChangeListener = onNumChangeListener;
        return this;
    }

    public interface OnNumChangeListener {
        void onNuberChange(QuantityView cartQuantityView, EditText editText, int num);
    }


    //点击加号减号按钮回调接口
    private OnSubAddClickCallBack onSubAddClickCallBack;

    public QuantityView setOnSubAddClickCallBack(OnSubAddClickCallBack onSubAddClickCallBack) {
        this.onSubAddClickCallBack = onSubAddClickCallBack;
        return this;
    }

    public interface OnSubAddClickCallBack {
        void onClickCallBack(QuantityView quantitySkuDesView, int num);
    }

}
