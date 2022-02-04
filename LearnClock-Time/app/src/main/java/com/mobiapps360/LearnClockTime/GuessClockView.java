package com.mobiapps360.LearnClockTime;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

public class GuessClockView extends View {

    public int height, width = 0;
    public int padding = 0;
    public int fontSize = 0;
    public int numeralSpacing = 0;
    public int handTruncation, hourHandTruncation = 0;
    public int radius = 0;
    public Paint paint;
    public int setHour = 10;
    public int setMinute = 30;

    public boolean isInit;
    public int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    //   public int[] numbers = {1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    public int[] minutes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60};
    //    public int[] numbers = {1, 2, 3, 4, 5, 6};
    public Rect rect = new Rect();
    public Path hour;
    Canvas canvas1;

    public GuessClockView(Context context) {
        super(context);
    }

    public GuessClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GuessClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initClock() {
        System.out.println("------------------------------------------------------");
//        System.out.println("wwww" + getHeight());
//        System.out.println("hhhhh" + getWidth());
        height = getHeight();
        width =  getWidth();
//        height = 700;
//        width =  700;
        padding = numeralSpacing + 50;
        fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20,
                getResources().getDisplayMetrics());
        int min = Math.min(height, width);
        radius = min / 2 - padding;
        System.out.println("---initClock radius****" + (radius));
        System.out.println("---initClock padding****" + (padding));

        handTruncation = min / 20;
        hourHandTruncation = min / 7;
        paint = new Paint();
        isInit = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) { initClock();
        } else {
            int min = Math.min(height, width);
            radius = min / 2 - padding;
            //paint = new Paint();
        }
        System.out.println("onDraw!!!!!");
        canvas1 = canvas;
//        System.out.println("--getWidth***"+width);
//        System.out.println("--getHeight***"+height);
       // drawNumeral();
        drawCircle();
        drawCircleBorder();
        drawCenter();
        drawNumeral();

        drawMinuteMark();

        setHourHand(setHour);
        setMinuteHand(setMinute);
    }

    public void drawHand(Canvas canvas, double loc, boolean isHour) {
        double angle = Math.PI * loc / 30 - Math.PI / 2;
        int handRadius = isHour ? radius - handTruncation - hourHandTruncation : radius - handTruncation - 50;
        paint.setColor(getResources().getColor(android.R.color.black));
        if (!isHour) {
            paint.setStrokeWidth(6f);
        } else {
            paint.setStrokeWidth(10f);
        }
        canvas.drawLine(getWidth() / 2, getHeight() / 2,
                (float) (getWidth() / 2 + Math.cos(angle) * handRadius),
                (float) (getHeight() / 2 + Math.sin(angle) * handRadius),
                paint);
    }

