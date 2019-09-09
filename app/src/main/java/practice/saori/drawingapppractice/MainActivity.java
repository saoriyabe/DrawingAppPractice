package practice.saori.drawingapppractice;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.zip.Inflater;

import practice.saori.drawingapppractice.view.DrawingView;

public class MainActivity extends AppCompatActivity {
    DrawingView drawingView;

    // for color change dialog
    View colorCheckView;
    SeekBar redSeekBar;
    SeekBar greenSeekBar;
    SeekBar blueSeekBar;
    AlertDialog currentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawingView = findViewById(R.id.drawing_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.change_line_color:
                showChangeLineColorDialog();
                break;
            case R.id.clear_path:
                drawingView.clear();
                break;
            case R.id.save_image:
                drawingView.saveAsPng();
                break;
        }
        return true;
    }

    private void showChangeLineColorDialog() {
        View view = getLayoutInflater().inflate(R.layout.change_color_dialog, null);
        redSeekBar = view.findViewById(R.id.seekBar_red);
        greenSeekBar = view.findViewById(R.id.seekBar_green);
        blueSeekBar = view.findViewById(R.id.seekBar_blue);
        colorCheckView = view.findViewById(R.id.color_check_view);

        redSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        greenSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        blueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setPositiveButton("Change!", colorButtonListener);
        dialog.setView(view);
        currentDialog = dialog.create();
        currentDialog.setTitle("Change Color");
        currentDialog.show();
    }

    private DialogInterface.OnClickListener colorButtonListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            int redColor = redSeekBar.getProgress();
            int greenColor = greenSeekBar.getProgress();
            int blueColor = blueSeekBar.getProgress();
            drawingView.changeLineColor(Color.argb(255, redColor, greenColor, blueColor));
            currentDialog.dismiss();
            currentDialog = null;
            Toast.makeText(getApplicationContext(), "onClick!", Toast.LENGTH_LONG);
            Log.d("onClickListener", "Color R:" + redColor + " G:" + greenColor + " B:" + blueColor);
        }
    };

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            colorCheckView.setBackgroundColor(
                    Color.argb(255,
                            redSeekBar.getProgress(),
                            greenSeekBar.getProgress(),
                            blueSeekBar.getProgress())
            );
            Toast.makeText(getApplicationContext(), "progress changed", Toast.LENGTH_LONG);
            Log.d("onProgressChanged", "R:" +  redSeekBar.getProgress()
                    + " G: " + greenSeekBar.getProgress()
                    + " B: " + blueSeekBar.getProgress());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
