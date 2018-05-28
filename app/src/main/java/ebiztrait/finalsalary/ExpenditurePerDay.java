package ebiztrait.finalsalary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExpenditurePerDay extends AppCompatActivity {

    private String GEN_SAL_DATE = "gen_sal_date";
    private String LAST_AMOUNT = "last_amount";
    private String BANK_AMOUNT = "bank_amount";
    private String CASH_AMOUNT = "cash_amount";
    private String SAVINGS_AMOUNT = "savings_amount";


    private EditText generalSalaryDateEt;
    private EditText bankBalEt;
    private EditText cashEt;
    private EditText savingEt;

    private TextView currentBalTv;
    private TextView nxtSalDateTv;
    private TextView currentDateTv;
    private TextView nxtSalDaysTv;
    private TextView dailyExpenseTv;

    private String todaysDate;

    private int generalSalaryDate, nextSalaryDate, bankBal = 0, cash = 0, savings = 0, currentBal;

    private String TAG = "@@KWALLET==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure_per_day);

        generalSalaryDateEt = findViewById(R.id.gen_sal_date_et);
        bankBalEt = findViewById(R.id.bank_bal_et);
        cashEt = findViewById(R.id.cash_et);
        savingEt = findViewById(R.id.saving_et);

        currentBalTv = findViewById(R.id.current_bal_tv);
        nxtSalDateTv = findViewById(R.id.nxt_sal_date_tv);
        currentDateTv = findViewById(R.id.current_date_tv);
        nxtSalDaysTv = findViewById(R.id.nxt_sal_days_tv);
        dailyExpenseTv = findViewById(R.id.daily_expense_tv);

        bankBalEt.requestFocus();

    }


    @Override
    protected void onResume() {
        super.onResume();

        if (!SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(LAST_AMOUNT, "").equalsIgnoreCase("")) {
            generalSalaryDate = Integer.parseInt(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(GEN_SAL_DATE, "0"));
            bankBal = Integer.parseInt(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(BANK_AMOUNT, "0"));
            cash = Integer.parseInt(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(CASH_AMOUNT, "0"));
            savings = Integer.parseInt(SmartApplication.REF_SMART_APPLICATION.readSharedPreferences().getString(SAVINGS_AMOUNT, "0"));

            currentBal = bankBal + cash - savings;
        }
        generalSalaryDateEt.setText(String.valueOf(generalSalaryDate));
        generalSalaryDateEt.setSelection(generalSalaryDateEt.length());

        bankBalEt.setText(String.valueOf(bankBal));
        bankBalEt.setSelection(bankBalEt.length());

        cashEt.setText(String.valueOf(cash));
        cashEt.setSelection(cashEt.length());

        savingEt.setText(String.valueOf(savings));
        savingEt.setSelection(savingEt.length());

        currentBalTv.setText(String.valueOf(currentBal));

        Calendar c = Calendar.getInstance();

        todaysDate = c.get(Calendar.DATE) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.YEAR);


        setOnValueChangeListner(bankBalEt);
        setOnValueChangeListner(cashEt);
        setOnValueChangeListner(savingEt);
        setOnValueChangeListner(generalSalaryDateEt);


        setData();
        }


    private void setData() {
        int remainingDays = remaningDaysCounter(dateToTimeStamp(getCurrentMonthSalaryDate()));

        if (remainingDays >= 0) {
            calculateDaysAndDailyExpense(todaysDate, getCurrentMonthSalaryDate(), remainingDays, currentBal);
            Log.d(TAG, "currentMonthSalDay==" + getCurrentMonthSalaryDate());
        } else {
            remainingDays = remaningDaysCounter(dateToTimeStamp(getNextMonthSalaryDate()));
            calculateDaysAndDailyExpense(todaysDate, getNextMonthSalaryDate(), remainingDays, currentBal);
            Log.d(TAG, "nextMonthSalDay==" + getNextMonthSalaryDate());
        }
    }


    private void calculateDaysAndDailyExpense(String currentDate, String salaryDate, int remainingDays, int currentBal) {
        if (remainingDays == 0) {
            remainingDays = 1;
        }
        String dailyExpense = String.valueOf(currentBal / remainingDays);

        currentBalTv.setText(String.valueOf(currentBal));
        dailyExpenseTv.setText(dailyExpense + " / day");
        currentDateTv.setText(dateFormatChanger2(currentDate));
        nxtSalDateTv.setText(dateFormatChanger2(salaryDate));
        nxtSalDaysTv.setText(remainingDays + " days" );
    }


    private String getCurrentMonthSalaryDate() {
        Calendar c = Calendar.getInstance();

        String currentMonthdate = generalSalaryDate + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.YEAR);

        if (getDayByDate(currentMonthdate) == 7) {
            nextSalaryDate = generalSalaryDate + 2;
        } else if (getDayByDate(currentMonthdate) == 1) {
            nextSalaryDate = generalSalaryDate + 1;
        } else {
            nextSalaryDate = generalSalaryDate;
        }
        return nextSalaryDate + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.YEAR);
    }


    private String getNextMonthSalaryDate() {
        Calendar c = Calendar.getInstance();
        int nextSalaryMonth = c.get(Calendar.MONTH) + 2, nextSalaryYear;

        if (nextSalaryMonth == 13) {
            nextSalaryMonth = 1;
            nextSalaryYear = c.get(Calendar.YEAR) + 1;
        } else {
            nextSalaryYear = c.get(Calendar.YEAR);
        }

        String currentMonthdate = generalSalaryDate + "-" + nextSalaryMonth + "-" + nextSalaryYear;

        if (getDayByDate(currentMonthdate) == 7) {
            nextSalaryDate = generalSalaryDate + 2;
        } else if (getDayByDate(currentMonthdate) == 1) {
            nextSalaryDate = generalSalaryDate + 1;
        } else {
            nextSalaryDate = generalSalaryDate;
        }
        return nextSalaryDate + "-" + nextSalaryMonth + "-" + nextSalaryYear;
    }


    private long dateToTimeStamp(String date) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date returnDate = null;
        try {
            returnDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long output = returnDate.getTime() / 1000L;
        String str = Long.toString(output);
        return Long.parseLong(str) * 1000;
    }


    private int remaningDaysCounter(long nextMonthdate) {

        long diff = nextMonthdate - dateToTimeStamp(todaysDate);
        Log.d(TAG, "nextMonthDate==" + nextMonthdate + "------" + "thisMonthDate==" + dateToTimeStamp(todaysDate));
        float dayCount = (float) diff / (24 * 60 * 60 * 1000);


        return (int) dayCount;
    }


    private void setOnValueChangeListner(final EditText valuesEt) {
        valuesEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int sourceValue;

                if (!TextUtils.isEmpty(charSequence.toString())) {
                    sourceValue = Integer.parseInt(charSequence.toString());
                } else {
                    sourceValue = 0;
                }
                if (valuesEt.getId() == generalSalaryDateEt.getId()) {
                    generalSalaryDate = sourceValue;
                } else if (valuesEt.getId() == bankBalEt.getId()) {
                    bankBal = sourceValue;
                } else if (valuesEt.getId() == cashEt.getId()) {
                    cash = sourceValue;
                } else if (valuesEt.getId() == savingEt.getId()) {
                    savings = sourceValue;
                }
                currentBal = bankBal + cash - savings;

                Log.d(TAG, "generalSalaryDate==" + generalSalaryDate);

                setData();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(GEN_SAL_DATE, String.valueOf(generalSalaryDate));
        SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(LAST_AMOUNT, String.valueOf(currentBal));
        SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(BANK_AMOUNT, String.valueOf(bankBal));
        SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(SAVINGS_AMOUNT, String.valueOf(savings));
        SmartApplication.REF_SMART_APPLICATION.writeSharedPreferences(CASH_AMOUNT, String.valueOf(cash));
    }

    public static String dateFormatChanger2(String date_str) {
        String formattedDate = "";
        try {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(date_str);
            formattedDate = new SimpleDateFormat("MMM,dd yyyy EEEE").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static int getDayByDate(String date) {
        java.text.DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date returnDate = null;
        try {
            returnDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(returnDate);
        return c.get(Calendar.DAY_OF_WEEK);
    }
}