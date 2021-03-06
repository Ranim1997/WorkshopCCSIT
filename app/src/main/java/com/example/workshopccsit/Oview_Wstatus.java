package com.example.workshopccsit;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class Oview_Wstatus extends AppCompatActivity {
    int O_ID;
    String o_name;
    int w_Id;
    ImageView image;
    TextView title,duration,present,date,location,seatNo;

     ColumnChartView chart;
     ColumnChartData data;
     boolean hasAxes = true;
     boolean hasAxesNames = true;
     boolean hasLabels = true;
     boolean hasLabelForSelected = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity_layout);

        title = findViewById(R.id.w_title) ;
        location=  findViewById(R.id.W_Location) ;
        present= findViewById(R.id.Oname) ;
        date= findViewById(R.id.date) ;
        duration= findViewById(R.id.W_duration) ;
        seatNo= findViewById(R.id.seatsno);
        image= findViewById(R.id.imagelogo);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("iTitle"));
        location.setText(intent.getStringExtra("iLocation"));
        duration.setText(intent.getStringExtra("iDuration"));
        present.setText(intent.getStringExtra("iPresenter"));
        date.setText(intent.getStringExtra("iDate"));
        seatNo.setText(intent.getStringExtra("iSeatNo"));
        O_ID   = intent.getIntExtra("OId", -1);
        o_name = intent.getStringExtra("O_name");
        w_Id = intent.getIntExtra("iID", -1);

    ////
        byte[] Image =  intent.getByteArrayExtra("iImage");
        Bitmap bmp= BitmapFactory.decodeByteArray(Image, 0 , Image.length);
        image.setImageBitmap(bmp);
////
        DBHelper myDB;
        myDB = new DBHelper(this);
        int absent= myDB.absent(w_Id);
        int present = myDB.present(w_Id);

        chart = findViewById(R.id.chartbar);

        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;

        values = new ArrayList<SubcolumnValue>();
        values.add(new SubcolumnValue(absent, Color.parseColor("#9A7D46")).setLabel("Absent"));
        values.add(new SubcolumnValue(present, Color.parseColor("#0D4040")).setLabel("Attended"));

        Column column = new Column(values);
        column.setHasLabels(hasLabels);
        column.setHasLabelsOnlyForSelected(hasLabelForSelected);
        columns.add(column);

        data = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis().setTextColor(Color.GRAY).setAutoGenerated(false);
            Axis axisY = new Axis().setHasLines(true).setLineColor(Color.GRAY);
            if (hasAxesNames) {
                axisX.setTextColor(Color.GRAY).setTextSize(14);
                axisY.setTextColor(Color.GRAY).setTextSize(14);
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }
        chart.setColumnChartData(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sub_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item2_menu:
                logOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logOut(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}












