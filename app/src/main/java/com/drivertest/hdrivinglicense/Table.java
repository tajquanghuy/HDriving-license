package com.drivertest.hdrivinglicense;

import android.provider.BaseColumns;

public class Table {
    private Table(){}

    public static class CategoriesTable implements BaseColumns {
        public static final  String TABLE_NAME = "categories";
        public static final String COLUMN_NAME = "name";
    }
    //dữ liệu bảng question
    public static class QuestionTable implements BaseColumns{
        //Tên bảng
        public static final  String TABLE_NAME = "questions";
        //Câu hỏi
        public static final String COLUMN_QUESTION = "question";
        //3 đáp án chọn
        public static final  String COLUMN_OPTION1 = "option1";
        public static final  String COLUMN_OPTION2 = "option2";
        public static final  String COLUMN_OPTION3 = "option3";
        //đáp án
        public static final  String COLUMN_ANSWER = "answer";
        //id chuyên mục
        public static final  String COLUMN_CATEGORY_ID = "id_categories";
    }

}
