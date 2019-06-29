package com.sc.bigboss.bigboss;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class SpacesItemDecoration extends RecyclerView.ItemDecoration {
  private final int padding;

  public SpacesItemDecoration(int padding) {
    this.padding = padding;
  }

  @Override public void getItemOffsets(
          Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    outRect.bottom = padding;
  }
}