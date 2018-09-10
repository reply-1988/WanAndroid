package reply_1988.wanandroid.timeline;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.interfaces.OnArticleClickedListener;
import reply_1988.wanandroid.interfaces.OnCancelCollectClickedListener;
import reply_1988.wanandroid.interfaces.OnCancelReadLaterClickedListener;
import reply_1988.wanandroid.interfaces.OnCategoryClickedListener;
import reply_1988.wanandroid.interfaces.OnCollectClickedListener;
import reply_1988.wanandroid.interfaces.OnReadLaterClickedListener;

import java.nio.file.Path;
import java.util.List;

public class TimerLineAdapter extends RecyclerView.Adapter<TimerLineAdapter.ViewHolder> {

    private final String ARTICLE_URL = "articleUrl";

    private final List<ArticleDetailData> mValues;

    private OnArticleClickedListener mOnArticleClickedListener;
    private OnCategoryClickedListener mOnCategoryClickedListener;
    private OnCollectClickedListener mOnCollectClickedListener;
    private OnCancelCollectClickedListener mOnCancelCollectClickedListener;
    private OnReadLaterClickedListener mOnReadLaterClickedListener;
    private OnCancelReadLaterClickedListener mOnCancelReadLaterClickedListener;



    public TimerLineAdapter(List<ArticleDetailData> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timeline_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.time.setText(holder.mItem.getNiceDate());
        holder.author.setText(holder.mItem.getAuthor());
        holder.category.setText(String.format("%s/%s", holder.mItem.getSuperChapterName(), holder.mItem.getChapterName()));
        holder.title.setText(holder.mItem.getTitle());
        if (mValues.get(position).isCollect()) {
            holder.collect.setImageResource(R.drawable.ic_collect);
        } else {
            holder.collect.setImageResource(R.drawable.ic_uncollect);
        }
        if (mValues.get(position).isReadLater()) {
            holder.readLater.setImageResource(R.drawable.ic_readlater);
        } else {
            holder.readLater.setImageResource(R.drawable.ic_unreadlater);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public CardView mCardView;
        public TextView title;
        public AppCompatButton category;
        public AppCompatTextView author;
        public AppCompatTextView time;
        public ArticleDetailData mItem;
        public ImageButton collect;
        public ImageButton readLater;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCardView = mView.findViewById(R.id.card_view_layout);
            title = mView.findViewById(R.id.text_view_title);
            category = mView.findViewById(R.id.btn_category);
            author = mView.findViewById(R.id.text_view_author);
            time = mView.findViewById(R.id.text_view_time);
            collect = mView.findViewById(R.id.imageButton);
            readLater = mView.findViewById(R.id.readLater);

            mCardView.setOnClickListener(this);
            category.setOnClickListener(this);
            collect.setOnClickListener(this);
            readLater.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.card_view_layout:
                    mOnArticleClickedListener.onClick(getAdapterPosition());
                    break;
                case R.id.imageButton:
                    if (mValues.get(getAdapterPosition()).isCollect()) {
                        mOnCancelCollectClickedListener.onClick(getAdapterPosition());
                    } else {
                        mOnCollectClickedListener.onClick(getAdapterPosition());
                    }
                    break;
                case R.id.readLater:
                    if (mValues.get(getAdapterPosition()).isReadLater()) {
                        mOnCancelReadLaterClickedListener.onClick(getAdapterPosition());
                    } else {
                        mOnReadLaterClickedListener.onClick(getAdapterPosition());
                    }
                    break;
            }
        }
    }

    public void setOnArticleClickedListener(OnArticleClickedListener onArticleClickedListener) {
        mOnArticleClickedListener = onArticleClickedListener;
    }

    public void setOnCategoryClickedListener(OnCategoryClickedListener onCategoryClickedListener) {
        mOnCategoryClickedListener = onCategoryClickedListener;
    }

    public void setOnCollectClickedListener(OnCollectClickedListener onCollectClickedListener) {

        mOnCollectClickedListener = onCollectClickedListener;
    }

    public void setOnCancelCollectClickedListener(OnCancelCollectClickedListener onCancelCollectClickedListener) {

        mOnCancelCollectClickedListener = onCancelCollectClickedListener;
    }

    public void setOnReadLaterClickedListener(OnReadLaterClickedListener onReadLaterClickedListener) {

        mOnReadLaterClickedListener = onReadLaterClickedListener;
    }

    public void setOnCancelReadLaterClickedListener(OnCancelReadLaterClickedListener onCancelReadLaterClickedListener) {

        mOnCancelReadLaterClickedListener = onCancelReadLaterClickedListener;
    }


    public void updateAdapter(List<ArticleDetailData> detailDataList) {

        mValues.clear();
        mValues.addAll(detailDataList);
        notifyDataSetChanged();
        notifyItemRemoved(detailDataList.size());
    }

}
