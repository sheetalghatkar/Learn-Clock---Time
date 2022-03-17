package com.mobiapps360.LearnClockTime;
import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SetTimeAdapter extends RecyclerView.Adapter<SetTimeAdapter.ViewHolder> {

    private SetTimeItem[] listSetTimeItems;
    private Context context;
    int tempMinAngle = 0;
    int newHourAngle = 0;





    public SetTimeAdapter(Context context) {
        this.context = context;
    }

    public SetTimeAdapter(SetTimeItem[] listSetTimeData) {
        this.listSetTimeItems = listSetTimeData;
    }

    public void setListMenuItem(SetTimeItem[] listSetTimeData) {
        this.listSetTimeItems = listSetTimeData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.set_time_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final SetTimeItem SetTimeItemModel = listSetTimeItems[position];
        System.out.println("onBindViewHolder---");
        newHourAngle = (int)Integer.parseInt(Constant.setTimeStaticHourHand);// SetTimeItemModel.setHourHand;
        tempMinAngle = (int)Integer.parseInt(Constant.setTimeStaticMinuteHand); //SetTimeItemModel.setMinuteHand;
        holder.textViewCardNumber.setText(Html.fromHtml("<b>" + String.valueOf(position+1) + "</b>"+"/"+String.valueOf(listSetTimeItems.length)));
        holder.card_hour_hand.setRotation((float) newHourAngle);
        holder.card_minute_hand.setRotation((float) tempMinAngle);
        System.out.println("---xyzz----"+SetTimeItemModel.setHourHand);
//        holder.txtHourHide.setText(String.valueOf((int) newHourAngle));
//        holder.txtMinuteHide.setText(String.valueOf((int) tempMinAngle));
//        Constant.setTimeStaticHourHand = String.valueOf((int) newHourAngle);
//        Constant.setTimeStaticMinuteHand = String.valueOf((int) tempMinAngle);
    }

    @Override
    public int getItemCount() {
        return listSetTimeItems.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewCardNumber;
        ImageView imgVwSetTimeHourHand;
        ImageView imgVwSetTimeMinuteHand;
        CardView card_hour_hand;
        CardView card_minute_hand;
        ImageView imgVwClockDial;

        public double clockAngle;
        int[] minuteArray;
        double centreY =  Resources.getSystem().getDisplayMetrics().widthPixels/2;     // imgVwClockDial.getHeight()/2;
        double centreX =  Resources.getSystem().getDisplayMetrics().widthPixels/2;    //   imgVwClockDial.getWidth()/2;
        int[] hourArray = new int[]{0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 360};
        List<Integer>  minuteArrayList = new ArrayList<Integer>();
        int hourArrayLength = hourArray.length;
        int iCount = 0;
        boolean isTouchHourHand = true; // to move only Hour hand
        boolean isTouchMinuteHand = true; // to move only Minute hand

        boolean isTouchHourWhileMoving = false; // to Check hour hand touch while moving
        boolean isTouchMinuteWhileMoving = false; //  to Check minute hand touch while moving

       TextView txtHourHide;
       TextView txtMinuteHide;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewCardNumber = itemView.findViewById(R.id.textViewSetTimeCardNumber);
            this.card_hour_hand = itemView.findViewById(R.id.card_set_time_hour_hand);
            this.card_minute_hand = itemView.findViewById(R.id.card_set_time_minute_hand);
            this.imgVwClockDial = itemView.findViewById(R.id.imgVwSetTimeClockDial);
            this.imgVwSetTimeHourHand = itemView.findViewById(R.id.imgVwSetTimeHourHand);
            this.imgVwSetTimeMinuteHand = itemView.findViewById(R.id.imgVwSetTimeMinuteHand);
            this.txtHourHide = itemView.findViewById(R.id.txtHourHide);
            this.txtMinuteHide = itemView.findViewById(R.id.txtMinuteHide);


            System.out.println("ViewHolder---***"+itemView.getTag());


            imgVwSetTimeMinuteHand.setAlpha(0.7f);
            imgVwSetTimeHourHand.setAlpha(0.7f);
            //-----------------------------------------
            do {
                minuteArrayList.add(iCount);
                iCount = iCount + 6;
            } while (iCount < 366);
            minuteArray = new int[minuteArrayList.size()];
            for (int i = 0; i < minuteArray.length; i++) {
                minuteArray[i] = minuteArrayList.get(i);
            }
            //-----------------------------------------
//            txtMinuteHide.setText(String.valueOf((int) tempMinAngle));
//            txtHourHide.setText(String.valueOf((int) newHourAngle));
//            Constant.setTimeStaticHourHand = String.valueOf((int) newHourAngle);
//            Constant.setTimeStaticMinuteHand = String.valueOf((int) tempMinAngle);
            imgVwClockDial.setOnTouchListener(handleTouch);
            //-----------------------------------------

            imgVwSetTimeHourHand.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            System.out.println("Inside hour hand ACTION_DOWN");
                            isTouchHourWhileMoving = true;
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            System.out.println("Inside hour hand ACTION_UP");
                            isTouchHourHand = false;
                            isTouchHourWhileMoving = false;
                            imgVwSetTimeHourHand.setAlpha(1.0f);
                            imgVwSetTimeMinuteHand.setAlpha(0.7f);
                            break;
                        }
                    }
                    return isTouchHourHand;
                }
            });

            //-----------------------------------------
            imgVwSetTimeMinuteHand.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println("imgVwSetTimeMinuteHand touch****");
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            isTouchMinuteWhileMoving = true;
                            System.out.println("Inside minute hand ACTION_DOWN");
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            System.out.println("Inside minute hand ACTION_UP");
                            isTouchMinuteHand = false;
                            isTouchMinuteWhileMoving = false;
                            imgVwSetTimeHourHand.setAlpha(0.7f);
                            imgVwSetTimeMinuteHand.setAlpha(1.0f);
                            break;
                        }
                    }
                    return isTouchMinuteHand;
                }
            });
        }
        //------------------------------------------
        View.OnTouchListener handleTouch = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("***parent Down ***");
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float tapLocationX = (float) event.getX();
                        float tapLocationY = (float) event.getY();
                        double theta = Math.atan2(tapLocationY - centreY, tapLocationX - centreX);
                        theta += Math.PI / 2;
                        clockAngle = Math.toDegrees(theta);
                        if (clockAngle < 0) {
                            clockAngle += 360;
                        }
                        if (!isTouchHourHand && isTouchHourWhileMoving) {
                            isTouchMinuteHand = false;
                            card_hour_hand.setRotation((float) clockAngle);
                            System.out.println("***parent Move hour hand***" + clockAngle);
                        } else if (!isTouchMinuteHand && isTouchMinuteWhileMoving) {
                            if ((int) clockAngle == 0) {
                                clockAngle = 360;
                            }
                            System.out.println("***parent Move minute hand tempAngle***" + tempMinAngle);
                            System.out.println("***parent Move minute hand clockAngle***" + clockAngle);
                            clockAngle = usingBinarySearch((int) clockAngle,minuteArray);
                            card_minute_hand.setRotation((float) clockAngle);
                            if (!(tempMinAngle == (int) clockAngle)) {
                                if ((((int) clockAngle > tempMinAngle) && (((int) clockAngle) - tempMinAngle) > 300) || ((tempMinAngle == 360) && (tempMinAngle - ((int) clockAngle)) < 60)) {
                                    if (newHourAngle == 0) {
                                        newHourAngle = 360;
                                    }
                                    newHourAngle = newHourAngle - 30;

                                    System.out.println("Fisrt If" + newHourAngle);
                                } else if ((tempMinAngle != 360) && (tempMinAngle > (int) clockAngle) && (tempMinAngle - ((int) clockAngle)) > 300) {
                                    if (newHourAngle == 360) {
                                        newHourAngle = 0;
                                    }
                                    newHourAngle = newHourAngle + 30;
                                    System.out.println("second If" + newHourAngle);
                                }
                                tempMinAngle = (int) clockAngle;
                                System.out.println("*** newHourAngle" + newHourAngle);
                                newHourAngle = nearest_small_value(newHourAngle);
                                if (newHourAngle == 360) {
                                    newHourAngle = 0;
                                }
                                newHourAngle = newHourAngle + (tempMinAngle) / 12;
                               // txtHourHide.setText(String.valueOf((int) newHourAngle));
                                Constant.setTimeStaticHourHand = String.valueOf((int) newHourAngle);
                                card_hour_hand.setRotation((float) newHourAngle);
                            }
                           // txtMinuteHide.setText(String.valueOf((int) tempMinAngle));
                            Constant.setTimeStaticMinuteHand = String.valueOf((int) tempMinAngle);
                            System.out.println("***updated newHourAngle" + newHourAngle);
                            System.out.println("***updated parent Move minute hand***" + tempMinAngle);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        System.out.println("***parent up ***");
                        if (!isTouchHourHand && isTouchHourWhileMoving) {
//                        newHourAngle = usingBinarySearch((int) clockAngle, hourArray);
                            System.out.println("***parent up tempMinAngle***"+tempMinAngle);

                            if (tempMinAngle != 360 && tempMinAngle != 0) {
                                newHourAngle = nearest_small_value((int) clockAngle);
                            } else {
                                System.out.println("***parent up temp angle 360***");
                                newHourAngle = usingBinarySearch((int) clockAngle,hourArray);
                            }
                            if (newHourAngle == 360) {
                                newHourAngle = 0;
                            }
                            if (tempMinAngle != 360) {
                                newHourAngle = newHourAngle + (tempMinAngle) / 12;
                            }
                          //  txtHourHide.setText(String.valueOf((int) newHourAngle));
                            Constant.setTimeStaticHourHand = String.valueOf((int) newHourAngle);
                            card_hour_hand.setRotation((float) newHourAngle);
                            System.out.println("***parent Up hour hand***" + newHourAngle);
                        }
                        isTouchMinuteHand = true;
                        isTouchHourHand = true;
                        isTouchHourWhileMoving = false;
                        isTouchMinuteWhileMoving = false;
                        imgVwSetTimeMinuteHand.setAlpha(0.7f);
                        imgVwSetTimeHourHand.setAlpha(0.7f);

                        break;
                }
                return true;
            }
        };

        //------------------------------------------

        int usingBinarySearch(int value, int[] a) {
            if (value <= a[0]) {
                return a[0];
            }
            if (value >= a[a.length - 1]) {
                return a[a.length - 1];
            }

            int result = Arrays.binarySearch(a, value);
            if (result >= 0) {
                return a[result];
            }

            int insertionPoint = -result - 1;
            return (a[insertionPoint] - value) < (value - a[insertionPoint - 1]) ?
                    a[insertionPoint] : a[insertionPoint - 1];
        }
        //------------------------------------------
        int nearest_small_value(int x) {
            int low = 0, high = hourArrayLength - 1, ans = x;
            while (low <= high) {
                int mid = (low + high) / 2;
                if (hourArray[mid] <= ans) {
                    if (hourArray[mid] == ans) {
                        high = hourArrayLength - 1;
                    }
                    low = mid + 1;
                }
                else
                    high = mid - 1;
            }
            if ((low - 1) >= 0) {
                low = low - 1;
            }
            System.out.println("Value:" + x);
            System.out.println("index value:" + hourArray[low]);
            return hourArray[low];
        };
    }
}

