package reply_1988.wanandroid.knowledgesystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.data.model.KSDetailData;
import reply_1988.wanandroid.data.model.KnowledgeSystemData;

import reply_1988.wanandroid.interfaces.OnKSClickListener;
import reply_1988.wanandroid.search.SearchActivity;

public class KSAdapter extends RecyclerView.Adapter<KSAdapter.ViewHolder> {

    private KnowledgeSystemData mKnowledgeSystemData;

    private OnKSClickListener mKSClickListener;

    public KSAdapter(KnowledgeSystemData knowledgeSystemData) {
        mKnowledgeSystemData = knowledgeSystemData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KSAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(mKnowledgeSystemData.getData().get(position).getName());
        List<KSDetailData> childrenBeanList = mKnowledgeSystemData.getData().get(position).getChildren();

        holder.mFlowLayout.setAdapter(new TagAdapter<KSDetailData>(childrenBeanList) {
            @Override
            public View getView(FlowLayout parent, int position, KSDetailData detailData) {
                TextView textView = (TextView) LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.text_ks_item, parent, false);
                textView.setText(detailData.getName());
                return textView;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mKnowledgeSystemData.getData().size();
    }


    /**
     * 更新体系数据
     * @param knowledgeSystemData 从服务器获取的最新体系数据
     */
    public void update(KnowledgeSystemData knowledgeSystemData) {
        mKnowledgeSystemData.getData().clear();
        mKnowledgeSystemData.getData().addAll(knowledgeSystemData.getData());
        notifyDataSetChanged();
        notifyItemRemoved(mKnowledgeSystemData.getData().size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;
        public TagFlowLayout mFlowLayout;

        public ViewHolder(View itemView) {

            super(itemView);
            mTextView = itemView.findViewById(R.id.text_category);
            mFlowLayout = itemView.findViewById(R.id.flowLayout_category);
            mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    mKSClickListener.onClick();
                    return true;
                }
            });
        }
    }

    public void setOnKSClickListener(OnKSClickListener clickListener) {

        mKSClickListener = clickListener;
    }
}
