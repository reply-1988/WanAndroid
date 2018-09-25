package reply_1988.wanandroid.knowledgesystem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.data.model.KnowledgeSystemData;
import reply_1988.wanandroid.interfaces.OnKSClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KSFragment extends Fragment implements KSContract.View{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;

    private KSAdapter mKSAdapter;
    private KSContract.Presenter mPresenter;

    public KSFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KSFragment.
     */
    public static KSFragment newInstance(String param1, String param2) {
        KSFragment fragment = new KSFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                public void onClick() {

                }
            });
        } else {
            mKSAdapter.update(knowledgeSystemData);
        }
        mRecyclerView.setAdapter(mKSAdapter);
    }
}
