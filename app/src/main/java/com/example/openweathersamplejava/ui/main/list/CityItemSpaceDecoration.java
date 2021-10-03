package com.example.openweathersamplejava.ui.main.list;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CityItemSpaceDecoration extends RecyclerView.ItemDecoration {

  private final int space;

  public CityItemSpaceDecoration(int space) {
    this.space = space;
  }

  @Override
  public void getItemOffsets(Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                             @NonNull RecyclerView.State state) {
    outRect.bottom = space;
  }
}