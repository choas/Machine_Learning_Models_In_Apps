package net.choas.android.inceptionv3;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Locale;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TFLiteInceptionV3";

    private static final String modelFile="inception_v3.tflite";
    private static final String labelFilename="labels.txt";

    private Interpreter tflite;

    private static final int DIM_BATCH_SIZE = 1;
    private static final int DIM_PIXEL_SIZE = 3 * 4; // 4 = Float.BYTES
    private static final int DIM_IMG_SIZE_X = 299;
    private static final int DIM_IMG_SIZE_Y = 299;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;

    private Vector<String> labels = new Vector<>();

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(this.getAssets().open(labelFilename)));
            String line;
            while ((line = reader.readLine()) != null) {
                labels.add(line);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException("Problem reading label file!", e);
        }

        try {
            tflite = new Interpreter(loadModelFile(modelFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Context context = this;

        ImageButton button1 = findViewById(R.id.image1Button);
        ImageButton button2 = findViewById(R.id.image2Button);
        ImageButton button3 = findViewById(R.id.image3Button);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                predict(context, R.drawable.image1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                predict(context, R.drawable.image2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                predict(context, R.drawable.image3);
            }
        });

        result = findViewById(R.id.result);
    }

    private void predict(Context context, int img) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        ImageView image = findViewById(R.id.image);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), img, options);
        image.setImageBitmap(bitmap);

        ByteBuffer imgBuf = convertBitmapToByteBuffer(bitmap);
        float[][] labelProb = new float[1][labels.size()];
        tflite.run(imgBuf, labelProb);

        String label = "";
        Float percent = -1.0f;

        for(int i = 0; i < labels.size(); i++) {
            if (labelProb[0][i] > 0.05) {
                Log.d(TAG, " >>>> " + i + " " + labels.get(i) + " = " + labelProb[0][i]);

                if (labelProb[0][i] > percent) {
                    percent = labelProb[0][i];
                    label = labels.get(i);
                }
            }
        }

        percent *= 100;
        String resultText = String.format(Locale.getDefault(),"%d%% %s", percent.intValue(), label);
        result.setText(resultText);
    }

    // taken from https://github.com/tensorflow/tensorflow/blob/master/tensorflow/contrib/lite/java/demo/app/src/main/java/com/example/android/tflitecamerademo/ImageClassifier.java
    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer imgData = ByteBuffer.allocateDirect(
                DIM_BATCH_SIZE * DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y * DIM_PIXEL_SIZE);
        imgData.order(ByteOrder.nativeOrder());
        imgData.rewind();

        int[] intValues = new int[DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y];

        bitmap = Bitmap.createScaledBitmap(bitmap, DIM_IMG_SIZE_X, DIM_IMG_SIZE_Y, false);

        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        // Convert the image to floating point.
        int pixel = 0;
        long startTime = SystemClock.uptimeMillis();
        for (int i = 0; i < DIM_IMG_SIZE_X; ++i) {
            for (int j = 0; j < DIM_IMG_SIZE_Y; ++j) {
                final int val = intValues[pixel++];

                // taken from https://github.com/tensorflow/tensorflow/issues/14719#issuecomment-348991399
                imgData.putFloat((((val >> 16) & 0xFF)-IMAGE_MEAN)/IMAGE_STD);
                imgData.putFloat((((val >> 8) & 0xFF)-IMAGE_MEAN)/IMAGE_STD);
                imgData.putFloat(((val & 0xFF)-IMAGE_MEAN)/IMAGE_STD);
            }
        }
        long endTime = SystemClock.uptimeMillis();
        Log.d(TAG, "Timecost to put values into ByteBuffer: " + Long.toString(endTime - startTime));

        return imgData;
    }

    private MappedByteBuffer loadModelFile(String modelFile) throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd(modelFile);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}
