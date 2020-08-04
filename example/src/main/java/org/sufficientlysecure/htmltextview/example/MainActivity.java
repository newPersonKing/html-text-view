/*
 * Copyright (C) 2013-2016 Dominik Schürmann <dominik@schuermann.eu>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sufficientlysecure.htmltextview.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.sufficientlysecure.htmltextview.ClickableTableSpan;
import org.sufficientlysecure.htmltextview.DrawTableLinkSpan;
import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;
import org.sufficientlysecure.htmltextview.OnClickATagListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.sufficientlysecure.htmltextview.example.WebViewActivity.EXTRA_TABLE_HTML;

/*https://github.com/SufficientlySecure/html-textview/tree/master/example*/
public class MainActivity extends Activity {

    // The html table(s) are individually passed through to the ClickableTableSpan implementation
    // presumably for a WebView activity.
    class ClickableTableSpanImpl extends ClickableTableSpan {
        @Override
        public ClickableTableSpan newInstance() {
            return new ClickableTableSpanImpl();
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra(EXTRA_TABLE_HTML, getTableHtml());
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HtmlTextView textView = (HtmlTextView) findViewById(R.id.html_text);

        //text.setRemoveFromHtmlSpace(false); // default is true
        textView.setClickableTableSpan(new ClickableTableSpanImpl());
        DrawTableLinkSpan drawTableLinkSpan = new DrawTableLinkSpan();
        drawTableLinkSpan.setTableLinkText("[tap for table]");
        textView.setDrawTableLinkSpan(drawTableLinkSpan);

        // Best to use indentation that matches screen density.
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        textView.setListIndentPx(metrics.density * 10);

        // a tag click listener
        textView.setOnClickATagListener(new OnClickATagListener() {

            @Override
            public void onClick(View widget, @Nullable String href) {
                final Toast toast = Toast.makeText(MainActivity.this, null, Toast.LENGTH_SHORT);
                toast.setText(href);
                toast.show();
            }
        });

        String str = "<html><body>正常文本<b>加粗</b><i>倾斜文本</i><b><i>加粗的倾斜文本</i></b><font color=#00ffff>蓝色的文本</font>正常文本</body></html>";

        textView.setHtml(str, new HtmlResImageGetter(getBaseContext()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_view_data_binding) {
//            startActivity(new Intent(this, DataBindingExampleActivity.class));
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
