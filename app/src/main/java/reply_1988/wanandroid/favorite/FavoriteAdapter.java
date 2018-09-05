package reply_1988.wanandroid.favorite;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.data.model.ArticleDetailData;

import reply_1988.wanandroid.dummy.DummyContent.DummyItem;
import reply_1988.wanandroid.interfaces.OnArticleClickedListener;
import reply_1988.wanandroid.interfaces.OnCategoryClickedListener;

import java.util.List;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private final String ARTICLE_URL = "articleUrl";

    private final List<ArticleDetailData> mValues;

    private OnArticleClickedListener mOnArticleClickedListener;
    private OnCategoryClickedListener mOnCategoryClickedListener;

    public FavoriteAdapter(List<ArticleDetailData> items) {
        mValues = items;
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favoritet_item, parent, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.time.setText(holder.mItem.getNiceDate());
        holder.author.setText(holder.mItem.getAuthor());
        holder.category.setText(String.format("%s/%s", holder.mItem.getSuperChapterName(), holder.mItem.getChapterName()));
        holder.title.setText(holder.mItem.getTitle());
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

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCardView = mView.findViewById(R.id.card_view_layout);
            title = mView.findViewById(R.id.text_view_title);
            category = mView.findViewById(R.id.btn_category);
            author = mView.findViewById(R.id.text_view_author);
            time = mView.findViewById(R.id.text_view_time);
            mCardView.setOnClickListener(this);
            category.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.card_view_layout:
                    mOnArticleClickedListener.onClick(getAdapterPosition());
            }
        }
    }

    public void setOnArticleClickedListener(OnArticleClickedListener onArticleClickedListener) {
        mOnArticleClickedListener = onArticleClickedListener;
    }

    public void setOnCategoryClickedListener(OnCategoryClickedListener onCategoryClickedListener) {
        mOnCategoryClickedListener = onCategoryClickedListener;
    }

    public void updateAdapter(List<ArticleDetailData> detailDataList) {

        mValues.clear();
        mValues.addAll(detailDataList);
        notifyDataSetChanged();
        notifyItemRemoved(detailDataList.size());
    }
}
