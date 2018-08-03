package com.corey.basic.tv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corey.basic.R;

/**
 * Created by caosanyang on 2018/7/18.
 */
public class ContentFragment extends Fragment {

  View rootView;

  TextView tvContent;

  public static final String ARG_CONTENT = "ARG_CONTENT";

  public static ContentFragment newInstance(String content) {
    Bundle args = new Bundle();
    args.putString(ARG_CONTENT, content);
    ContentFragment fragment = new ContentFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.fragment_content, container, false);
    return rootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    tvContent = rootView.findViewById(R.id.tv_content);
    tvContent.setText(getArguments().getString(ARG_CONTENT));
  }
}
