package com.think.glide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.demonstrate.DemonstrateUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView iv;
    protected Button btn;
    protected String item;
    private String STR_TAG = "--->***";
    private String TAG = this.getClass().getSimpleName();
    protected String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        iv = (ImageView) findViewById(R.id.iv);
        btn = (Button) findViewById(R.id.btn);
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn.setOnClickListener(MainActivity.this);
        btn2.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn) {
            show();
        } else if (view.getId() == R.id.btn2) {
            show0();
        }
    }

    private void show0() {
        String[] items = {
                "0加载图片来自File",
                "1加载图片来自资源id",
                "2加载图片来自uri",
                "3加载强制转化为gif",
                "4加载原图的缩略图",
                "5加载缩略图高级方式",
                "6加载设置不缓存",
                "7加载设置优先级",
                "8加载回调后的图片",
                "9加载回调后的图片设置尺寸",
                "10加载图片圆角转换",
                "11加载图片圆角转换2",
        };
        new AlertDialog.Builder(this)
                .setTitle("glide操作2")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                selectedA0();
                                break;
                            case 1:
                                selectedA1();
                                break;
                            case 2:
                                selectedA2();
                                break;
                            case 3:
                                selectedA3();
                                break;
                            case 4:
                                selectedA4();
                                break;
                            case 5:
                                selectedA5();
                                break;
                            case 6:
                                selectedA6();
                                break;
                            case 7:
                                selectedA7();
                                break;
                            case 8:
                                selectedA8();
                                break;
                            case 9:
                                selectedA9();
                                break;
                            case 10:
                                selectedA10();
                                break;
                            case 11:
                                selectedA11();
                                break;
                            case 12:
                                break;
                        }
                    }
                }).create()
                .show();
    }

    private void selectedA11() {
        //RoundedCorners,
        //CenterInside
        Glide.with(this)
                .load(UC.IMAGE_URL_A11)
                .apply(
                        new RequestOptions()
                                .override(300)
                                .transform(new RoundedCorners(20))
                )
                .into(iv);
    }

    private void selectedA10() {
        Glide.with(this)
                .load(UC.IMAGE_URL_A10)
                .apply(new RequestOptions().transform(new GlideRoundTransform(this)))
                .into(iv);
    }

    private void selectedA9() {
        //强制Glide返回一个Bitmap
        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>(300, 300) {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                iv.setImageBitmap(resource);
            }
        };
        Glide.with(this).asBitmap()
                .load(UC.IMAGE_URL_A9)
                .into(target
                );
    }


    private void selectedA8() {
        //上面这段代码不要写成匿名内部类的机制，原因就是java的自动垃圾回收机制可能在图片还没有加载好的时候就已经把你的Target回收了
        //注意.with()里面的参数，Glide的请求是和传进去的Context共存亡的，如果传一个Activity进去，当Activity GG过后，你的请求也就GG了，但是如果这样传：.with(context.getApplicationContext() ).当你的Activity GG过后，请求还是会继续，回调还是会继续。


        //强制Glide返回一个Bitmap
        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                iv.setImageBitmap(resource);
            }
        };
        Glide.with(this).asBitmap()
                .load(UC.IMAGE_URL_A8)
                .into(target
                );
    }

    private void selectedA7() {
        Glide.with(this)
                .load(UC.IMAGE_URL_A7)
                .apply(new RequestOptions().priority(Priority.LOW))
                .into(iv);
    }

    private void selectedA6() {
        Glide.with(this)
                .load(UC.IMAGE_URL_A6)
                .apply(new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(iv);
    }

    private void selectedA5() {
        //先加载第一张图片作为缩略图,在加载最终要显示的图片
        //thumbnail中加载的是缩略图
        Glide.with(this).load(UC.IMAGE_URL_9)
                .thumbnail(Glide.with(this).load(UC.IMAGE_URL_A5)).into(iv);
    }

    private void selectedA4() {
        //先加载为原图的十分之一,后再加载原图.
        Glide.with(this)
                .load(UC.IMAGE_URL_A4)
                .thumbnail(0.1f)
                .into(iv);
    }

    private void selectedA3() {
        Glide.with(this).asGif().load(UC.GIF_URL_0).into(iv);
    }

    private void selectedA2() {
        Glide.with(this)
                .load(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "d.jpg")))
                .into(iv);
    }

    private void selectedA1() {
        Glide.with(this)
                .load(R.mipmap.ic_launcher_round)
                .into(iv);
    }

    private void selectedA0() {
        File file 
        = new File(Environment.getExternalStorageDirectory(), "3.jpg");
        Glide.with(this).load(file).into(iv);
    }

    private void show() {
        final String[] items = {
                "0加载图片一般用法",
                "1加载图片成功监听",
                "2加载图片失败",
                "3加载图片ID占位图",
                "4加载图片Drawable占位图",
                "5加载图片传入null",
                "6加载图片后修改宽高",
                "7加载图片后修改宽高等尺寸",
                "8加载图片centerCrop",
                "9加载图片fitCenter",
                "10加载图片centerInside",
                "11加载图片circleCrop",
                "12加载动态图的第一帧",
                "13加载动态图成功",
                "14加载本地视频缩略图",

        };
        new AlertDialog.Builder(this)
                .setTitle("glide操作选项")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        item = items[which] + STR_TAG;
                        switch (which) {
                            case 0:
                                selected0();
                                break;
                            case 1:
                                selected1();
                                break;
                            case 2:
                                selected2();
                                break;
                            case 3:
                                selected3();
                                break;
                            case 4:
                                selected4();
                                break;
                            case 5:
                                selected5();
                                break;
                            case 6:
                                selected6();
                                break;
                            case 7:
                                selected7();
                                break;
                            case 8:
                                selected8();
                                break;
                            case 9:
                                selected9();
                                break;
                            case 10:
                                selected10();
                                break;
                            case 11:
                                selected11();
                                break;
                            case 12:
                                selected12();
                                break;
                            case 13:
                                selected13();
                                break;
                            case 14:
                                selected14();
                                break;
                        }
                    }
                }).create()
                .show();
    }

    private void selected14() {
        //缩略图的加载
        String name = "v.f40.mp4";
        Glide.with(this).load(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), name)))
                .into(iv);
    }

    private void selected13() {
        //GIF加载，URL指向的资源必须是gif，如果是普通图片则不显示。
        //相反，如果指向正确但没有执行asGif方法，则只是作为普通图片展示
        Glide.with(this).asGif().load(UC.GIF_URL_0).into(iv);
    }

    private void selected12() {
        //可理解为加载动态图的第一帧的Bitmap,比如Gif
        Glide.with(this).asBitmap().
                load(UC.GIF_URL_0).into(iv);
    }

    private void selected11() {
        Glide.with(this)
                .load(UC.IMAGE_URL_11)
                .apply(new RequestOptions().circleCrop())
                .into(iv);
    }

    private void selected10() {
        Glide.with(this)
                .load(UC.IMAGE_URL_10)
                .apply(new RequestOptions().centerInside())
                .into(iv);
    }

    private void selected9() {
        Glide.with(this)
                .load(UC.IMAGE_URL_8)
                .apply(new RequestOptions().fitCenter())
                .into(iv);
    }

    private void selected8() {
        RequestOptions options = new RequestOptions()
                .centerCrop();
        Glide.with(this)
                .load(UC.IMAGE_URL_9)
                .apply(options)
                .into(iv);
    }

    private void selected7() {
        RequestOptions options = new RequestOptions().override(300).fitCenter();
        Glide.with(this)
                .load(UC.IMAGE_URL_1)
                .apply(options)
                .into(iv);
    }

    private void selected6() {
        RequestOptions options = new RequestOptions().override(400, 400).fitCenter();
        Glide.with(this).load(UC.IMAGE_URL_1)
                .apply(options)
                .into(iv);
    }

    private void selected5() {
        RequestOptions options = new RequestOptions()
                .fallback(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this)
                .load(null)
                .apply(options)
                .into(iv);
    }

    private void selected4() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.load);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        RequestOptions options = new RequestOptions().placeholder(drawable);
        Glide.with(this)
                .load(UC.IMAGE_URL_ERROR)
                .apply(options)
                .into(iv);
    }

    private void selected3() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.load)
                .error(R.mipmap.fail)
                .fallback(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this)
                .load(UC.IMAGE_URL_ERROR)
                .apply(options)
                .into(iv);
    }

    private void selected2() {
        Glide.with(this)
                .load(UC.IMAGE_URL_ERROR)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        DemonstrateUtil.showToastResult(MainActivity.this, "onLoadFailed失败");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        DemonstrateUtil.showToastResult(MainActivity.this, "onResourceReady成功");
                        return false;
                    }
                }).into(iv);
    }

    private void selected1() {

        Glide.with(this)
                .load(UC.IMAGE_URL_1)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        DemonstrateUtil.showToastResult(MainActivity.this, "onLoadFailed加载失败!");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        DemonstrateUtil.showToastResult(MainActivity.this, "onResourceReady加载成功!");
                        return false;
                    }
                })
                .into(iv);
    }

    private void selected0() {
        //默认使用原图加载,内存缓存,磁盘缓存,
        Glide.with(this).load(UC.IMAGE_URL_0).into(iv);
    }


}
