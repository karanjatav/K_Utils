package ebiztrait.finalsalary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Home extends AppCompatActivity {

    private TextView tvExpPerDay;
    private TextView tvFinalSalary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvExpPerDay = findViewById(R.id.tv_exp_per_day);
        tvFinalSalary = findViewById(R.id.tv_final_salary);

        setActions();
    }

    private void setActions() {
        tvExpPerDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ExpenditurePerDay.class));
            }
        });

        tvFinalSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, FinalSalary.class));
            }
        });
    }
}
