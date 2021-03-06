package reply_1988.wanandroid.knowledgesystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.data.model.KnowledgeSystemData;
import reply_1988.wanandroid.interfaces.OnKSClickListener;
import reply_1988.wanandroid.search.SearchActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KSFragment extends Fragment implements KSContract.View{

    private RecyclerView mRecyclerView;

    private KSAdapter mKSAdapter;
    private KSContract.Presenter mPresenter;

    public KSFragment() {
        // Required empty public constructor
    }

    public static KSFragment newInstance() {
        return new KSFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_ks, container, false);
        initView(v);
        mPresenter.getKSData();
        return v;
    }

    @Override
    public void setPresenter(KSContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycleView);
    }

    @Override
    public void setAdapter(KnowledgeSystemData knowledgeSystemData) {
        if (mKSAdapter == null) {
            mKSAdapter = new KSAdapter(knowledgeSystemData);
            mKSAdapter.setOnKSClickListener(new OnKSClickListener() {
                @Override
                public void onClick(int cid, String title) {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(SearchActivity.ARG_KS_CID, cid);
                    intent.putExtra(SearchActivity.ARG_TITLE, title);
                    startActivity(intent);
                }
            });
        } else {
            mKSAdapter.update(knowledgeSystemData);
        }
        mRecyclerView.setAdapter(mKSAdapter);
    }
}
