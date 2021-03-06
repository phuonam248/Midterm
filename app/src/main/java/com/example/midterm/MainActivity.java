package com.example.midterm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.midterm.R.drawable.shape_4corners_layout;

public class MainActivity extends AppCompatActivity implements RecyclerMealAdapter.OnMealListener,
        RecyclerIngredientsAdapter.OnIngredientListener{
    private static final String TAG="MainActivity";
    private LinearLayoutManager _linearLayoutManager;
    private static List<DietMeals> _dietMeals;
    private static List<Ingredient> _ingredientList;
    private RecyclerMealAdapter _recyclerMealAdapter;
    private RecyclerView _mealRecyclerView;
    private RecyclerView _rclViewPicker;
    private RecyclerIngredientsAdapter _ingredientsAdapter;

    private List<RecyclerMealAdapter> _mealWeekAdapters;
    private static List<List<DietMeals>> _dietMealsWeek;
    private static int _dayID;
    private BottomNavigationView _bottomNavigationView;

    private static Helper helper=new Helper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        initListDietMeal();
        initWeekAdapter();
        _linearLayoutManager=
                new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        setRecyclerView();
    }

    private void setRecyclerView() {
        _recyclerMealAdapter=_mealWeekAdapters.get(_dayID);
        _mealRecyclerView=(RecyclerView)findViewById(R.id.recyclerViewMeal);
        _mealRecyclerView.setLayoutManager(_linearLayoutManager);
        _mealRecyclerView.setAdapter(_recyclerMealAdapter);
    }

    private void initWeekAdapter() {
        _dayID=0;
        _mealWeekAdapters=new ArrayList<>(6);
        _dietMealsWeek=new ArrayList<>(6);
        for (int i =0;i<6;i++){
            List<DietMeals> _dMeal= new ArrayList<>();
            RecyclerMealAdapter _rclMealAdapter= new RecyclerMealAdapter(_dMeal,
                    this,this);
            _dietMealsWeek.add(_dMeal);
            _mealWeekAdapters.add(_rclMealAdapter);
        }
    }

    private void initListDietMeal() {
        _dietMeals=new ArrayList<>();
        _dietMeals.add( new DietMeals("Bò xào dứa",
                "https://www.youtube.com/watch?v=KO0D9FMwUt0",
                helper.fromImageIdToInt("im_bo_xao_dua",this),
                20,60,60,90,132));
        _dietMeals.get(0).addIngredient(new Ingredient(
                helper.fromImageIdToInt("img_beef",this),
                "Thịt Bò","g",200));
        _dietMeals.get(0).addIngredient(new Ingredient(
                helper.fromImageIdToInt("img_pineapple",this),
                "Dứa","g",100));
        _dietMeals.get(0).addIngredient(new Ingredient(
                helper.fromImageIdToInt("img_black_pepper",this),
                "Tiêu xay","g",5));
        _dietMeals.get(0).addIngredient(new Ingredient(
                helper.fromImageIdToInt("img_sugar",this),
                "Đường","g",10));
        _dietMeals.get(0).addIngredient(new Ingredient(
                helper.fromImageIdToInt("img_salt",this),
                "Muối","g",10));
        _dietMeals.get(0).addIngredient(new Ingredient(
                helper.fromImageIdToInt("img_tomato",this),
                "Cà Chua","g",50));
        _dietMeals.add( new DietMeals("Yến mạch nấu chuối",
                "https://www.youtube.com/watch?v=KO0D9FMwUt0",
                helper.fromImageIdToInt("im_yen_mach_chuoi",this),
                10,0,0,7.6,150));
        _dietMeals.get(1).addIngredient(new Ingredient(
                helper.fromImageIdToInt("img_oat",this),
                "Yến Mạch","g",38));
        _dietMeals.get(1).addIngredient(new Ingredient(
                helper.fromImageIdToInt("img_banana",this),
                "Chuối","g",50));
    }

    @Override
    public void onMealItemClick(int position) {
        DietMeals _dMeal = _dietMealsWeek.get(_dayID).get(position);
        AlertDialog.Builder _builder = new AlertDialog.Builder(MainActivity.this);
        View _view=getLayoutInflater().inflate(R.layout.food_info_layout,null);
        _builder.setView(_view);
        AlertDialog dialog= _builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setLayout(1000,1700);
        initMealInfoLayout(_view,_dMeal);

    }

    private void initMealInfoLayout(View view, final DietMeals dMeal) {
        ImageView _imgMeal=(ImageView)view.findViewById(R.id.imgVMeal);
        TextView _txtMeal=(TextView)view.findViewById(R.id.textViewMeal);
        Button _btnYoutube= (Button)view.findViewById(R.id.buttonYoutube);
        ListView _listView= (ListView) view.findViewById(R.id.listViewIngredient);
        IngredientAdapter _adapter=
                new IngredientAdapter(this,0,dMeal.getIngridient());
        _listView.setAdapter(_adapter);
        _imgMeal.setImageResource(dMeal.getPhotoId());
        _txtMeal.setText(dMeal.getName());
        _btnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = helper.intentToVideoUrl(dMeal.getVideoUri());
                startActivity(intent);
            }
        });
    }

    public void btnFindFood_onClick(View view) {
        AlertDialog.Builder _builder = new AlertDialog.Builder(MainActivity.this);
        View _view=getLayoutInflater().inflate(R.layout.ingredient_picker_layout,null);
        _builder.setView(_view);
        AlertDialog dialog= _builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setLayout(900,1000);
        /* DUNG MO NO RA LAM GI */
        initIngredientsList();
        initRecyclerViewPicker(_view,dialog);
    }

    private void initIngredientsList() {
        _ingredientList=new ArrayList<>();
        _ingredientList.add(new Ingredient(
                helper.fromImageIdToInt("img_lime",this),
                "Chanh","",0));
        _ingredientList.add(new Ingredient(
                helper.fromImageIdToInt("img_apple",this),
                "Táo","",0));
        _ingredientList.add(new Ingredient(
                helper.fromImageIdToInt("img_banana",this),
                "Chuối","",0));
        _ingredientList.add(new Ingredient(
                helper.fromImageIdToInt("img_banana",this),
                "Chuối","",0));
        _ingredientList.add(new Ingredient(
                helper.fromImageIdToInt("img_banana",this),
                "Chuối","",0));
        _ingredientList.add(new Ingredient(
                helper.fromImageIdToInt("img_banana",this),
                "Chuối","",0));
        _ingredientList.add(new Ingredient(
                helper.fromImageIdToInt("img_lime",this),
                "Chanh","",0));
        _ingredientList.add(new Ingredient(
                helper.fromImageIdToInt("img_apple",this),
                "Táo","",0));
        _ingredientList.add(new Ingredient(
                helper.fromImageIdToInt("img_banana",this),
                "Chuối","",0));
        _ingredientList.add(new Ingredient(
                helper.fromImageIdToInt("img_banana",this),
                "Chuối","",0));
        _ingredientList.add(new Ingredient(
                helper.fromImageIdToInt("img_banana",this),
                "Chuối","",0));
        _ingredientList.add(new Ingredient(
                helper.fromImageIdToInt("img_banana",this),
                "Chuối","",0));
    }

    private void initRecyclerViewPicker(View view, final AlertDialog dialog) {

        _ingredientsAdapter = new RecyclerIngredientsAdapter(_ingredientList,
                this,this);
        GridLayoutManager _gridLayoutManager = new GridLayoutManager(this,5);
        _rclViewPicker= (RecyclerView)view.findViewById(R.id.recyclerViewPicker);
        _rclViewPicker.setLayoutManager(_gridLayoutManager);
        //_rclViewPicker.addItemDecoration(new RecyclerPickerDecoration(10));
        _rclViewPicker.setAdapter(_ingredientsAdapter);
        Button _buttonOk = view.findViewById(R.id.buttonChoose);
        _buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> _selectedIngredientName = _ingredientsAdapter.getSelectedName();
                DietMeals _result = helper.findProperDietMeal(
                        (ArrayList<String>) _selectedIngredientName,
                        (ArrayList<DietMeals>) _dietMeals);
                Log.d(TAG, "onClick: "+_result.getName());
                setResult(_result);
                dialog.cancel();
            }
        });
    }

    private void setResult(DietMeals result) {
        _dietMealsWeek.get(_dayID).add(result);
        _mealRecyclerView.getAdapter().notifyItemInserted(_dietMealsWeek.get(_dayID).size());
    }

    @Override
    public void onIngredientItemClick(View view,int position,ImageView imageView) {
        Ingredient _ingredient= _ingredientList.get(position);
        _ingredient.setCheck(!_ingredient.isChecked());
        imageView.setVisibility(_ingredient.isChecked()? view.VISIBLE: view.GONE);
    }

    public void btnDay_onClick(View view) {
        int _btnId=view.getId();
        switch (_btnId){
            case R.id.buttonMonday:
                _dayID=0;
                break;
            case R.id.buttonTuesday:
                _dayID=1;
                break;
            case R.id.buttonWednesday:
                _dayID=2;
                break;
            case R.id.buttonThursday:
                _dayID=3;
                break;
            case R.id.buttonFriday:
                _dayID=4;
                break;
            case R.id.buttonSaturday:
                _dayID=5;
                break;
        }
        _mealRecyclerView.setAdapter(_mealWeekAdapters.get(_dayID));
        Log.d(TAG, "btnDay_onClick: "+_dayID);
    }
}