package net.hananet.bns2.a0214androidstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatePicker datePicker;
    TimePicker timePicker;
    Button btnStart , btnReservation,btnClear;
    TextView tvDate,tvTime, tvCountDown;
    CheckBox cb01,cb02,cb03,cb04,cb05,cb06,cb07,cb08;
    CountDownTimer countDownTimer;

    boolean isStart = false;
    boolean isCountDownEnd = false;
    boolean isClicked = false;
    static int s_year , s_month , s_day , s_hour , s_minute;
    //static 변수는 s_ 를 붙이는 경우가 다수
    CheckBox[] checkBoxes;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setElements();

        checkBoxes = new CheckBox[]{cb01,cb02,cb03,cb04,cb05,cb06,cb07,cb08};
        
        btnStart.setOnClickListener(view ->
        {
            isStart = true;
            tvDate.setText(s_year + " / " + s_month + " / " + s_day);
            tvTime.setText(s_hour + " : " + s_minute);
            doCountDown(); // 카운트다운 시작 메서드
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
        {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute)
            {
                s_hour = hour;
                s_minute = minute;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int year, int month, int day)
                {
                    s_year = year;
                    s_month = month +1;
                    s_day = day;
                }
            });
        }

        btnReservation.setOnClickListener(view ->
        {

            if(!isCountDownEnd && checkBoxCheck())
            {
                Toast.makeText(this,"성공!",Toast.LENGTH_SHORT).show();
                isClicked = true;
            }
            else
            {
                Toast.makeText(this,"실패!",Toast.LENGTH_SHORT).show();
                isClicked = true;
            }
            // 한 번 클릭하면 false 되고 초기화하면 다시 활성화되도록 하기

            if(isClicked && !isStart)
            {
                btnReservation.setClickable(false);
                Toast.makeText(this,"예약 종료 , 초기화 후 다시 시도하세요",Toast.LENGTH_SHORT).show();
            }

        });

        btnClear.setOnClickListener(view ->
        {
            countDownTimer.cancel();
            tvCountDown.setText("00:00");
            isClicked = false;
            btnReservation.setClickable(true);

            for(CheckBox checkBox : checkBoxes)
            {
                checkBox.setChecked(false);
            }

            isCountDownEnd = false;

        });



    }// endOnCreate

    private void doCountDown()
    {
        final int duration = 20; //카운트다운할 초
        countDownTimer = new CountDownTimer(duration*1000 , 1000)
        { //ms로 계산됨 -> 1000을 곱함 , 1000ms (==1초) 간격으로 카운트다운 진행
            int secondsPassed =0; // 카운트다운이 진행된 시간 int 변수
            public void onTick(long millisUntilFinished) // 위에서 주어진 1000ms마다 실행되는 메서드
            {
                secondsPassed ++;
                int remainSeconds = duration - secondsPassed; // 남은 초 = 카운트다운 초기값 - 진행된 초
                int second = remainSeconds%60; // 남은 초를 60(==1분) 으로 나눈 나머지가 표시할 second가 됨
                String strFormatTime  = String.format(("00 : %01d"),second); //초 String 형식으로 변환
                tvCountDown.setText(strFormatTime); // textView에 표시

                if(secondsPassed == duration) // 지난 시간과 지정한 타이머 시간이 같으면
                {
                    cancel(); // 타이머 멈춤(종료)
                    isCountDownEnd = true;
                }

            }

            @Override
            public void onFinish() {
                //타이머 종료 시 동작(현 예제에서는 동작 없음)
            }

        };
        countDownTimer.start(); //타이머 시작 메서드 호출
    }

    //메서드로 빼서 리팩해보려는 시도
    boolean checkBoxCheck()
    {

        boolean checkBoxResult = true;

        for(int i=0;i<8;i++)
        {
            if(!checkBoxes[i].isChecked())
            {
                checkBoxResult = false;
                break;
            }
        }

        return checkBoxResult;
    }
    void setElements()
    {
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        btnReservation = findViewById(R.id.btnReservation);
        btnStart = findViewById(R.id.btnStart);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvCountDown = findViewById(R.id.tvCountDown);
        cb01=findViewById(R.id.cb01);
        cb02=findViewById(R.id.cb02);
        cb03=findViewById(R.id.cb03);
        cb04=findViewById(R.id.cb04);
        cb05=findViewById(R.id.cb05);
        cb06=findViewById(R.id.cb06);
        cb07=findViewById(R.id.cb07);
        cb08=findViewById(R.id.cb08);
        btnClear = findViewById(R.id.btnClear);

    }
}