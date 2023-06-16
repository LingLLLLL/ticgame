package com.dataflair.ticgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txt_turn;
    private String  turn = "X";
    private String[][] my_board;
    private TableLayout gameBoard;
    private int grid_size = 3;
    private String playerXName = "Player X";
    private String playerYName = "Player Y";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_turn = findViewById(R.id.txt_turn);
        gameBoard = findViewById(R.id.gameBoard);

        // Set up the board
        my_board = new String[grid_size][grid_size];
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                my_board[i][j] = " ";
                TextView tv = new TextView(this);
//                tv.setBackgroundResource(R.drawable.cell_border);
                tv.setGravity(Gravity.CENTER);
                tv.setOnClickListener(Move(i, j, tv));
                TableRow row = (TableRow) gameBoard.getChildAt(i);
                if (row != null) {
                    row.addView(tv, j);
                }
            }
        }

        // Set player names from settings
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        playerXName = sharedPref.getString("playerXName", "Player X");
        playerYName = sharedPref.getString("playerYName", "Player Y");

        // Display first player's name
        txt_turn.setText("Turn: " + playerXName);
    }

    protected int gameStatus() {
        // State meanings:
        // 0 Continue
        // 1 X Wins
        // 2 O Wins
        // -1 Draw

        int rowX = 0, colX = 0, rowO = 0, colO = 0;
        for (int i = 0; i < grid_size; i++) {
            if (check_Row_Equality(i, playerXName))
                return 1;
            if (check_Column_Equality(i, playerXName))
                return 1;
            if (check_Row_Equality(i, playerYName))
                return 2;
            if (check_Column_Equality(i, playerYName))
                return 2;
            if (check_Diagonal(playerXName))
                return 1;
            if (check_Diagonal(playerYName))
                return 2;
        }

        boolean boardFull = true;
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                if (my_board[i][j] == " ")
                    boardFull = false;
            }
        }
        if (boardFull)
            return -1;
        else return 0;
    }

    protected boolean check_Diagonal(String player) {
        int count_Equal1 = 0, count_Equal2 = 0;
        for (int i = 0; i < grid_size; i++)
            if (my_board[i][i] == player)
                count_Equal1++;
        for (int i = 0; i < grid_size; i++)
            if (my_board[i][grid_size - 1 - i] == player)
                count_Equal2++;
        if (count_Equal1 == grid_size || count_Equal2 == grid_size)
            return true;
        else return false;
    }

    protected boolean check_Row_Equality(int r, String player) {
        int count_Equal = 0;
        for (int i = 0; i < grid_size; i++) {
            if (my_board[r][i] == player) {
                count_Equal++;
            }
        }
        if (count_Equal == grid_size) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean check_Column_Equality(int c, String player) {
        int count_Equal = 0;
        for (int i = 0; i < grid_size; i++) {
            if (my_board[i][c] == player) {
                count_Equal++;
            }
        }
        if (count_Equal == grid_size) {
            return true;
        } else {
            return false;
        }
    }

    protected View.OnClickListener Move(final int row, final int col, final TextView tv) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (my_board[row][col] == " ") {
                    my_board[row][col] = turn;
                    tv.setText(turn);
                    int status = gameStatus();
                    if (status == 1) {
                        txt_turn.setText(playerXName + " Wins!");
                        disableBoard();
                    } else if (status == 2) {
                        txt_turn.setText(playerYName + " Wins!");
                        disableBoard();
                    } else if (status == -1) {
                        txt_turn.setText("It's a draw!");
                        disableBoard();
                    } else {
                        if (turn == playerXName) {
                            turn = playerYName;
                            txt_turn.setText("Turn: " + playerYName);
                        } else {
                            turn = playerXName;
                            txt_turn.setText("Turn: " + playerXName);
                        }
                    }
                }
            }
        };
    }


    protected void disableBoard() {
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                TextView tv = (TextView) ((TableRow) gameBoard.getChildAt(i)).getChildAt(j);
                tv.setEnabled(false);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}