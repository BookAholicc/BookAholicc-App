package com.bookaholicc.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bookaholicc.Adapters.CartAdapter;
import com.bookaholicc.Model.Product;
import com.bookaholicc.R;
import com.bookaholicc.StorageHelpers.CartHandler;
import com.bookaholicc.StorageHelpers.DataStore;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 29/5/17.
 *
 * Shows Cart Products -- Uses {@link CartHandler} to provide with List of Products that the User has Added
 */

public class CartFragment extends Fragment implements CartAdapter.CartCallbacks, CompoundButton.OnCheckedChangeListener {



    /*The Rooot of this page*/
    @BindView(R.id.cart_root)
    FrameLayout mCartRoot;
    @BindView(R.id.no_order_image)
    ImageView mCartImage;
    @BindView(R.id.no_order_text)
    TextView mOrderText;
    @BindView(R.id.no_orders_loading_indicator)
    ProgressBar mProgess;
    @BindView(R.id.no_orderindicator_text)
    TextView mIndicatorText;
    @BindView(R.id.no_order_loading_frame)
    RelativeLayout mLoadingFrame;
    @BindView(R.id.no_orders_root_frame)
    FrameLayout mRootFragme;

    private Context mContext;
    private View v;


    public List<Product> mCartList;
    private String TAG = "CARTFRAGMENT";
    private boolean isDefaultChecked = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = LayoutInflater.from(mContext).inflate(R.layout.cart_fragment, container, false);
        ButterKnife.bind(this, v);


        //get Prdoucts from Storage
        mCartList = CartHandler.getInstance(mContext).getProducts();


        if (mCartList == null){
            //No Cart Products Exists
            NoCartProducts();
        }
        else{
            // Products DO Exist
            showCartProducts(mCartList);
        }
        return v;
    }

    private void NoCartProducts() {

    }

    private void showCartProducts(List<Product> mCartList) {

        //There is Products Remove the Views and Show the it in Recycelr view
        View cartListView = null;

        if (mCartRoot != null){
            try {


                // Inflate The views by Showing Products
                mCartRoot.removeAllViews();
                cartListView = LayoutInflater.from(mContext).inflate(R.layout.cart_view,mCartRoot,false);
                RecyclerView mList = (RecyclerView) cartListView.findViewById(R.id.list);

                // Initilized Top Cards values
                TextView mProductsCount = (TextView) cartListView.findViewById(R.id.cart_view_product_count);
                TextView mAmount = (TextView) cartListView.findViewById(R.id.cart_view_amount_value);
                Button mPlaceOrder = (Button) cartListView.findViewById(R.id.place_order_button);
                mProductsCount.setText(mCartList.size());

                CheckBox mDefaultChekcbox = (CheckBox) cartListView.findViewById(R.id.cart_view_check_box);

                // Check whether Locatin exisits or not
                DataStore mStore = DataStore.getStorageInstance(mContext);
                if (mStore.isFirstTime()){

                    // change the Text to Select delivery Location
                    mDefaultChekcbox.setText("Select Default Delivery Location");

                }
                else{
                    //Leave i
                }




                String totalAmount = getAmount(mCartList);
                mAmount.setText(totalAmount);
                CartAdapter mAdapter = new CartAdapter(mCartList,mContext,this);

                mList.setLayoutManager(new LinearLayoutManager(mContext));
                mList.setAdapter(mAdapter);
            }
            catch (Exception e){
                Log.d(TAG, "showCartProducts: Exception: "+e.getLocalizedMessage());
            }
        }

    }

    private void placeOrder() {
        //Here are The List of products
        //Check whether Check box Check or Not
        //Check whether Location has been saved on not

        if (isDefaultChecked){
            //check whetehr Lat lng Exists or Not
        }


    }

    private String getAmount(List<Product> mCartList) {
        String totalSum ="";
        for (int i=0;i<mCartList.size();i++){
            totalSum = totalSum + mCartList.get(i).getOru_price();
        }

        return totalSum;
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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void removeProduct(int pos, Product p) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        // IF First Time Checked then it is
        // Then show Map Picker Activity
        if (compoundButton.getId() == R.id.cart_view_check_box && b){
            //Check Box Clicked
            isDefaultChecked = b;

        }
        else{

            isDefaultChecked = false;


        }
    }
}
