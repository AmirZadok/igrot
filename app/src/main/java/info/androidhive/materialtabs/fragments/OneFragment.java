package info.androidhive.materialtabs.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.activity.AsyncBook;
import info.androidhive.materialtabs.activity.CreateList;
import info.androidhive.materialtabs.activity.SavedArticle;

import static android.content.Context.MODE_PRIVATE;


public class OneFragment extends Fragment{
    private ArrayList<SavedArticle> articleList = new ArrayList<SavedArticle>();
    String [] book_type ;
    private ArrayList<String[]> articles;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    ArrayAdapter bookAdapter,igeretAdapter;
    Spinner bookSpinner,valueSpinner;
    String currentBook = "";
    String currentArticle = "";
    private int bookOpen ;
    private int articleOpen ;


    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        CreateList booktytles=new CreateList();
        book_type=booktytles.getBook_type();
        articles=booktytles.getArticles();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one, container, false);

         bookSpinner = (Spinner) v.findViewById(R.id.book_spinner);
         valueSpinner = (Spinner) v.findViewById(R.id.igeret_spinner);

        bookAdapter = new ArrayAdapter <String> (this.getActivity(),android.R.layout.simple_spinner_item, book_type);
        bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookSpinner.setAdapter(bookAdapter);
        bookSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getSelectedItem().equals(book_type[i])) {

                    igeretAdapter = new ArrayAdapter  <String>(getActivity(),android.R.layout.simple_spinner_item, articles.get(i));
                    igeretAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    valueSpinner.setAdapter(igeretAdapter);
                    bookOpen=i;
                    currentBook = book_type[i];


                }
                else onNothingSelected(adapterView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        valueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getSelectedItem().equals(articles.get(bookOpen)[i])) {
                    currentArticle = articles.get(bookOpen)[i];
                    articleOpen = i;
                getByRequest(String.valueOf(bookOpen),String.valueOf(i));


                }
                else onNothingSelected(adapterView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        FloatingActionButton myFab = (FloatingActionButton)  v.findViewById(R.id.addTofavourites);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Toast.makeText(getActivity(),"Add to favourites!",Toast.LENGTH_SHORT).show();
              //  Toast.makeText(getActivity(),currentBook+"-"+currentArticle,Toast.LENGTH_SHORT).show();

               // Toast.makeText(getActivity(),articleList.get(0).toString(),Toast.LENGTH_SHORT).show();


                SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                SharedPreferences preferences = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String savedArticles = preferences.getString("savedArticles", null);

                    Type listType = new TypeToken<ArrayList<SavedArticle>>(){}.getType();

                    ArrayList<SavedArticle> articleList = new Gson().fromJson(savedArticles, listType);
                    articleList.add(new SavedArticle(currentBook,currentArticle,bookOpen,articleOpen));







                Gson gson = new Gson();
                String articleJSON = gson.toJson(articleList);

                editor.putString("savedArticles", articleJSON);

               editor.commit();


            }
        });

        return v;

    }





    public void setText(String text){
        TextView textView = (TextView) getView().findViewById(R.id.allBooksText);
        textView.setText(text);
    }

    public void getByRequest(String bookId,String volumeId){


        try {

            String answer=new AsyncBook().execute("http://amirzadok1234.esy.es/api.php?" + "bookId=" + bookId + "&volumeId=" + volumeId).get();
//            System.out.println("http://amirzadok1234.esy.es/api.php?" + "bookId=" + bookId + "&volumeId=" + volumeId);
            setText(answer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
