package net.gsantner.markor.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.gsantner.markor.R;
import net.gsantner.markor.activity.DocumentActivity;

import java.io.File;

public class MarkorWebViewClient extends WebViewClient {

    private final Activity _activity;

    public MarkorWebViewClient(Activity activity) {
        _activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("file://")) {
            File file = new File(url.replace("file://", ""));
            String mimetype;
            if (ContextUtils.get().isMaybeMarkdownFile(file)) {
                Intent newPreview = new Intent(_activity, DocumentActivity.class);
                newPreview.putExtra(DocumentIO.EXTRA_PATH, file);
                newPreview.putExtra(DocumentActivity.EXTRA_DO_PREVIEW, true);
                _activity.startActivity(newPreview);
            } else if ((mimetype = ContextUtils.getMimeType(url)) != null) {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), mimetype);
                _activity.startActivity(intent);
            } else {
                Uri uri = Uri.parse(url);
                _activity.startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, uri), _activity.getString(R.string.open_with)));
            }
        } else {
            ContextUtils.get().openWebpageInExternalBrowser(url);
        }
        return true;
    }
}
