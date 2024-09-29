package com.example.testsudoku;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class trong_viewAddQuesActivity extends AppCompatActivity {
    private static final int PICK_IMGAGE_REQUEST = 1;  //Định nghĩa mã yêu cầu để xác định hành động chọn ảnh
    private static final int REQUEST_STORAGE_PERMISSION = 100;

    private ImageView imgView;
    private Uri ImageUri;
    private EditText edQuestion, edAnswer, edHint;
    private Button btnChoosePic, btnSave, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.trong_activityviewaddques);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edQuestion = findViewById(R.id.edQuestion);
        edAnswer = findViewById(R.id.edAnswer);
        edHint = findViewById(R.id.edHint);

        imgView = findViewById(R.id.imgViewImg);

        btnChoosePic = findViewById(R.id.btnChoosePic);
        btnSave = findViewById(R.id.btnSave);

        btnChoosePic.setOnClickListener(v -> chooseImage());
        btnSave.setOnClickListener(v -> saveQuestionToDatabase());
        btnBack = findViewById(R.id.btnBackMenu);
        btnBack.setOnClickListener(v -> backMenu());

    }

    private void backMenu() {
        finish();
    }
    //Phương thức này kiểm tra xem ứng dụng đã có quyền truy cập vào bộ nhớ ngoài (để chọn ảnh) chưa.
    // Nếu chưa có, nó sẽ yêu cầu quyền truy cập.
    // Nếu đã có quyền, nó sẽ gọi phương thức openImagePicker để mở giao diện chọn ảnh.
    private void chooseImage() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            // Yêu cầu quyền truy cập bộ nhớ ngoài nếu chưa được cấp
            androidx.core.app.ActivityCompat.requestPermissions(
                    trong_viewAddQuesActivity.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION
            );
        } else {
            // Nếu đã có quyền, mở trình chọn ảnh
            openImgaePick();
        }
    }

    //Phương thức này khởi tạo một Intent để mở giao diện chọn ảnh, chỉ cho phép chọn tệp loại ảnh (image/*),
    // và sử dụng ACTION_OPEN_DOCUMENT để cho phép người dùng chọn ảnh từ thiết bị.
    private void openImgaePick() {
        Intent intent = new Intent();
        intent.setType("image/*");// Chỉ định loại dữ liệu là ảnh
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);// Sử dụng hành động để mở tài liệu
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMGAGE_REQUEST);
    }

    // Phương thức xử lý kết quả khi người dùng cấp quyền hoặc từ chối quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                // Nếu quyền được cấp, mở trình chọn ảnh
                openImgaePick();
            } else {
                // Nếu quyền bị từ chối, hiển thị thông báo
                Toast.makeText(this, "Quyền truy cập bộ nhớ đã bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Phương thức này được gọi khi người dùng đã chọn ảnh. Nếu ảnh đã được chọn thành công,
    // URI của ảnh sẽ được lưu vào imageUri,
    // và ảnh sẽ được hiển thị trên ImageView. Nếu có lỗi trong quá trình tải ảnh, nó sẽ được in ra log.
    @Override
    protected void onActivityResult(int requestCPde, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCPde, resultCode, data);
        if(requestCPde == PICK_IMGAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            ImageUri = data.getData();
            getContentResolver().takePersistableUriPermission(ImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImageUri);
                imgView.setImageBitmap(bitmap);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//Phương thức này lưu câu hỏi, đáp án, gợi ý và ảnh vào cơ sở dữ liệu.
// Trước hết, nó kiểm tra các trường dữ liệu xem có trống không. Nếu không trống, nó
// sử dụng DatabaseHelper để lưu dữ
// liệu vào cơ sở dữ liệu. Sau khi lưu thành công, giao diện được reset lại về trạng thái ban đầu.
    private void saveQuestionToDatabase() {
        String question = edQuestion.getText().toString();
        String answer = edAnswer.getText().toString();
        String hint = edHint.getText().toString();
        String imgName = ImageUri != null ? ImageUri.toString() : null;
        if(question.isEmpty() || answer.isEmpty() || hint.isEmpty() || imgName == null) {
            Toast.makeText(trong_viewAddQuesActivity.this, "Vui lòng nhập dầy đủ thông tin và chọn ảnh", Toast.LENGTH_SHORT).show();
            return;
        }

        trong_DataHelper dpHelper = new trong_DataHelper(trong_viewAddQuesActivity.this);
        dpHelper.getWritableDatabase();
        boolean success = dpHelper.addGame(imgName, question, answer, hint);
        if(success) {
            Toast.makeText(trong_viewAddQuesActivity.this, "Lưu câu hỏi thành công!", Toast.LENGTH_SHORT).show();
            // Reset form nếu cần thiết
            edQuestion.setText("");
            edAnswer.setText("");
            edHint.setText("");
            imgView.setImageResource(R.drawable.ic_launcher_background);
        }else {
            Toast.makeText(trong_viewAddQuesActivity.this, "Lưu câu hỏi thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}