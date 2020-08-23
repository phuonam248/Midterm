package com.example.midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    private ArrayList<News> _newsList;
    private NewsArrayAdapter _newsArrayAdapter;
    News _selectedNews;

    private ListView _newsListView;
    private TextView _selectedNewsDate;
    private TextView _selectedNewsTitle;
    private TextView _selectedNewsShortDes;
    private TextView _selectedNewsAuthorName;
    private ImageView _selectedNewsAuthorImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initComponents();
        loadData();
        if (_newsList.size() > 0)
            setUpStartingSelectedNews(_selectedNews);
        setUpNewsListView();
    }

    private void loadData() {
        _newsList.add(new News("Aug. 20, 2020, at 12:06 p.m",
                "Orlando Hospital Cook Honors Health Care Heroes With Portraits",
                "K. Aleisha Fetters",
                "The self-taught artist used a special technique to burn the images of six health workers into plywood.\n",
                R.drawable.author_2,
                R.drawable.background_2,
                "https://health.usnews.com/hospital-heroes/articles/orlando-hospital-cook-honors-health-care-heroes-with-portraits"));

        _newsList.add(new News("Feb. 20, 2020, at 12:06 p.m",
                "Does coffee raise blood pressure?",
                "Louisa Richards",
                "Research about coffee and blood pressure is conflicting. However, it seems that how often a person drinks coffee could influence its effect on blood pressure",
                R.drawable.author_1,
                R.drawable.background_1,
                "https://www.medicalnewstoday.com/articles/does-coffee-raise-blood-pressure#does-it"));

        _newsList.add(new News("Aug. 19, 2020, at 2:52 p.m.",
                "Noom vs. Mediterranean Diet: What's the Difference?",
                "Elaine K. Howley",
                "Both take a holistic approach to being and eating healthy.",
                R.drawable.author_3,
                R.drawable.background_3,
                "https://health.usnews.com/wellness/compare-diets/articles/noom-vs-mediterranean-diet-whats-the-difference"));

        _newsList.add(new News("Aug. 17, 2020, at 2:23 p.m.",
                "8 Healthy Drinks Rich in Electrolytes",
                "Ruben Castaneda",
                "The self-taught artist used a special technique to burn the images of six health workers into plywood.\n",
                R.drawable.author_4,
                R.drawable.background_4,
                "https://health.usnews.com/wellness/slideshows/healthy-drinks-rich-in-electrolytes"));

        _newsList.add(new News("Aug. 20, 2020, at 12:06 p.m",
                "Orlando Hospital Cook Honors Health Care Heroes With Portraits",
                "K. Aleisha Fetters",
                "The self-taught artist used a special technique to burn the images of six health workers into plywood.\n",
                R.drawable.author_2,
                R.drawable.background_2,
                "https://health.usnews.com/hospital-heroes/articles/orlando-hospital-cook-honors-health-care-heroes-with-portraits"));

        _newsList.add(new News("Aug. 20, 2020, at 12:06 p.m",
                "Orlando Hospital Cook Honors Health Care Heroes With Portraits",
                "K. Aleisha Fetters",
                "The self-taught artist used a special technique to burn the images of six health workers into plywood.\n",
                R.drawable.author_2,
                R.drawable.background_2,
                "https://health.usnews.com/hospital-heroes/articles/orlando-hospital-cook-honors-health-care-heroes-with-portraits"));

        _newsList.add(new News("Aug. 20, 2020, at 12:06 p.m",
                "Orlando Hospital Cook Honors Health Care Heroes With Portraits",
                "K. Aleisha Fetters",
                "The self-taught artist used a special technique to burn the images of six health workers into plywood.\n",
                R.drawable.author_2,
                R.drawable.background_2,
                "https://health.usnews.com/hospital-heroes/articles/orlando-hospital-cook-honors-health-care-heroes-with-portraits"));

        _newsList.add(new News("Aug. 20, 2020, at 12:06 p.m",
                "Orlando Hospital Cook Honors Health Care Heroes With Portraits",
                "K. Aleisha Fetters",
                "The self-taught artist used a special technique to burn the images of six health workers into plywood.\n",
                R.drawable.author_2,
                R.drawable.background_2,
                "https://health.usnews.com/hospital-heroes/articles/orlando-hospital-cook-honors-health-care-heroes-with-portraits"));

        _selectedNews = _newsList.get(0);
    }

    private void setUpNewsListView() {
        _newsListView = findViewById(R.id.newsListView);
        _newsArrayAdapter = new NewsArrayAdapter(this, R.layout.news_row_layout, _newsList);
        _newsArrayAdapter.setOnNewsClickInterface(new NewsArrayAdapter.OnNewsClickInterface() {
            @Override
            public void onNewsAdapterClick(News selectedNews) {
                _selectedNews = selectedNews;
                setUpStartingSelectedNews(selectedNews);
            }
        });
        _newsListView.setAdapter(_newsArrayAdapter);
    }

    private void setUpStartingSelectedNews(News selectedNews) {
        _selectedNewsShortDes.setText(selectedNews.getShortDes());
        _selectedNewsAuthorImage.setImageResource(selectedNews.getAuthorPhotoId());
        _selectedNewsAuthorName.setText(selectedNews.getAuthor());
        _selectedNewsDate.setText(selectedNews.getDate());
        _selectedNewsTitle.setText(selectedNews.getTitle());

        LinearLayout selectedNewsWrapper = findViewById(R.id.selectedNewsWrapper);
        selectedNewsWrapper.setBackgroundResource(selectedNews.getBackgroundPhotoId());
    }

    private void initComponents() {
        _selectedNewsAuthorImage = findViewById(R.id.selectedNewsAuthorImage);
        _selectedNewsAuthorName = findViewById(R.id.selectedNewsAuthorName);
        _selectedNewsTitle = findViewById(R.id.selectedNewsTitle);
        _selectedNewsDate = findViewById(R.id.selectedNewsDate);
        _selectedNewsShortDes = findViewById(R.id.selectedNewsShortDes);
        _newsList = new ArrayList<>();
    }

    public void startingNews_onClick(View view) {
        Intent intent = new Intent(NewsActivity.this, WebViewActivity.class);
        intent.putExtra("url", _selectedNews.getUri());
        startActivity(intent);
    }
}