//    public void drawHands(Canvas canvas) {
//        Calendar c = Calendar.getInstance();
//        float hour = c.get(Calendar.HOUR_OF_DAY);
//        hour = hour > 12 ? hour - 12 : hour;
//
////        System.out.println("hour****"+(hour + c.get(Calendar.MINUTE) / 60) * 5f);
////        System.out.println("Minute****"+c.get(Calendar.MINUTE));
//
////        drawHand(canvas, 10 * 5f, true);
////        drawHand(canvas, 10, false);
//        //   drawHand(canvas, c.get(Calendar.SECOND), false);
//
////        drawHand(canvas, (hour + c.get(Calendar.MINUTE) / 60) * 5f, true);
////        drawHand(canvas, c.get(Calendar.MINUTE), false);
////        drawHand(canvas, c.get(Calendar.SECOND), false);
////        setHourHand(11);
////        setMinuteHand(15);
//
//
//    }

    public void setHourHand(double handPosition) {
        handPosition = handPosition * 5f;
        System.out.println("---handPosition hr----"+ handPosition);
        System.out.println("---Math.PI----"+ Math.PI);

        double angle = Math.PI * handPosition / 30 - Math.PI / 2;
        System.out.println("---angle Hour----"+ angle);
        int handRadius = radius - handTruncation - hourHandTruncation + 20;
        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setStrokeWidth(15f);
        canvas1.drawLine(getWidth() / 2, getHeight() / 2,
                (float) (getWidth() / 2 + Math.cos(angle) * handRadius),
                (float) (getHeight() / 2 + Math.sin(angle) * handRadius),
                paint);
    }

    public void setMinuteHand(double handPosition) {
        System.out.println("---handPosition minute----"+ handPosition);
        double angle = Math.PI * handPosition / 30 - Math.PI / 2;
        System.out.println("---angle minute----"+ angle);
        int handRadius = radius - handTruncation - 10;
        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setStrokeWidth(6f);
        canvas1.drawLine(getWidth() / 2, getHeight() / 2,
                (float) (getWidth() / 2 + Math.cos(angle) * handRadius),
                (float) (getHeight() / 2 + Math.sin(angle) * handRadius),
                paint);
    }

    public void drawNumeral() {
        int fixedRadius = radius;
        paint.setTextSize(fontSize);
        radius = radius - 35;
        for (int number : numbers) {
            String tmp = String.valueOf(number);
            paint.getTextBounds(tmp, 0, tmp.length(), rect);
            paint.setColor(Color.parseColor("#1891ff"));
            double angle = Math.PI / 6 * (number - 3);
            int x = (int) (width / 2 + Math.cos(angle) * (radius) - rect.width() / 2);
            int y = (int) (height / 2 + Math.sin(angle) * (radius) + rect.height() / 2);
            paint.setShadowLayer(5.0f, 2.0f, 2.0f, 0xFF000000);
            canvas1.drawText(tmp, x, y, paint);
        }
        radius = fixedRadius;
    }

    public void drawMinuteMark() {

            System.out.println("-------I am inside drawMinuteMark------------------");

//        paint.getTextBounds("9", 0, "9".length(), rect);

        int tempradiusBigMark = radius - 10;
        int tempradiusSmallMark = radius + 5;

        for (int number : minutes) {
            String tmp = String.valueOf(number);
            double angle = Math.PI / 30 * (number - 1);

            //big mark with diff of 5 ADDITIONAL  3- 6- 9-12 big mark
            if ((number == 6) || (number == 11)  || (number == 21) || (number == 26) || (number == 36) || (number == 41) || (number == 51) || (number == 56)) {
                paint.setStrokeWidth(9f);
                paint.setShadowLayer(4.0f, 0.0f, 2.0f, 0xFF000000);

                radius = tempradiusSmallMark;
            } else if ((number == 1) || (number == 16) || (number == 31) || (number == 46)) {
                paint.setStrokeWidth(13);
                paint.setShadowLayer(4.0f, 0.0f, 2.0f, 0xFF000000);

                radius = tempradiusSmallMark;
            } else {
                paint.setStrokeWidth(4f);
                radius = tempradiusSmallMark;
            }
//w 1.93
//h 2.195 var
            //small mark with diff of 5
            int x = (int) (width / 1.93 + Math.cos(angle) * (radius) - rect.width() / 4.5);
            int y = (int) (height / 2.06 + Math.sin(angle) * (radius) + rect.height() / 4.5);
            paint.setColor(Color.parseColor("#fd1494"));
            int minuteMarkLength = 14;
            if ((number == 6) || (number == 11)  || (number == 21) || (number == 26) || (number == 36) || (number == 41) || (number == 51) || (number == 56)) {
                minuteMarkLength = 22;
            } else if ((number == 1) || (number == 16) || (number == 31) || (number == 46)) {
                minuteMarkLength = 24;
            }

            canvas1.drawLine(x, y,
                    (float) (x + Math.cos(angle) * (minuteMarkLength)),
                    (float) (y + Math.sin(angle) * (minuteMarkLength)),
                    paint);
        }

       // radius = fixedRadius;
    }

    public void drawCenter() {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#fd1494"));
        canvas1.drawCircle(width / 2, height / 2, 12, paint);
    }

    public void drawCircle() {
        paint.reset();
        paint.setColor(getResources().getColor(android.R.color.white));
        paint.setAntiAlias(false);
//        System.out.println("---rrrr****"+ (radius + padding - 10));
        canvas1.drawCircle(width / 2, height / 2, radius + padding - 10, paint);
    }


    public void drawCircleBorder() {
        paint.reset();
        paint.setColor(Color.parseColor("#1891ff"));
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setShadowLayer(5.0f, 2.0f, 2.0f, 0xFF000000);
        canvas1.drawCircle(width / 2, height / 2, radius + padding - 10, paint);
    }
}
