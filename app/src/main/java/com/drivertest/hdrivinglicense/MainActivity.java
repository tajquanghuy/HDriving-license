package com.drivertest.hdrivinglicense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.drivertest.hdrivinglicense.iap.activities.IAPActivity;
import com.drivertest.hdrivinglicense.model.Category;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textViewHightScore;
    private Spinner spinnerCategory;
    private Button buttonStartQuestion, btnSup;

    private int hightscore;
    private static final int REQUEST_CODE_QUESTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        //load chủ đề
        loadCategories();
        //load điểm
        loadHightScore();
        //click bắt đầu
        buttonStartQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuestion();
            }
        });

    }

    //load hiển thị
    private void loadHightScore() {
        SharedPreferences preferences = getSharedPreferences("share", MODE_PRIVATE);//Nhận lấy shared preference instance có tên là "share"
        hightscore = preferences.getInt("hightscore", 0);
        textViewHightScore.setText("Điểm cao: " + hightscore);
    }

    //Hàm bắt ddaauaf cầu hỏi qua activity question
    private void startQuestion() {
        //Lấy id, name chủ đề đã chọn
        Category category = (Category) spinnerCategory.getSelectedItem();
        int categoryID = category.getId();
        String categoryName = category.getName();
        //chuyển qua activity question
        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);

        intent.putExtra("idcategories", categoryID);
        intent.putExtra("catgoriesname", categoryName);
        //sử dung startActivityFỏResult để có thể nhận lại dữ liệu kết quả trả về thông qua pt onActivityResult()
        startActivity(intent);
    }

    private void AnhXa() {
        textViewHightScore = findViewById(R.id.textviewDiemCao);
        buttonStartQuestion = findViewById(R.id.buttonBatDau);
        spinnerCategory = findViewById(R.id.spinnerChonDe);
        btnSup = findViewById(R.id.btnSup);
        btnSup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, IAPActivity.class));
            }
        });
    }

    //Load chủ đề
    private void loadCategories() {
        Database database = new Database(this);
        //Lấy dữ liệu từ danh sách chủ đề
        List<Category> categories = database.getDataCategories();
        //tạo adapter
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        //bố cục hiển thị chủ đề
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //gán chủ đề lên spinner hiển thị
        spinnerCategory.setAdapter(categoryArrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QUESTION) {
            if (requestCode == RESULT_OK) {
                int score = data.getIntExtra("score", 0);

                if (score > hightscore) {
                    updatehightscore(score);
                }
            }
        }
    }

    //update điểm cao
    private void updatehightscore(int score) {
        //gawbns điểm cao mơi
        hightscore = score;
        //hiển thị
        textViewHightScore.setText("Điểm cao: " + hightscore);
        //lưu ttruwx điểm
        SharedPreferences preferences = getSharedPreferences("share", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //gắn giá trị cho điểm cao mới vào khóa
        editor.putInt("hightscore", hightscore);
        //hoàn tất
        editor.apply();
    }
}