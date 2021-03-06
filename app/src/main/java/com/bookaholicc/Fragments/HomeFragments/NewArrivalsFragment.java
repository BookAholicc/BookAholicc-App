package com.bookaholicc.Fragments.HomeFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.Activitiy.ViewProductActivity;
import com.bookaholicc.Adapters.ViewpagerAdapters.ListAdapters.NewArrivalListAdapter;
import com.bookaholicc.Adapters.ViewpagerAdapters.MainFragmentAdapter;
import com.bookaholicc.Adapters.ViewpagerAdapters.SwipeAdapterNewArrivals;
import com.bookaholicc.CustomUI.InkPageIndicator;
import com.bookaholicc.DataHandler.HomePageDataHandler;
import com.bookaholicc.Fragments.HomeFragement;
import com.bookaholicc.Model.Combo;
import com.bookaholicc.Model.Product;
import com.bookaholicc.R;
import com.bookaholicc.StorageHelpers.CartHandler;
import com.bookaholicc.StorageHelpers.DataStore;
import com.bookaholicc.utils.APIUtils;
import com.bookaholicc.utils.BundleKey;
import com.bookaholicc.utils.NetworkUtils;
import com.bookaholicc.utils.RVdecorator;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.bookaholicc.R.attr.layoutManager;
import static com.bookaholicc.R.attr.logo;


/**
 * Created by nandhu on 30/5/17.
 * Diplayed in {@link HomeFragement}
 * provided by {@link MainFragmentAdapter}
 */

public  class NewArrivalsFragment extends Fragment implements SwipeAdapterNewArrivals.ComboInterface, NewArrivalListAdapter.NewArrvialsListCallback, HomePageDataHandler.homeDataCallbacks {


    private Context mContext;
    private View mView;
    @BindView(R.id.new_arrivals_root)
    FrameLayout mRootFrame;
    private String TAG = "BK NEW ARRIVALS";



    private List<Product> mParsedProducts;

    private List<Combo> mParsedCombo;
    HomePageDataHandler mDataHandler;
    Unbinder unbinder;
    NewArrivalsInterface mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            mContext = context;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.new_arrivals_fragment,container,false);
        unbinder = ButterKnife.bind(this,mView);

        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mContext != null){
            mContext = null;
            unbinder.unbind();

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mDataHandler == null){
            mDataHandler = new HomePageDataHandler(getContext(),this);
            mDataHandler.makeRequests();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }



    /** Got the Response here
     * Remove the Loading view and Show the Recycler view
     * with Data Sources
     *
     * */




    @Override
    public void ComboproductClicked(Product p) {
        //Pass it as a String to View Product Activity
        Gson gson = new Gson();
//
        String productListString = gson.toJson(mParsedCombo);
        Log.d(TAG, "showProduct: Converted Product List to String :"+productListString);
        Intent i = new Intent(mContext, ViewProductActivity.class);
//        //// TODO: 20/6/17 Implement Transitions
        i.putExtra(BundleKey.ARG_COMBO_LIST,productListString);

        mContext.startActivity(i);
    }

    @Override
    public void dataLoaded(List<Product> mProductsList, List<Combo> mComboList) {
        View mInfaltedView = null;

        if (mRootFrame != null & mRootFrame.isShown()){

             mInfaltedView = View.inflate(mContext,R.layout.new_arrivals_content,mRootFrame);
        }


        /*-----------------------*/



        InkPageIndicator mIndicator = (InkPageIndicator) mInfaltedView.findViewById(R.id.indicator);

        SwipeAdapterNewArrivals mAdapter1 = new SwipeAdapterNewArrivals(mComboList,mContext,this);
        ViewPager mTopPager = (ViewPager) mInfaltedView.findViewById(R.id.home_top_pager);
        if (mTopPager != null) {

            mTopPager.setAdapter(mAdapter1);
        }
        if (mIndicator != null && mTopPager != null && mTopPager.isShown()) {

            mIndicator.setViewPager(mTopPager);
        }
        /*-------PREPARING FOR BOTTOM LIST---------*/
        RecyclerView mListView = (RecyclerView) mInfaltedView.findViewById(R.id.new_arrivals_list);
        mListView.setLayoutManager(new GridLayoutManager(mContext,2));
        if (mListView != null && mListView.isShown()){
            NewArrivalListAdapter mAdapter = new NewArrivalListAdapter(mContext,mProductsList,this);
            mListView.setAdapter(mAdapter);
            mListView.setNestedScrollingEnabled(false);
            RVdecorator dividerItemDecoration = new RVdecorator(ContextCompat.getDrawable(mContext,R.drawable.divider));
            mListView.addItemDecoration(dividerItemDecoration);
            mParsedProducts = mProductsList;
        }
        mParsedCombo = mComboList;
    }

    @Override
    public void noDataLoaded() {
        Toast.makeText(mContext,"Some Error Occurred , Try Again..",Toast.LENGTH_LONG).show();

    }

    @Override
    public void showProduct(NewArrivalListAdapter.NewArrivalListItem item, int pos, int pid) {


    }

    public interface NewArrivalsInterface{
        void showProduct(int pid,View mView);
        void comboClicked(int comboId,View mView);
    }
}
