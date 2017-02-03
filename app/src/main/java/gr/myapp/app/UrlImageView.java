package gr.myapp.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;


public class UrlImageView extends ImageView {
    private static class UrlLoadingTask extends AsyncTask<URL, Void, Bitmap> {
        private final ImageView updateView;
        private boolean         isCancelled = false;
        private InputStream     urlInputStream;

        private UrlLoadingTask(ImageView updateView) {
            this.updateView = updateView;
        }

        @Override
        protected Bitmap doInBackground(URL... params) {
            try {
                URLConnection con = params[0].openConnection();
                con.setUseCaches(true);
                this.urlInputStream = con.getInputStream();
                return BitmapFactory.decodeStream(urlInputStream);
            } catch (IOException e) {
                return null;
            } finally {
                if (this.urlInputStream != null) {
                    try {
                        this.urlInputStream.close();
                    } catch (IOException e) {
                        ;
                    } finally {
                        this.urlInputStream = null;
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (!this.isCancelled) {
                this.updateView.setImageBitmap(result);
            }
        }

        /*
         */
        @Override
        protected void onCancelled() {
            this.isCancelled = true;
            try {
                if (this.urlInputStream != null) {
                    try {
                        this.urlInputStream.close();
                    } catch (IOException e) {
                        ;
                    } finally {
                        this.urlInputStream = null;
                    }
                }
            } finally {
                super.onCancelled();
            }
        }
    }

    /*
     */
    private AsyncTask<URL, Void, Bitmap> currentLoadingTask;
    /*
     */
    private Object                       loadingMonitor = new Object();

    public UrlImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public UrlImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UrlImageView(Context context) {
        super(context);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        cancelLoading();
        super.setImageBitmap(bm);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageResource(int resId) {
        cancelLoading();
        super.setImageResource(resId);
    }

    @Override
    public void setImageURI(Uri uri) {
        cancelLoading();
        super.setImageURI(uri);
    }

    /**
     * loads image from given url
     *
     * @param url
     */
    public void setImageURL(URL url) {
        synchronized (loadingMonitor) {
            this.currentLoadingTask = new UrlLoadingTask(this).execute(url);
        }
    }

    /**
     */
    public void cancelLoading() {
        synchronized (loadingMonitor) {
            if (this.currentLoadingTask != null) {
                this.currentLoadingTask.cancel(true);
                this.currentLoadingTask = null;
            }
        }
    }
}