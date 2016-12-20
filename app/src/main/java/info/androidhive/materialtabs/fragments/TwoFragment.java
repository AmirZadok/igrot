package info.androidhive.materialtabs.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.activity.SavedArticle;

import static android.content.Context.MODE_PRIVATE;


public class TwoFragment extends Fragment{

    private ArrayAdapter<SavedArticle> listAdapter ;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private String articleJSON;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.print("i created");


    }

    @Override
    public void onResume() {


      System.out.println("onResume");


        super.onResume();
    }

    @Override
    public void onPause() {
        System.out.println("onPause");
        super.onPause();
    }



public void print(){


}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        ListView mainListView = (ListView) view.findViewById( R.id.myArticlesListView );
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View item,
                                     int position, long id) {
                SavedArticle savedarticle = listAdapter.getItem( position );
                savedarticle.toggleChecked();
                articleViewHolder viewHolder = (articleViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked( savedarticle.isChecked() );
            }
        });

        SharedPreferences preferences = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        articleJSON = preferences.getString("savedArticles", null);
        Type listType = new TypeToken<ArrayList<SavedArticle>>(){}.getType();
        ArrayList<SavedArticle> articleList = new Gson().fromJson(articleJSON, listType);
                
            // Set our custom array adapter as the ListView's adapter.
            listAdapter = new articleArrayAdapter(getActivity(), articleList);
            mainListView.setAdapter(listAdapter);


        return view;
    }




    /** Holds child views for one row. */
    private static class articleViewHolder {
        private CheckBox checkBox ;
        private TextView textView ;
        public articleViewHolder() {}
        public articleViewHolder( TextView textView, CheckBox checkBox ) {
            this.checkBox = checkBox ;
            this.textView = textView ;
        }
        public CheckBox getCheckBox() {
            return checkBox;
        }
        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }
        public TextView getTextView() {
            return textView;
        }
        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }

    /** Custom adapter for displaying an array of Planet objects. */
    private static class articleArrayAdapter extends ArrayAdapter<SavedArticle> {

        private LayoutInflater inflater;

        public articleArrayAdapter( Context context, List<SavedArticle> articleList ) {
            super( context, R.layout.my_book_simplerow, R.id.rowTextView, articleList );
            // Cache the LayoutInflate to avoid asking for a new one each time.
            inflater = LayoutInflater.from(context) ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // savedarticle to display
            SavedArticle savedarticle = this.getItem( position );

            // The child views in each row.
            CheckBox checkBox ;
            TextView textView ;

            // Create a new row view
            if ( convertView == null ) {
                convertView = inflater.inflate(R.layout.my_book_simplerow, null);

                // Find the child views.
                textView = (TextView) convertView.findViewById( R.id.rowTextView );
                checkBox = (CheckBox) convertView.findViewById( R.id.CheckBox01 );

                // Optimization: Tag the row with it's child views, so we don't have to
                // call findViewById() later when we reuse the row.
                convertView.setTag( new articleViewHolder(textView,checkBox) );

                // If CheckBox is toggled, update the planet it is tagged with.
                checkBox.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        SavedArticle savedarticle = (SavedArticle) cb.getTag();
                        savedarticle.setChecked( cb.isChecked() );
                    }
                });
            }
            // Reuse existing row view
            else {
                // Because we use a ViewHolder, we avoid having to call findViewById().
                articleViewHolder viewHolder = (articleViewHolder) convertView.getTag();
                checkBox = viewHolder.getCheckBox() ;
                textView = viewHolder.getTextView() ;
            }

            // Tag the CheckBox with the Planet it is displaying, so that we can
            // access the planet in onClick() when the CheckBox is toggled.
            checkBox.setTag( savedarticle );

            // Display planet data
            checkBox.setChecked( savedarticle.isChecked() );
            textView.setText( savedarticle.toString() );

            return convertView;
        }

    }

//    public Object onRetainCustomNonConfigurationInstance () {
//        return articles ;
//    }

    /**
     * Created by AZ on 12/19/2016.
     */
    

}





