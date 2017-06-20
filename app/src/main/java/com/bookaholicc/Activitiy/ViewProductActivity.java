package com.bookaholicc.Activitiy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.bookaholicc.Adapters.ViewpagerAdapters.ListAdapters.ProductSwipeAdapter;
import com.bookaholicc.Model.Product;
import com.bookaholicc.R;
import com.bookaholicc.StorageHelpers.CartHandler;
import com.bookaholicc.utils.BlurBuilder;
import com.bookaholicc.utils.BundleKey;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 16/3/17.
 * +The Horizontal Swipe View Pageer
 *
 */

public class ViewProductActivity extends AppCompatActivity {


    private static final String TAG = "VIEW_PRODUCT";
    @BindView(R.id.vp_pager)
    HorizontalInfiniteCycleViewPager mPager;
    ProductSwipeAdapter mAdapter;

    @BindView(R.id.vp_backdrop_image)
    ImageView mBackDropImage;


    List<Product> mProductsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_product);
        ButterKnife.bind(this);
        Log.d(TAG, "Inside View Product Setting Up Pager ");


        //Get THe Product List

        Type type = new TypeToken<List<Product>>(){}.getType();
        Gson gson = new Gson();
        String Json = getIntent().getStringExtra(BundleKey.ARG_PRODUCT_LIST);
        List<Product> productList = gson.fromJson(Json, type);
        Log.d(TAG, String.format("Pid of First Product %d", productList.get(0).getPid()));
        Log.d(TAG, "onCreate: size of products " + productList.size());




        //set up the Adapter
        mAdapter = new ProductSwipeAdapter(getSupportFragmentManager(), this, mProductsList);
        //set it to Adapter
        mPager.setAdapter(mAdapter);
        mPager.setInterpolator(new AccelerateDecelerateInterpolator());
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + mPager.getRealItem());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });





    }
//        if (getIntent() != null){
//            String trns = getIntent().getStringExtra(BundleKey.TRANS_NAME);
//            Log.d(TAG, "onCreate: trans Name: "+trns);
//            mBackDropImage.setTransitionName(trns);
//            Bitmap b = drawableToBitmap(ContextCompat.getDrawable(this, R.mipmap.fifa));
//            Bitmap b1 = BlurBuilder.blur(this,b);
//            mBackDropImage.setImageBitmap(b1);
//            mBackDropImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        }
//        mProductsList = CartHandler.getInstance(this).getMockProducts();
//
//
//



    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
