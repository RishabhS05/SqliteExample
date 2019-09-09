package com.example.sqliteexample.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqliteexample.MyAdapter;
import com.example.sqliteexample.R;
import com.example.sqliteexample.db.SQLiteHelper;
import com.example.sqliteexample.listerners.IClicklistener;
import com.example.sqliteexample.models.Hero;

import java.util.ArrayList;
import java.util.List;

import static com.example.sqliteexample.Utils.CREATEDBY;
import static com.example.sqliteexample.Utils.FIRST_APP;
import static com.example.sqliteexample.Utils.ID;
import static com.example.sqliteexample.Utils.NAME;
import static com.example.sqliteexample.Utils.PUBLISHER;
import static com.example.sqliteexample.Utils.REALNAME;
import static com.example.sqliteexample.Utils.TABLE_NAME;
import static com.example.sqliteexample.Utils.URL;

public class AvengersDetails extends Fragment implements IClicklistener {
    RecyclerView recyclerView;
    List<Hero> heroes = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag, null);
        recyclerView = view.findViewById(R.id.detail_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        MyAdapter adapter = new MyAdapter(heroes, getActivity(), this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAlldetails();

    }

    public void getAlldetails() {
        heroes.clear();
        SQLiteHelper helper = new SQLiteHelper(getActivity());
        SQLiteDatabase db = helper.getReadableDatabase();
        String cols[] = {ID, NAME, REALNAME, FIRST_APP, CREATEDBY, PUBLISHER, URL};
        Cursor cursor = db.query(TABLE_NAME, cols, null, null, null, null, null);
        if (!cursor.isAfterLast()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                heroes.add(new Hero(cursor.getInt(cursor.getColumnIndex(ID)),
                        cursor.getString(cursor.getColumnIndex(NAME)),
                        cursor.getString(cursor.getColumnIndex(REALNAME)),
                        cursor.getString(cursor.getColumnIndex(FIRST_APP)),
                        cursor.getString(cursor.getColumnIndex(CREATEDBY)),
                        cursor.getString(cursor.getColumnIndex(PUBLISHER)),
                        cursor.getString(cursor.getColumnIndex(URL))));
                cursor.moveToNext();
            }
        } else {
            Toast.makeText(getActivity(), "no data in db", Toast.LENGTH_SHORT).show();
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public String toString() {
        return AvengersDetails.class.getSimpleName();
    }

    @Override
    public void onclick(Hero hero, int flag) {
        if (flag == UPDATE) {
            AddHeroProfile addHeroProfile = new AddHeroProfile(hero);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, addHeroProfile)
                    .addToBackStack("update")
                    .commit();
        } else if (flag == DELETE) {
            delete(hero.getId());
            getAlldetails();
            recyclerView.getAdapter().notifyDataSetChanged();
        }

    }

    public void delete(int id) {
        SQLiteHelper helper = new SQLiteHelper(getActivity());
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{"" + id});
        db.close();
    }

}
