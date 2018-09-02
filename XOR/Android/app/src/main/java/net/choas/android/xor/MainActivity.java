package net.choas.android.xor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.AssetFileDescriptor;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.tensorflow.lite.Interpreter;


public class MainActivity extends AppCompatActivity {
    private Interpreter tflite;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView result00 = findViewById(R.id.result0xor0);
        TextView result01 = findViewById(R.id.result0xor1);
        TextView result10 = findViewById(R.id.result1xor0);
        TextView result11 = findViewById(R.id.result1xor1);

        try {
            tflite = new Interpreter(loadModelFile("xor.tflite"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        result00.setText(prediction(0, 0));
        result01.setText(prediction(0, 1));
        result10.setText(prediction(1, 0));
        result11.setText(prediction(1, 1));
    }

    private String prediction(int a, int b) {
        float[][] in = new float[][]{{a, b}};
        float[][] out = new float[][]{{0}};
        tflite.run(in, out);
        return String.valueOf(out[0][0]);
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
