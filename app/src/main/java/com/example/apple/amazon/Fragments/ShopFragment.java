package com.example.apple.amazon.Fragments;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apple.amazon.Adapter.AlbumsAdapter;
import com.example.apple.amazon.Model.Data;
import com.example.apple.amazon.R;

import java.util.ArrayList;

public class ShopFragment extends Fragment {
    RecyclerView recyclerView;
    private ArrayList<Data> albumList;
    private AlbumsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(getActivity(), albumList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ShopFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();
    }

    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.book,
                R.drawable.book2,
                R.drawable.book,
                R.drawable.book2,
                R.drawable.book,
                R.drawable.book2,
                R.drawable.book,
                R.drawable.book2,
                R.drawable.book,
                R.drawable.book2};

        Data a = new Data("True Romance", String.valueOf(covers[0]));
        albumList.add(a);

        a = new Data("Xscpae", String.valueOf(covers[1]));
        albumList.add(a);

        a = new Data("Maroon 5", String.valueOf(covers[2]));
        albumList.add(a);

        a = new Data("Born to Die", String.valueOf(covers[3]));
        albumList.add(a);

        a = new Data("Honeymoon", String.valueOf(covers[4]));
        albumList.add(a);

        a = new Data("I Need a Doctor", String.valueOf(covers[5]));
        albumList.add(a);

        a = new Data("Loud", String.valueOf(covers[6]));
        albumList.add(a);

        a = new Data("Legend", String.valueOf(covers[7]));
        albumList.add(a);

        a = new Data("Hello", String.valueOf(covers[8]));
        albumList.add(a);

        a = new Data("Greatest Hits", String.valueOf(covers[9]));
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
