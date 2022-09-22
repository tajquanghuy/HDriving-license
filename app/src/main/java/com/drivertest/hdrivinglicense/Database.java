package com.drivertest.hdrivinglicense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.drivertest.hdrivinglicense.model.Category;
import com.drivertest.hdrivinglicense.model.Question;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    //Tên database
    private static final String DATABASE_NAME = "Question.db";
    //vertion
    public static final int VERTION = 1;
    private SQLiteDatabase db;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERTION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;
        //Biến bảng chuyên mục
        final String CATEGORIES_TABLE = "CREATE TABLE " +
                Table.CategoriesTable.TABLE_NAME + "( " +
                Table.CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table.CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";
        //biến bảng question
        final String QUESTIONS_TABLE = "CREATE TABLE " +
                Table.QuestionTable.TABLE_NAME + " ( " +
                Table.QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table.QuestionTable.COLUMN_QUESTION + " TEXT, " +
                Table.QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                Table.QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                Table.QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                Table.QuestionTable.COLUMN_ANSWER + " INTEGER, " +
                Table.QuestionTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + Table.QuestionTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                Table.CategoriesTable.TABLE_NAME + "(" + Table.CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        //Tạo bảng
        db.execSQL(CATEGORIES_TABLE);
        db.execSQL(QUESTIONS_TABLE);
        //insert dl
        CreateCategories();
        CreateQuestions();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Table.CategoriesTable.COLUMN_NAME);// loại bỏ bảng nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + Table.QuestionTable.TABLE_NAME);
        onCreate(db);
    }

    //insert chủ đề vào csdl
    private void insertCategories(Category category) {
        ContentValues values = new ContentValues();
        values.put(Table.CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(Table.CategoriesTable.TABLE_NAME, null, values);
    }

    //Cac giá trị insert
    private void CreateCategories() {
        //đề 1 id =1
        Category c1 = new Category("Lý thuyết 1");
        insertCategories(c1);
        //đề 2 id =2
        Category c2 = new Category("Lý thuyết 2");
        insertCategories(c2);
        //đề 3 id =3
        Category c3 = new Category("Xa hình");
        insertCategories(c3);
    }

    //insert câu hỏi và đáp án vào csdl
    private void insertQuestion(Question question) {
        ContentValues values = new ContentValues();// tạo lớp được sử dụng để lưu trữ dữ liệu vào cơ sở dữ liệu.
        values.put(Table.QuestionTable.COLUMN_QUESTION, question.getQuestion());//key là tên cột trong cơ sở dữ liệu và valuelà giá trị để lưu trữ trong cột đó.
        values.put(Table.QuestionTable.COLUMN_OPTION1, question.getOption1());
        values.put(Table.QuestionTable.COLUMN_OPTION2, question.getOption2());
        values.put(Table.QuestionTable.COLUMN_OPTION3, question.getOption3());
        values.put(Table.QuestionTable.COLUMN_ANSWER, question.getAnswer());
        values.put(Table.QuestionTable.COLUMN_CATEGORY_ID, question.getCategoryID());

        db.insert(Table.QuestionTable.TABLE_NAME, null, values);
    }

    private void CreateQuestions() {
        //Dữ liệu bảng question
        Question q1 = new Question("Người điều khiển phương tiện giao thông đường bộ mà trong cơ thể có chất ma túy có bị nghiêm cấm hay không?",
                "A. Bị nghiêm cấm",
                "B. Không bị nghiêm cấm",
                "C. Không bị nghiêm cấm, nếu có chất ma túy ở mức nhẹ, có thể điều khiển phương tiện tham gia giao thông.", 1, 1); //đáp án và id
        insertQuestion(q1);
        Question q2 = new Question("Hành vi điều khiển xe cơ giới chạy quá tốc độ quy định, giành đường, vượt ẩu có bị nghiêm cấm hay không?",
                "A. Bị nghiêm cấm tùy từng trường hợp",
                "B. Không bị nghiêm cấm",
                "C. Bị nghiêm cấm.", 3, 1);
        insertQuestion(q2);
        Question q3 = new Question("Bạn đang lái xe phía trước có một xe cứu thương đang phát tín hiệu ưu tiên bạn có được phép vượt hay không?",
                "A.  Không được vượt", "B. Được vượt khi đang đi trên cầu", "C. Được phép vượt khi đi qua nơi giao nhau có ít phương tiện cùng tham gia giao thông", 1, 1);
        insertQuestion(q3);

        Question q4 = new Question("Ở phần đường dành cho người đi bộ qua đường, trên cầu, đầu cầu, đường cao tốc, đường hẹp, đường dốc, tại nơi đường bộ giao nhau cùng mức với đường sắt có được quay đầu xe hay không?",
                "A. Được phép", "B. Không được phép", "C. Tùy từng trường hợp.", 2, 1);
        insertQuestion(q4);

        Question q5 = new Question("Khi điều khiển xe mô tô hai bánh, xe mô tô ba bánh, xe gắn máy, những hành vi buông cả hai tay; sử dụng xe để kéo, đẩy xe khác, vật khác; sử dụng chân chống của xe quệt xuống đường khi xe đang chạy có được phép không?",
                "A. Được phép", "B. Tùy trường hợp", "C. Không được phép.", 3, 1);
        insertQuestion(q5);

        Question q6 = new Question("Người điều khiển xe mô tô hai bánh, xe gắn máy có được đi xe dàn hàng ngang; đi xe vào phần đường dành cho người đi bộ và phương tiện khác; sử dụng ô, điện thoại di động, thiết bị âm thanh (trừ thiết bị trợ thính) hay không?",
                "A. Được phép nhưng phảo đảm bảo an toàn", "B.  Không được phép", "C. Được phép tùy từng hoàn cảnh, điều kiện cụ thể.", 2, 1);
        insertQuestion(q6);

        Question q7 = new Question("Hành vi vận chuyển đồ vật cồng kềnh bằng xe mô tô, xe gắn máy khi tham gia giao thông có được phép hay không?",
                "A. Không được vận chuyển", "B. Chỉ được vận chuyển khi đã chằng buộc cẩn thận", "C. Chỉ được vận chuyển vật cồng kềnh trên xe máy nếu khoảng cách về nhà ngắn hơn 2 km.", 1, 1);
        insertQuestion(q7);

        Question q8 = new Question("Hành vi sử dụng xe mô tô để kéo, đẩy xe mô tô khác bị hết xăng đến trạm mua xăng có được phép hay không?",
                "A. Chỉ được kéo nếu đã nhìn thấy trạm xăng", "B. Chỉ được thực hiện trên đường vắng phương tiện cùng tham gia giao thông.", "C.  Không được phép.", 3, 1);
        insertQuestion(q8);

        Question q9 = new Question("Phần của đường bộ đươc sử dụng cho các phương tiện giao thông qua lại là gì?",
                "A. Phần mặt đường và lề đường ", "B. Phần đường xe chạy", "C. Phần đường xe cơ giới.", 2, 1);
        insertQuestion(q9);

        Question q10 = new Question("'Làn đường' là gì?",
                "A. Là một phần của phần đường xe chạy được chia theo chiều dọc của đường, sử dụng cho xe chạy.", "B. Là một phần của phần đường xe chạy được chia theo chiều dọc của đường, có bề rộng đủ cho xe chạy an toàn", "C. Là đường cho xe ô tô chạy, dừng, đỗ an toàn.", 2, 1);
        insertQuestion(q10);

        Question q11 = new Question("Người lái xe được hiểu như thế nào trong các khái niệm dưới đây?",
                "A. Là người điều khiển xe cơ giới", "B. Là người điều khiển xe thô sơ", "C.Là người điều khiển xe có súc vật kéo.", 1, 1);
        insertQuestion(q11);

        Question q12 = new Question("Trong các khái niệm dưới đây, 'dải phân cách' được hiểu như thế nào là đúng?",
                " A. Là bộ phận của đường để ngăn cách không cho các loại xe vào những nơi không được phép", " B. Là bộ phận của đường để phân tách phần đường xe chạy và hành lang an toàn giao thông.", "C. Là bộ phận của đường để phân chia mặt đường thành hai chiều xe chạy riêng biệt hoặc để phân chia phần đường của xe cơ giới và xe thô sơ.", 3, 1);
        insertQuestion(q12);

        Question q13 = new Question("'Dải phân cách' trên đường bộ gồm những loại nào?",
                "a. Dải phân cách gồm loại cố định và loại di động", "b. Dải phân cách gồm tường chống ồn, hộ lan cứng và hộ lan mềm.", "c. Dải phân cách gồm giá long môn và biển báo hiệu đường bộ.", 1, 1);
        insertQuestion(q13);

        Question q14 = new Question("Khái niệm 'phương tiện giao thông thô sơ đường bộ' được hiểu thế nào là đúng?",
                "a. Gồm xe đạp (kể cả xe đạp máy, xe đạp điện), xe xích lô, xe lăn dùng cho người khuyết tật, xe súc vật kéo và các loại xe tương tự", "b. Gồm xe đạp (kể cả xe đạp máy, xe đạp điện), xe gắn máy, xe cơ giới dùng cho người khuyết tật và xe máy chuyên dùng", "c. Gồm xe ô tô, máy kéo, rơ moóc hoặc sơ mi rơ moóc được kéo bởi xe ô tô, máy kéo.", 1, 1);
        insertQuestion(q14);

        Question q15 = new Question("'Phương tiện tham gia giao thông đường bộ' gồm những loại nào?",
                "a. Phương tiện giao thông cơ giới đường bộ", "b. Phương tiện giao thông thô sơ đường bộ và xe máy chuyên dùng", "c. Cả ý a và b", 3, 1);
        insertQuestion(q15);

        Question q16 = new Question("'Người điều khiển phương tiện tham gia giao thông đường bộ' gồm những đối tượng nào dưới đây?",
                "a. Người điều khiển xe cơ giới, người điều khiển xe thô sơ", "b. Người điều khiển xe máy chuyên dùng tham gia giao thông đường bộ.", "c. Cả ý a và ý b", 3, 1);
        insertQuestion(q16);

        Question q17 = new Question("Khái niệm 'người điều khiển giao thông' được hiểu như thế nào là đúng?",
                "a. Là người điều khiển phương tiện tham gia giao thông tại nơi thi công, nơi ùn tắc giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.", "b. Là cảnh sát giao thông, người được giao nhiệm vụ hướng dẫn giao thông tại nơi thi công, nơi ùn tắc giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.", "c. Là người tham gia giao thông tại nơi thi công, nơi ùn tắt giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.", 2, 1);
        insertQuestion(q17);

        Question q18 = new Question("'Người tham gia giao thông đường bộ' gồm những đối tượng nào?",
                "a. Người điều khiển, người sử dụng phương tiện tham giao giao thông đường bộ", "b. Người điều khiển, dẫn dắt súc vật, người đi bộ trên đường.", "c. Cả ý a và ý b.", 3, 1);
        insertQuestion(q18);

        Question q19 = new Question("Cuộc đua xe chỉ được thực hiện khi nào?",
                "a.  Diễn ra trên đường phố không có người qua lại", "b. Được người dân ủng hộ.", "c. Được cơ quan có thẩm quyền cấp phép.", 3, 1);
        insertQuestion(q19);

        Question q20 = new Question("Người lái xe sử dụng đèn như thế nào khi lái xe trong khu đô thị và đông dân cư vào ban đêm?",
                "a. Bất cứ đèn nào miễn là mắt nhìn rõ phía trước", "b. Đèn chiếu xa (đèn pha) khi đường vắng, đèn pha chiếu gần (đèn cốt) khi có xe đi ngược chiều.", "c. Đèn chiếu gần (đèn cốt).", 3, 1);
        insertQuestion(q20);

        Question q21 = new Question("Trong các khái niệm dưới đây, khái niệm dừng xe được hiểu như thế nào là đúng?",
                "A. Là trạng thái đứng yên của phương tiện giao thông không giới hạn thời gian để cho người lên, xuống phương tiện, xếp dỡ hàng hóa hoặc thực hiện công việc khác",
                "B. Là trạng thái đứng yên tạm thời của phương tiện giao thông trong một khoảng thời gian cần thiết đủ để cho người lên, xuống phương tiện, xếp dỡ hàng hóa hoặc thực hiện công việc khác",
                "C. Là trạng thái đứng yên của phương tiện giao thông không giới hạn thời gian giữa 02 lần vận chuyển hàng hóa hoặc hành khách.", 2, 2);
        insertQuestion(q21);
        Question q22 = new Question("Sử dụng rượu, bia khi lái xe, nếu bị phát hiện thì bị xử lý như thế nào?",
                "A. Chỉ bị nhắc nhở",
                "B. Bị xử phạt hành chính hoặc có thể bị xử lý hình sự tùy theo mức độ vi phạm",
                "C. Không bị xử lý hình sự.", 2, 2);
        insertQuestion(q22);
        Question q23 = new Question(" Bạn đang lái xe phía trước có một xe cảnh sát giao thông không phát tín hiệu ưu tiên bạn có được phép vượt hay không?",
                "A.  Không được vượt", "B. Được vượt khi đang đi trên cầu", "C. Được vượt khi đảm bảo an toàn.", 3, 2);
        insertQuestion(q23);

        Question q24 = new Question("Khi điều khiển xe cơ giới, người lái xe phải bật đèn tín hiệu báo rẽ trong các trường hợp nào sau đây?",
                "A. Khi cho xe chạy thẳng", "B. Trước khi thay đổi làn đường", "C. Sau khi thay đổi làn đường..", 2, 2);
        insertQuestion(q24);

        Question q25 = new Question("Khi điều khiển xe mô tô hai bánh, xe mô tô ba bánh, xe gắn máy, những hành vi buông cả hai tay; sử dụng xe để kéo, đẩy xe khác, vật khác; sử dụng chân chống của xe quệt xuống đường khi xe đang chạy có được phép không?",
                "A. Được phép", "B. Tùy trường hợp", "C. Không được phép.", 2, 2);
        insertQuestion(q25);

        Question q26 = new Question("Người có giấy phép lái xe mô tô hạng A1 được phép điều khiển các loại xe nào dưới đây?",
                "A. Xe mô tô hai bánh có dung tích xi-lanh từ 50 cm3 đến dưới 175 cm3", "B.  Xe mô tô ba bánh dùng cho người khuyết tật", "C. Cả ý a và ý b.", 3, 2);
        insertQuestion(q26);

        Question q27 = new Question("Khi sử dụng giấy phép lái xe đã khai báo mất để điều khiển phương tiện cơ giới đường bộ, ngoài việc bị thu hồi giấy phép lái xe, chịu trách nhiệm trước pháp luật, người lái xe không được cấp giấy phép lái xe trong thời gian bao nhiêu năm?",
                "A. 05 Năm", "B. 02 Năm", "C. 03 Năm.", 1, 2);
        insertQuestion(q27);

        Question q28 = new Question("Trên đường một chiều có vạch kẻ phân làn đường xe thô sơ và xe cơ giới phải đi như thế nào là đúng quy tắc giao thông?",
                "A. Xe thô sơ phải đi trên làn đường bên trái ngoài cùng, xe cơ giới, xe máy chuyên dùng đi trên làn đường bên phải.", "B. Xe thô sơ phải đi trên làn đường bên phải trong cùng; xe cơ giới, xe máy chuyên dùng đi trên làn đường bên trái", "C.  Xe thô sơ đi trên làn đường phù hợp không gây cản trở giao thông, xe cơ giới, xe máy chuyên dùng đi trên làn đường bên phải.", 2, 2);
        insertQuestion(q28);

        Question q29 = new Question("Khi muốn chuyển hướng, người lái xe phải thực hiện như thế nào để đảm bảo an toàn giao thông?",
                "A. Quan sát gương, ra tín hiệu, quan sát an toàn và chuyển hướng. ", "B. Quan sát gương, giảm tốc độ, ra tín hiệu chuyển hướng, quan sát an toàn và chuyển hướng.", "C. Quan sát gương, tăng tốc độ, ra tín hiệu và chuyển hướng.", 2, 2);
        insertQuestion(q29);

        Question q30 = new Question("Làn đường là gì?",
                "A. Là một phần của phần đường xe chạy được chia theo chiều dọc của đường, sử dụng cho xe chạy.", "B. Là một phần của phần đường xe chạy được chia theo chiều dọc của đường, có bề rộng đủ cho xe chạy an toàn", "C. Là đường cho xe ô tô chạy, dừng, đỗ an toàn.", 2, 2);
        insertQuestion(q30);

        Question q31 = new Question("Người lái xe được hiểu như thế nào trong các khái niệm dưới đây?",
                "A. Là người điều khiển xe cơ giới", "B. Là người điều khiển xe thô sơ", "C.Là người điều khiển xe có súc vật kéo.", 1, 2);
        insertQuestion(q31);

        Question q32 = new Question("Trong các khái niệm dưới đây, dải phân cách được hiểu như thế nào là đúng?",
                " A. Là bộ phận của đường để ngăn cách không cho các loại xe vào những nơi không được phép", " B. Là bộ phận của đường để phân tách phần đường xe chạy và hành lang an toàn giao thông.", "C. Là bộ phận của đường để phân chia mặt đường thành hai chiều xe chạy riêng biệt hoặc để phân chia phần đường của xe cơ giới và xe thô sơ.", 3, 2);
        insertQuestion(q32);

        Question q33 = new Question("Dải phân cách trên đường bộ gồm những loại nào?",
                "a. Dải phân cách gồm loại cố định và loại di động", "b. Dải phân cách gồm tường chống ồn, hộ lan cứng và hộ lan mềm.", "c. Dải phân cách gồm giá long môn và biển báo hiệu đường bộ.", 1, 2);
        insertQuestion(q33);

        Question q34 = new Question("Khái niệm phương tiện giao thông thô sơ đường bộ được hiểu thế nào là đúng?",
                "a. Gồm xe đạp (kể cả xe đạp máy, xe đạp điện), xe xích lô, xe lăn dùng cho người khuyết tật, xe súc vật kéo và các loại xe tương tự", "b. Gồm xe đạp (kể cả xe đạp máy, xe đạp điện), xe gắn máy, xe cơ giới dùng cho người khuyết tật và xe máy chuyên dùng", "c. Gồm xe ô tô, máy kéo, rơ moóc hoặc sơ mi rơ moóc được kéo bởi xe ô tô, máy kéo.", 1, 2);
        insertQuestion(q34);

        Question q35 = new Question("Bạn đang lái xe trên đường hẹp, xuống dốc và gặp một xe đang đi lên dốc, bạn cần làm gì?",
                "a. Tiếp tục đi và xe lên dốc phải nhường đường cho mình", "b. Nhường đường cho xe lên dốc", "c. Chỉ nhường đường khi xe lên dốc nháy đèn.", 2, 2);
        insertQuestion(q35);

        Question q36 = new Question("Tại nơi đường giao nhau, người lái xe đang đi trên đường không ưu tiên phải nhường đường như thế nào là đúng quy tắc giao thông?",
                "a. Nhường đường cho xe đi ở bên phải mình tới", "b. Nhường đường cho xe đi ở bên trái mình tới", "c. Nhường đường cho xe đi trên đường ưu tiên hoặc đường chính từ bất kỳ hướng nào tới.", 3, 2);
        insertQuestion(q36);


        Question q37 = new Question("Khái niệm người điều khiển giao thông được hiểu như thế nào là đúng?",
                "a. Là người điều khiển phương tiện tham gia giao thông tại nơi thi công, nơi ùn tắc giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.", "b. Là cảnh sát giao thông, người được giao nhiệm vụ hướng dẫn giao thông tại nơi thi công, nơi ùn tắc giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.", "c. Là người tham gia giao thông tại nơi thi công, nơi ùn tắt giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.", 2, 2);
        insertQuestion(q37);

        Question q38 = new Question("Người tham gia giao thông đường bộ gồm những đối tượng nào?",
                "a. Người điều khiển, người sử dụng phương tiện tham giao giao thông đường bộ", "b. Người điều khiển, dẫn dắt súc vật, người đi bộ trên đường.", "c. Cả ý a và ý b.", 3, 2);
        insertQuestion(q38);

        Question q39 = new Question("Tại nơi đường giao nhau, người lái xe đang đi trên đường không ưu tiên phải nhường đường như thế nào là đúng quy tắc giao thông?",
                "a.  Nhường đường cho xe đi ở bên phải mình tới", "b. Nhường đường cho xe đi ở bên trái mình tới", "c. Nhường đường cho xe đi trên đường ưu tiên hoặc đường chính từ bất kỳ hướng nào tới.", 3, 2);
        insertQuestion(q39);

        Question q40 = new Question("Người lái xe sử dụng đèn như thế nào khi lái xe trong khu đô thị và đông dân cư vào ban đêm?",
                "a. Bất cứ đèn nào miễn là mắt nhìn rõ phía trước", "b. Đèn chiếu xa (đèn pha) khi đường vắng, đèn pha chiếu gần (đèn cốt) khi có xe đi ngược chiều.", "c. Đèn chiếu gần (đèn cốt).", 3, 2);
        insertQuestion(q40);
    }

    public List<Category> getDataCategories() {
        List<Category> categoryList = new ArrayList<>();//tạo mới 1 đối tượng
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Table.CategoriesTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndexOrThrow(Table.CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndexOrThrow(Table.CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            }
            while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }

    //lấy dữ liệu câu hỏi và đáp án có id = id _category theo chủ đề đã chọn
    public ArrayList<Question> getQuestion(int catgoryID) {
        ArrayList<Question> questionArrayList = new ArrayList<>();
        db = getReadableDatabase();
        String selection = Table.QuestionTable.COLUMN_CATEGORY_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(catgoryID)};
        Cursor c = db.query(Table.QuestionTable.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();

                question.setId(c.getColumnIndexOrThrow(Table.QuestionTable._ID));
                question.setQuestion(c.getString(c.getColumnIndexOrThrow(Table.QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndexOrThrow(Table.QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndexOrThrow(Table.QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndexOrThrow(Table.QuestionTable.COLUMN_OPTION3)));
                question.setAnswer(c.getInt(c.getColumnIndexOrThrow(Table.QuestionTable.COLUMN_ANSWER)));
                question.setCategoryID(c.getInt(c.getColumnIndexOrThrow(Table.QuestionTable.COLUMN_CATEGORY_ID)));
                questionArrayList.add(question);
            }
            while (c.moveToNext());
        }
        c.close();
        return questionArrayList;
    }

}
