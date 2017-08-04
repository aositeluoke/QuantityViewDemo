package com.aositeluoke.quantityviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private QuantityView quantity, dialogQuantity;
    private CommonDialog inputDialog, confirmDialog;//输入框按钮和确认删除按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputDialog = new CommonDialog(this, R.style.dialog_theme_center_dispay, R.layout.dialog_quantity);
        inputDialog.setCanceledOnTouchOutside(true);
        dialogQuantity = inputDialog.findViewByRootView(R.id.quantity);
        inputDialog.setCloseDialogListener(R.id.tv_cancel)
                .setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QuantityView view = inputDialog.findViewByRootView(R.id.quantity);
                        quantity.setNum(view.getNum());
                        inputDialog.dismiss();
                    }
                });

        confirmDialog = new CommonDialog(this, R.style.dialog_theme_center_dispay, R.layout.dialog_quantity_zero);
        confirmDialog.setCanceledOnTouchOutside(true);
        confirmDialog.setCloseDialogListener(R.id.tv_cancel)
                .setCloseDialogListener(R.id.tv_confirm);
        quantity = ((QuantityView) findViewById(R.id.quantity));

        //点击加减号调用接口成功后，再改变数量控件的数量
        quantity.setNowChange(false);
        quantity.setOnSubAddClickCallBack(new QuantityView.OnSubAddClickCallBack() {
            @Override
            public void onClickCallBack(QuantityView quantitySkuDesView, int num) {
                if (num == 0) {
                    confirmDialog.show();
                } else {
                    quantitySkuDesView.setNum(num);
                }
            }
        });

        //点击中间输入框，弹出对话框让用户输入数量
        quantity.setCanInput(false);
        quantity.setCenterClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogQuantity.setMinNum(quantity.getMinNum() == 0 ? 1 : 0);
                dialogQuantity.setMaxNum(quantity.getMaxNum());
                dialogQuantity.setNum(quantity.getNum());
                inputDialog.show();
            }
        });

        quantity.setOnNumChangeListener(new QuantityView.OnNumChangeListener() {
            @Override
            public void onNuberChange(QuantityView cartQuantityView, EditText editText, int num) {

            }
        });
    }
}
