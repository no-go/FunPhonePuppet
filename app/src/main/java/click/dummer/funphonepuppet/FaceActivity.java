package click.dummer.funphonepuppet;

import android.app.Activity;
import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class FaceActivity extends Activity implements SensorEventListener {
    public static String PACKAGE_NAME;
    public static final int BLINKY_TIME = 100;

    SensorManager sensorManager;
    Sensor sensor;
    TextView valueView;
    ImageView imageView;
    ArrayList<Bitmap> bitmaps;
    String[] bitmapNames = {"blinky.jpg", "normal.jpg", "force1.jpg", "force2.jpg"};
    int[] forces = {0, 20, 30};

    Handler mHandler = new Handler();
    Handler sleepHandler = new Handler();

    Runnable undoBlinki = new Runnable() {
        @Override
        public void run() {
            sensorManager.registerListener(FaceActivity.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            int i = new Random().nextInt(5000);
            mHandler.postDelayed(doBlinki, 500 + i);
        }
    };

    Runnable doBlinki = new Runnable() {
        @Override
        public void run() {
            imageView.setImageBitmap(bitmaps.get(0));
            sensorManager.unregisterListener(FaceActivity.this);
            sleepHandler.postDelayed(undoBlinki, BLINKY_TIME);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face);
        valueView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        PACKAGE_NAME = this.getPackageName();

        String path = makeDir();
        if(path != null) {
            storeDefaultBitmaps(path);
            loadBitmaps(path);
            setBackgrounds();
            mHandler.postDelayed(doBlinki, 5000);
        }
        toggleFullscreen();
    }

    private String makeDir() {
        File file = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            file = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                    PACKAGE_NAME
            );
        } else {
            file = new File(Environment.getExternalStorageDirectory() + "/Documents/"+PACKAGE_NAME);
        }

        String path = file.getPath() + "/";
        try {
            file.mkdirs();
            file = new File(path);
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.fileErr) + ": " + file.getPath(), Toast.LENGTH_SHORT).show();
            return null;
        }
        return path;
    }

    private void storeDefaultBitmaps(String path) {
        storeMipmap(path + bitmapNames[0], R.mipmap.blinky);
        storeMipmap(path + bitmapNames[1], R.mipmap.normal);
        storeMipmap(path + bitmapNames[2], R.mipmap.force1);
        storeMipmap(path + bitmapNames[3], R.mipmap.force2);
    }

    private void storeMipmap(String filename, int res) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), res);
        FileOutputStream out = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                out = new FileOutputStream(filename);
                b.compress(Bitmap.CompressFormat.JPEG, 90, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadBitmaps(String path) {
        bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeFile(path + bitmapNames[0]));
        bitmaps.add(BitmapFactory.decodeFile(path + bitmapNames[1]));
        bitmaps.add(BitmapFactory.decodeFile(path + bitmapNames[2]));
        bitmaps.add(BitmapFactory.decodeFile(path + bitmapNames[3]));
    }

    private void setBackgrounds() {
        int pixel = bitmaps.get(0).getPixel(0,0);
        imageView.setBackgroundColor(pixel);
        valueView.setBackgroundColor(pixel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Float v = Math.abs(sensorEvent.values[0]) + Math.abs(sensorEvent.values[1]) + Math.abs(sensorEvent.values[2]);
        int val = Math.round(v);
        valueView.setText(Integer.toString(val));
        if (val < forces[1]) {
            imageView.setImageBitmap(bitmaps.get(1));
        } else if (val < forces[2]) {
            imageView.setImageBitmap(bitmaps.get(2));
        } else {
            imageView.setImageBitmap(bitmaps.get(3));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    public void toggleFullscreen() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;

        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }
}
