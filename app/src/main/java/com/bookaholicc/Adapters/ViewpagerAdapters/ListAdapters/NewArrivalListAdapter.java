package com.bookaholicc.Adapters.ViewpagerAdapters.ListAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bookaholicc.CustomUI.WhitenyBooksFont;
import com.bookaholicc.Fragments.HomeFragments.NewArrivalsFragment;
import com.bookaholicc.Model.Product;
import com.bookaholicc.R;
import com.bookaholicc.utils.ScreenUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 31/5/17.
 * The Adapter used in {@link NewArrivalsFragment}
 *
 */

public class NewArrivalListAdapter extends RecyclerView.Adapter<NewArrivalListAdapter.NewArrivalListItem> {


    private Context mContext;
    private List<Product> mList;
    private NewArrvialsListCallback mCallback;


    public NewArrivalListAdapter(Context mContext, List<Product> mList, NewArrvialsListCallback mCallback) {
        this.mContext = mContext;
        this.mList = mList;
        this.mCallback = mCallback;

    }

    @Override
    public NewArrivalListItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewArrivalListItem(LayoutInflater.from(mContext).inflate(R.layout.product_grid_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final NewArrivalListItem holder, int position) {
        //First Set the image
        Glide.with(mContext)
                .load(mList.get(position).getImageURL())
                .into(holder.mProductImage);



        final int pos = position;
        holder.mAuthorName.setText(mList.get(pos).getAuthorName());
        holder.mProductName.setText(mList.get(pos).getProductName());
        final int pid = mList.get(pos).getPid();
        holder.mPriceText.setText(mList.get(pos).getOru_price());
        holder.mCompleteDuration.setText(mList.get(pos).getDuration());
        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCallback != null){
                    mCallback.showProduct(holder,pos,pid);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    /**
     * The View Holder Class for this list
     */
    public static class NewArrivalListItem extends RecyclerView.ViewHolder {
        ImageView mProductImage;
        WhitenyBooksFont mProductName;
        WhitenyBooksFont mAuthorName;
        WhitenyBooksFont mPriceText;
        WhitenyBooksFont mCompleteDuration;
        RelativeLayout mRootView;

        public NewArrivalListItem(View itemView) {
            super(itemView);
            mRootView  = (RelativeLayout)itemView.findViewById(R.id.na_item_root);
            mProductImage = (ImageView) itemView.findViewById(R.id.p_item_image);
            mProductName = (WhitenyBooksFont) itemView.findViewById(R.id.p_item_pname);
            mAuthorName = (WhitenyBooksFont) itemView.findViewById(R.id.p_item_addn_info);
            mPriceText = (WhitenyBooksFont) itemView.findViewById(R.id.p_item_price);
            mCompleteDuration = (WhitenyBooksFont) itemView.findViewById(R.id.p_item_com_duration);
        }
    }

    public interface NewArrvialsListCallback {
        void showProduct(NewArrivalListItem item , int pos, int pid);
    }
}