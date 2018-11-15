package com.mylove.launcher.mvp.ui.fragment;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.utils.DataHelper;
import com.mylove.launcher.R;
import com.mylove.launcher.R2;
import com.mylove.launcher.app.utils.SystemUtils;
import com.mylove.launcher.focus.FocusBorder;
import com.mylove.launcher.mvp.ui.adapter.CommonRecyclerViewAdapter;
import com.mylove.launcher.mvp.ui.adapter.CommonRecyclerViewHolder;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/3.
 */

public class MoreFragment extends DialogFragment {

    public static final String MORE_FRAGMENT = "more";
    public static final String CHECK_FRAGMENT = "launcher_check";
    public static final String SELECT_FRAGMENT = "select";

    private static MoreFragment moreFragment;
    private View view;

    TvRecyclerView tvRecyclerView;

    protected FocusBorder mFocusBorder;
    private CommonRecyclerViewAdapter mAdapter;

    List<PackageInfo> packageInfos;
    private PackageManager packageManager;

    private String type;

    private String data;
    public void setType(String type) {
        this.type = type;
        if(MoreFragment.SELECT_FRAGMENT.equals(type)){
            data = DataHelper.getStringSF(getActivity(),"Home_Shortcut");
        }
    }

    public MoreFragment() {
        // Required empty public constructor
    }

    public void setPackageInfos(List<PackageInfo> packageInfos) {
        if(this.packageInfos == null){
            this.packageInfos = new ArrayList<PackageInfo>();
        }
        this.packageInfos.clear();
        if(packageInfos != null && packageInfos.size() > 1){
            this.packageInfos.addAll(packageInfos);
            this.packageInfos.remove(packageInfos.size() - 1);
        }
    }

    public static MoreFragment newInstance() {
        if (moreFragment == null){
            moreFragment = new MoreFragment();
        }
        return moreFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME,R.style.launcher_full_screen_more);
        packageManager = getActivity().getPackageManager();
    }

    private void initFocusBorder(ViewGroup viewGroup) {
        if(null == mFocusBorder) {
            mFocusBorder = new FocusBorder.Builder()
                    .asColor()
                    .borderColor(getResources().getColor(R.color.public_white))
                    .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 3)
//                    .shadowColor(getResources().getColor(R.color.public_white))
                    .animDuration(180L)
                    .build(viewGroup);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.launcher_more_fragment,container);
        initFocusBorder((ViewGroup)view);
        tvRecyclerView = (TvRecyclerView)view.findViewById(R.id.more_recycle_view);
        tvRecyclerView.setSpacingWithMargins(10, 10);
        setListener();
        mAdapter = new CommonRecyclerViewAdapter<PackageInfo>(getActivity()) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.launcher_more_item;
            }

            @Override
            public void onBindItemHolder(CommonRecyclerViewHolder helper, PackageInfo item, int position) {
                if(MoreFragment.SELECT_FRAGMENT.equals(type)){
                    if(data.contains(item.applicationInfo.packageName+";")){
                        View view = helper.getHolder().getView(R.id.launcher_item_check);
                        view.setVisibility(View.VISIBLE);
                    }
                }else if(MoreFragment.CHECK_FRAGMENT.equals(type)){

                }
                helper.getHolder().setImageDrawable(R.id.launcher_item_icon,packageManager.getApplicationIcon(item.applicationInfo));
                String name = (String) packageManager.getApplicationLabel(item.applicationInfo);
                helper.getHolder().setText(R.id.launcher_item_name,name);

            }
        };
        mAdapter.setDatas(packageInfos);
        tvRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private void setListener() {
        tvRecyclerView.setOnItemListener(new SimpleOnItemListener() {
            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.05f, 10);
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                if(MoreFragment.CHECK_FRAGMENT.equals(type)){
                    if(checkListener != null){
                        checkListener.onItemClick(parent,itemView,position,mAdapter.getItem(position));
                    }
                }else if(MoreFragment.SELECT_FRAGMENT.equals(type)){
                    if(selectListener != null){
                        selectListener.onItemClick(parent,itemView,position,mAdapter.getItem(position));
                    }
                }else if(MoreFragment.MORE_FRAGMENT.equals(type)){
                    PackageInfo info = (PackageInfo)mAdapter.getItem(position);
                    Log.e("MoreFragment",info.applicationInfo.packageName);
                    SystemUtils.openApk(getActivity(),info.applicationInfo.packageName);
                }
            }
        });
    }

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if(null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(selectListener != null){
            selectListener.onDismiss();
        }
        moreFragment = null;
    }

    private CheckListener checkListener;
    public interface CheckListener{
        void onItemClick(TvRecyclerView parent, View itemView, int position, Object item);
    }

    public CheckListener getCheckListener() {
        return checkListener;
    }

    public void setCheckListener(CheckListener checkListener) {
        this.checkListener = checkListener;
    }


    private SelectListener selectListener;
    public interface SelectListener{
        void onItemClick(TvRecyclerView parent, View itemView, int position, Object item);
        void onDismiss();
    }

    public SelectListener getSelectListener() {
        return selectListener;
    }

    public void setSelectListener(SelectListener selectListener) {
        this.selectListener = selectListener;
    }
}
