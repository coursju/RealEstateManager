package com.openclassrooms.realestatemanager.controler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.realestatemanager.R;

public class LoanSimulatorActivity extends AppCompatActivity {
    private static final String TAG = "LoanSimulatorActivity";
    private static final String[] LOAN_PERIOD =
            new String[] {"10", "15", "20", "25"};

    private AutoCompleteTextView loanPeriodTxt;
    private TextInputEditText loanTotalAmountTxt;
    private TextInputEditText loanFinancialContributionTxt;
    private TextView loanResultTxtZone;

    private Integer monthlyRepayment;
    private Integer loanCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_simulator);

        configureAutocompleteTxt();
        bindView();
    }

    public void configureAutocompleteTxt(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, LOAN_PERIOD);
        loanPeriodTxt = (AutoCompleteTextView)
                findViewById(R.id.loan_period_txt);
        loanPeriodTxt.setAdapter(adapter);
    }

    public void bindView(){
        loanTotalAmountTxt = findViewById(R.id.loan_total_amount_txt);
        loanFinancialContributionTxt = findViewById(R.id.loan_financial_contribution_txt);
        loanResultTxtZone = findViewById(R.id.loan_result_txt_zone);
    }

    public void showResultButton(View view){
        Double amount = 0.0;
        Double financialContribution = 0.0;

        if (!loanTotalAmountTxt.getText().toString().equals(""))amount = Double.valueOf(loanTotalAmountTxt.getText().toString());
        if (!loanFinancialContributionTxt.getText().toString().equals("") &&
            Double.valueOf(loanFinancialContributionTxt.getText().toString()) < amount){
            financialContribution = Double.valueOf(loanFinancialContributionTxt.getText().toString());
            if (amount != 0){
                Double loanCostDouble = ((amount - financialContribution) * getCreditRatio(loanPeriodTxt.getText().toString())) - (amount - financialContribution);
                Double monthlyRepaymentDouble = ((amount - financialContribution) * getCreditRatio(loanPeriodTxt.getText().toString())) / (Double.parseDouble(loanPeriodTxt.getText().toString()) * 12.0);

                String result = getResources().getString(R.string.loan_monthly_result)+"\n\n"+String.valueOf((monthlyRepaymentDouble.intValue())+"\n\n\n\n"
                        +getResources().getString(R.string.loan_cost_result)+"\n\n"+String.valueOf(loanCostDouble.intValue()));
                loanResultTxtZone.setText(result);
            }else{
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.loan_enter_amount), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.loan_lower_contribution), Toast.LENGTH_SHORT).show();
        }

    }

    public Double getCreditRatio(String creditTime) {
        switch (Integer.parseInt(creditTime)){
            case 10: return 1.05;
            case 15: return 1.1;
            case 20: return 1.15;
            case 25: return 1.2;
            default: return 0.0;
        }
    }
}
