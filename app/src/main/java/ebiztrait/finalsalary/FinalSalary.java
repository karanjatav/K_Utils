package ebiztrait.finalsalary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class FinalSalary extends AppCompatActivity {

    private String TOTAL_SALARY = "total_salary";
    private String TOTAL_WORKING_DAYS = "total_working_days";
    private String TOTAL_LEAVES = "total_leaves";
    private String FINAL_AMOUNT = "final_amount";

    double totalSalary = 0;
    double totalWorkingDays = 0;
    double totalLeaves = 0;

    double salaryPerDay = 0;
    double totalLeaveDeduction = 0;
    double finalAmount = 0;


    private EditText etTotalSalary;
    private EditText etTotalWorkingDays;
    private EditText etTotalLeaves;
    private TextView tvSalaryPerDay;
    private TextView tvTotalLeaveDeduction;
    private TextView tvFinalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_salary);

        etTotalSalary = findViewById(R.id.et_total_salary);
        etTotalWorkingDays = findViewById(R.id.et_total_working_days);
        etTotalLeaves = findViewById(R.id.et_total_leaves);

        tvSalaryPerDay = findViewById(R.id.tv_salary_per_day);
        tvTotalLeaveDeduction = findViewById(R.id.tv_total_leave_deduction);
        tvFinalAmount = findViewById(R.id.tv_final_amount);

    }

    @Override
    protected void onResume() {
        super.onResume();

        setTextChangeListener(etTotalSalary, etTotalWorkingDays, etTotalLeaves);
        if (!SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(FINAL_AMOUNT, "0").equalsIgnoreCase("0")) {
            etTotalSalary.setText(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(TOTAL_SALARY, "0"));
            etTotalWorkingDays.setText(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(TOTAL_WORKING_DAYS, "0"));
            etTotalLeaves.setText(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(TOTAL_LEAVES, "0"));

            setData();
            //  currentBal = bankBal + cash - savings;
        }


    }

    private double getSalaryPerDay() {
        salaryPerDay = totalSalary / totalWorkingDays;
        tvSalaryPerDay.setText(setDecimals(salaryPerDay));
        return salaryPerDay;
    }


    private double getTotalLeaveDeduction() {
        totalLeaveDeduction = getSalaryPerDay() * totalLeaves;
        tvTotalLeaveDeduction.setText(setDecimals(totalLeaveDeduction));
        return totalLeaveDeduction;
    }

  /*  private double getFinalAmount() {
        finalAmount = totalSalary - getTotalLeaveDeduction();
        tvFinalAmount.setText(String.valueOf(finalAmount));
        return finalAmount;
    }*/


    private void setTextChangeListener(EditText... editText) {

        for (EditText et : editText) {
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {
                    setData();
                }
            });
        }

    }

    private void setData() {

        if (!TextUtils.isEmpty(etTotalSalary.getText().toString()) && !etTotalSalary.getText().toString().equals(".")) {
            totalSalary = Double.valueOf(etTotalSalary.getText().toString());
        } else {
            totalSalary = 0;
        }

        if (!TextUtils.isEmpty(etTotalWorkingDays.getText().toString()) && !etTotalWorkingDays.getText().toString().equals(".")) {
            totalWorkingDays = Double.valueOf(etTotalWorkingDays.getText().toString());
        } else {
            totalWorkingDays = 0;
        }
        if (!TextUtils.isEmpty(etTotalLeaves.getText().toString()) && !etTotalLeaves.getText().toString().equals(".")) {
            totalLeaves = Double.valueOf(etTotalLeaves.getText().toString());
        } else {
            totalLeaves = 0;
        }


        finalAmount = totalSalary - getTotalLeaveDeduction();
        tvFinalAmount.setText(setDecimals(finalAmount));

        Log.d("totalSalary===", setDecimals(totalSalary));
        Log.d("totalWorkingDays===", setDecimals2(totalWorkingDays));
        Log.d("totalLeaves===", setDecimals2(totalLeaves));
        Log.d("salaryPerDay===", setDecimals(salaryPerDay));
        Log.d("totalLeaveDeduction===", setDecimals(totalLeaveDeduction));
        Log.d("finalAmount===", setDecimals(finalAmount));
    }

    private String setDecimals(double amount) {
        DecimalFormat f = new DecimalFormat("##.00");
        return String.valueOf(f.format(amount));
    }

    private String setDecimals2(double amount) {
        DecimalFormat f = new DecimalFormat("##.0");
        return String.valueOf(f.format(amount));
    }


    @Override
    protected void onPause() {
        super.onPause();
        SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(TOTAL_SALARY, setDecimals(totalSalary));
        SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(TOTAL_WORKING_DAYS, setDecimals2(totalWorkingDays));
        SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(TOTAL_LEAVES, setDecimals2(totalLeaves));
        SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(FINAL_AMOUNT, setDecimals(finalAmount));
    }

}
