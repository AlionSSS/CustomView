package com.example.originalview.activity;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.originalview.R;

import java.util.ArrayList;

/**
 * 下拉栏 Activity
 *
 * @author ALion
 */
public class DropDownActivity extends BaseActivity {

    private ImageView ivDrop;
    private ListView lvList;
    private EditText etContent;
    private ArrayList<String> mList;
    private PopupWindow mPopup;

    @Override
    public void initView() {
        setContentView(R.layout.activity_drop_down);

        ivDrop = (ImageView) findViewById(R.id.iv_drop);
        etContent = (EditText) findViewById(R.id.et_content);
        lvList = new ListView(this);
    }

    @Override
    public void initListener() {
        ivDrop.setOnClickListener(this);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                etContent.setText(mList.get(position));
                mPopup.dismiss();
            }
        });
    }

    @Override
    public void initData() {
        //初始化listView数据
        mList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mList.add("aabbcc" + i);
        }
        lvList.setAdapter(new MyAdapter());
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.iv_drop:
                showDropDown();
                break;
        }
    }

    private void showDropDown() {
        if (mPopup == null) {
            mPopup = new PopupWindow(lvList,
                    etContent.getWidth(), 250, true);
            mPopup.setBackgroundDrawable(new ColorDrawable());
        }
        mPopup.showAsDropDown(etContent);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public String getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(DropDownActivity.this, R.layout.item_drop_list, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews((String) getItem(position), (ViewHolder) convertView.getTag(), position);
            return convertView;
        }

        private void initializeViews(String item, ViewHolder holder, final int position) {
            holder.tvContent.setText(item);

            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        protected class ViewHolder {

            private final TextView tvContent;
            private final ImageView ivDelete;

            public ViewHolder(View view) {
                tvContent = (TextView) view.findViewById(R.id.tv_content);
                ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
            }
        }
    }
}
