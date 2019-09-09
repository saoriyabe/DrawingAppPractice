package practice.saori.drawingapppractice.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DrawingView extends View {
    private Paint paintLine;
    private Path path;
    private Bitmap bitmap;
    private Canvas canvas;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintLine = new Paint();
        path = new Path();

        // set paint parameters
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.STROKE); //Line
        paintLine.setStrokeWidth(5f);
        paintLine.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Prepare bitmap for save
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int action = event.getActionMasked();
        Log.d("Motion Event Action:", String.valueOf(action));

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(x, y);
                canvas.drawPath(path, paintLine);
                invalidate();
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paintLine);
    }

    public void clear() {
        path.reset();
        invalidate();
    }

    public void saveAsPng() {
        try {
            File extStrageDir =  (new ContextWrapper(getContext())).getDir("imageDir", Context.MODE_PRIVATE);
            File file = new File(
                    extStrageDir.getAbsolutePath(), "save_image.png");
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

            Toast.makeText(getContext(), "Image saved", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeLineColor(int color) {
        paintLine.setColor(color);
    }
}
