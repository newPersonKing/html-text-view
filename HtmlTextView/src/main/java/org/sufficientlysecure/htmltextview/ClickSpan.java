package org.sufficientlysecure.htmltextview;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

public class ClickSpan extends ClickableSpan {

    private String content;

    @Override
    public void onClick(@NonNull View widget) {
        Log.i("ccccccccccc","点击了Span===="+content);
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
//        super.updateDrawState(ds);
    }

    public void setContent(String content){
        this.content = content;
    }
}
