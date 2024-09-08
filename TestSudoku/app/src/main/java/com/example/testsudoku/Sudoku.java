package com.example.testsudoku;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;


public class Sudoku extends AppCompatActivity {
    EditText[][] sudokuCells = new EditText[9][9];
    EditText[][] checkResult = new EditText[9][9];
    Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sudoku);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView backToSudokuMenu = findViewById(R.id.img_backToSudokuMenu);
        TextView tv = findViewById(R.id.tv_1);

        backToSudokuMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Sudoku.this, SudokuMenu.class);
                startActivity(i);
            }
        });

        // Tạo gridlayout
        GridLayout sudokuGrid = new GridLayout(this);
        sudokuGrid.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));

        sudokuGrid.setColumnCount(9); // Số cột là 9 cho ván Sudoku 9x9
        sudokuGrid.setRowCount(9);    // Số hàng là 9 cho ván Sudoku 9x9
        sudokuGrid.setBackgroundColor(Color.parseColor("#FFFFFF")); // Màu nền trắng
        sudokuGrid.setBackgroundResource(R.drawable.sudoku_grid_border); // Border cho grid

        // Thêm các EditText vào GridLayout
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                EditText editText = new EditText(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = GridLayout.LayoutParams.WRAP_CONTENT;
                params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                params.columnSpec = GridLayout.spec(col, 1f);
                params.rowSpec = GridLayout.spec(row, 1f);

                editText.setLayoutParams(params);
                editText.setGravity(Gravity.CENTER);
                editText.setPadding(8, 8, 8, 8);
                editText.setTextSize(18);
                editText.setBackgroundResource(R.drawable.sudoku_border); // Đặt drawable cho viền
                editText.setInputType(InputType.TYPE_CLASS_NUMBER); // Chỉ cho phép nhập số
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)}); // Giới hạn 1 ký tự
                editText.setEnabled(false);
                editText.setTextColor(Color.BLACK);

                // Lưu EditText vào mảng
                sudokuCells[row][col] = editText;
                checkResult[row][col] = editText;
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // Xử lý sự kiện trước khi nội dung thay đổi
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // Xử lý khi nội dung đang thay đổi
                        boolean finished = true;

                        // Duyệt qua tất cả các ô trong lưới 9x9
                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 9; j++) {

                                String checkText = checkResult[i][j].getText().toString().trim();
                                String sudokuText = sudokuCells[i][j].getText().toString().trim();

                                // Kiểm tra nếu ô nào chưa khớp
                                if (!checkText.equals(sudokuText) || checkText.isEmpty()) {
                                    finished = false;
                                    break;
                                }
                            }
                            if (!finished) {
                                break;
                            }
                        }

                        // Nếu tất cả các ô đều khớp, hiển thị "WIN!"
                        if (finished) {
                            tv.setText("WIN!");
                        } else {
                            tv.setText("");  // Nếu chưa hoàn thành, có thể xóa thông báo "WIN!"
                        }
                    }


                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                sudokuGrid.addView(editText);
            }
        }
        LinearLayout mainLayout = findViewById(R.id.main_layout);
        mainLayout.addView(sudokuGrid);
        // Tạo và hiển thị bảng Sudoku
        fillBoard();
        removeNumbers();
    }

    public boolean UnusedInRow(int row, int value) {
        for (int x = 0; x < 9; x++) {
            if (!sudokuCells[row][x].getText().toString().isEmpty()) {
                if (Integer.parseInt(sudokuCells[row][x].getText().toString()) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean UnusedInCol(int col, int value) {
        for (int x = 0; x < 9; x++) {
            if (!sudokuCells[x][col].getText().toString().isEmpty()) {
                if (Integer.parseInt(sudokuCells[x][col].getText().toString()) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean UnusedInBox(int startRow, int startCol, int value) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!sudokuCells[startRow + i][startCol + j].getText().toString().isEmpty()) {
                    if (Integer.parseInt(sudokuCells[startRow + i][startCol + j].getText().toString()) == value) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void fillDiagonal() {
        for (int i = 0; i < 9; i += 3) {
            fillBox(i, i);
        }
    }

    private int randomGenerator(int num) {
        return random.nextInt(num) + 1;
    }

    // Điền số vào 1 ô vuông 3x3
    private void fillBox(int row, int col) {
        int num;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                do {
                    num = randomGenerator(9); // Tạo số ngẫu nhiên từ 1 đến 9
                } while (!UnusedInBox(row, col, num)); // Kiểm tra số có trùng lặp trong ô vuông 3x3
                checkResult[row + i][col + j].setText(String.valueOf(num));
                sudokuCells[row + i][col + j].setText(String.valueOf(num)); // Đặt số vào EditText
            }
        }
    }

    private boolean isSafe(int i, int j, int num) {
        return UnusedInRow(i, num) &&
                UnusedInCol(j, num) &&
                UnusedInBox(i - i % 3, j - j % 3, num);
    }

    private boolean fillRemaining(int i, int j) {
        if (j >= 9 && i < 8) {
            i++;
            j = 0;
        }
        if (i >= 9 && j >= 9) {
            return true;
        }

        if (i < 3) {
            if (j < 3) {
                j = 3;
            }
        } else if (i < 6) {
            if (j == (i / 3) * 3) {
                j += 3;
            }
        } else {
            if (j == 6) {
                i++;
                j = 0;
                if (i >= 9) {
                    return true;
                }
            }
        }

        for (int num = 1; num <= 9; num++) {
            if (isSafe(i, j, num)) {
                sudokuCells[i][j].setText(String.valueOf(num)); // Điền số vào EditText
                if (fillRemaining(i, j + 1)) {
                    return true;
                }
                sudokuCells[i][j].setText(""); // Reset lại ô nếu số không hợp lệ
                checkResult[i][j].setText(""); // Reset lại ô nếu số không hợp lệ

            }
        }
        return false;
    }

    private void fillBoard() {
        fillDiagonal(); // Điền các ô trên đường chéo
        fillRemaining(0, 3); // Điền các ô còn lại của bảng
    }

    private void removeNumbers() {
        int count = 30; // Số lượng ô muốn xóa
        while (count != 0) {
            int cellId = randomGenerator(81) - 1; // Lấy ngẫu nhiên 1 ô
            int i = cellId / 9; // Tính hàng của ô
            int j = cellId % 9; // Tính cột của ô

            // Kiểm tra nếu ô không trống
            if (!sudokuCells[i][j].getText().toString().isEmpty()) {
                sudokuCells[i][j].setText(""); // Xóa giá trị của ô, làm trống ô đó
                sudokuCells[i][j].setEnabled(true);
                count--; // Giảm số lượng ô cần xóa
            }
        }
    }
}