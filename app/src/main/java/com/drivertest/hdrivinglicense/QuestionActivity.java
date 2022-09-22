package com.drivertest.hdrivinglicense;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.drivertest.hdrivinglicense.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity {

    private TextView textviewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCategory;
    private TextView textViewCountDown;

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;

    private Button buttonConfimNext;

    private CountDownTimer countDownTimer;
    private ArrayList<Question> questionList;
    private long timeLeftInMillis;
    private int questionCounter;
    private int questionSize;

    private int Score;
    private boolean answered;
    private Question curentQuestions;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        anhxa();
        //Nhận dữ liệu chủ đề
        Intent intent = getIntent();
        int categoryID = intent.getIntExtra("idcategories", 0);
        String categoryName = intent.getStringExtra("catgoriesname");

        textViewCategory.setText("Chủ đề: " + categoryName);

        Database database = new Database(this);
        //danh sách list chứa ccaau hỏi
        questionList = database.getQuestion(categoryID);
        //lấy kinchs cỡ danh sách = tổng số câu hỏi
        questionSize = questionList.size();
        //đảo vị trị các phần tử
        Collections.shuffle(questionList);
        //show câu hỏi và đáp án
        showNextQuestion();
        //button xác nhân câu tiếp, hoàn thành
        buttonConfimNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nếu chưa tl câu hỏi
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuestionActivity.this, "Hãy chọn đáp án", Toast.LENGTH_SHORT).show();
                    }
                }
                // nếu dã tl , thực hiện chuyển câu hỏi
                else {
                    showNextQuestion();
                }
            }
        });
    }

    //hiển thị câu hỏi
    private void showNextQuestion() {
        //set lại màu đen cho đáp án
        textviewQuestion.setTextColor(Color.BLUE);
        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        //xóa chọn
        rbGroup.clearCheck();
        //nếu còn câu hỏi
        if (questionCounter < questionSize) {
            //lấy dữ liệu ở vị trí counter
            curentQuestions = questionList.get(questionCounter);
            //hiển thị câu hỏi
            textviewQuestion.setText(curentQuestions.getQuestion());
            rb1.setText(curentQuestions.getOption1());
            rb2.setText(curentQuestions.getOption2());
            rb3.setText(curentQuestions.getOption3());
            //tăng sau mỗi câu hỏi
            questionCounter++;
            //set ví trị câu hỏi hiện tại
            textViewQuestionCount.setText("Câu hỏi: " + questionCounter + " / " + questionSize);
            //giá trị false, chưa trả lời, đang show
            answered = false;
            //gắn tên cho button
            buttonConfimNext.setText("Xác nhận");
            //thời gian chạy 30s
            timeLeftInMillis = 30000;
            //đếm ngược thời gian trả lời
            startCountDown();

        } else {
            finishQuestion();
        }
    }

    //thời gian đếm ngược
    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                //update time
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                //hết giờ
                timeLeftInMillis = 0;
                updateCountDownText();
                //phương thức kiểm tra đán án
                checkAnswer();
            }
        }.start();
    }

    private void checkAnswer() {
        //true đã trả lời
        answered = true;
        //trả về radiobutton trong fbgroup
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        //vị trí của câu đã chọn
        int answer = rbGroup.indexOfChild(rbSelected) + 1;
        //nếu tl đúng đáp án
        if (answer == curentQuestions.getAnswer()) {
            //cộng 5 điểm
            Score = Score + 5;
            //hiển thị
            textViewScore.setText(("Điểm: " + Score));
        }
        //pt hiển thị đáp án
        showSolution();
    }

    private void showSolution() {
        //set màu cho radiobuuton đáp án
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        //kt đáp án set màu và hiển thị đáp án lên màn hình
        switch (curentQuestions.getAnswer()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                textviewQuestion.setText("Đáp án là A");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textviewQuestion.setText("Đáp án là B");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textviewQuestion.setText("Đáp án là C");
                break;
        }
        //nếu còn câu tl thì bttion sẽ settexxt là câu tiếp
        if (questionCounter < questionSize) {
            buttonConfimNext.setText("Câu tiếp");
        } else {
            buttonConfimNext.setText("Hoàn thành");
        }
        //dừng time
        countDownTimer.cancel();
    }

    //update thời gian
    private void updateCountDownText() {
        //tính phút 1 giây bằng 10000 miligiay, 1000: 1 giây - thời gian lặp lại 1 hành động
        int minutes = (int) ((timeLeftInMillis / 1000) / 60);
        //tính giây
        int seconds = (int) ((timeLeftInMillis / 1000) % 60);
        //định dạng kiểu time
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        //hiển thị lên màn hình
        textViewCountDown.setText(timeFormatted);
        //nếu thời gian dưới 10s thì chuyển màu đỏ
        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(Color.BLACK);
        }

    }

    ///Thoát qua giao diện chính
    private void finishQuestion() {
        //chứa dữ liệu gửi qua activity main
        Intent resultIntent = new Intent();
        resultIntent.putExtra("score", Score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    //back
    @Override
    public void onBackPressed() {
        count++;
        if (count >= 1) {
            finishQuestion();
        }
        count = 0;
    }

    //ánh xạ id
    private void anhxa() {
        textviewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewCategory = findViewById(R.id.text_view_category);

        textViewCountDown = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);

        buttonConfimNext = findViewById(R.id.button_confim_next);
    }
}