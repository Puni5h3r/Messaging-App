package com.example.hp.beginnerproject2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageUIAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<ListItemClass> mMessageList;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 0;

    public MessageUIAdapter(Context context, List<ListItemClass> mlistItemClassList){
        this.mContext = context;
        this.mMessageList = mlistItemClassList;

    }
    @Override
    public int getItemCount() {
        return mMessageList.size();
    }



    @Override
    public int getItemViewType(int position) {
        ListItemClass listItemClass = mMessageList.get(position);




        if(listItemClass.getSend_or_receive()==VIEW_TYPE_MESSAGE_RECEIVED){
            return VIEW_TYPE_MESSAGE_RECEIVED;

        }else {
            return VIEW_TYPE_MESSAGE_SENT;
        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view;

        if(viewType== VIEW_TYPE_MESSAGE_RECEIVED){
       view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_user_interface,parent,false);

        return new ReceivedMessageHolder(view);}
        else if(viewType==VIEW_TYPE_MESSAGE_SENT){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_user_interface_send,parent,false);

        return new SentMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ListItemClass listItemClass =  mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(listItemClass);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(listItemClass);
        }


    }

    public class DateShowingHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public DateShowingHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_message_date);
        }
        void bind(ListItemClass listItemClass){


        }


    }


    public  class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        public TextView receivedTime, receivedMessage, textMessageDate;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            receivedTime = itemView.findViewById(R.id.text_message_time);
            receivedMessage = itemView.findViewById(R.id.text_message_body);


        }

        void bind(ListItemClass listItemClass) {
            receivedMessage.setText(listItemClass.getMssg_body());

            // Format the stored timestamp into a readable String using method.
            //   timeText.setText(Utils.formatDateTime(message.getCreatedAt()));

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            String time2 = format.format(Calendar.getInstance().getTime());

            Date date1 = null;
            Date date2 = null;

            try {
                date1 = format.parse(listItemClass.getMssg_time());
                Log.e("date1",date1.toString());

                date2 =format.parse(time2);
                Log.e("date2",date2.toString());
            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("helme", String.valueOf(e));
            }
            if (date1 != null && date2 != null) {

                long difference = date2.getTime() - date1.getTime();

                double second=difference/1000;
                double min=second/60;
                double hour=min/60;
                double day=hour/24;
                //   long checkerOneHour = 1000*60*60;

                Log.e("time","day difference "+day);



                 if(min<1)
                {
                    receivedTime.setText("Just now");

                }
                else if(hour<1.0)
                {

                    int imin = (int) min;
                    String minutez = Integer.toString(imin).concat(" min ago");
                    //long inHr = difference/checkerOneHour;
                    //String x = Long.toString(inHr);
                    receivedTime.setText(minutez);



                }
                else if(hour<24.0){

                    SimpleDateFormat format1 = new SimpleDateFormat("h:mm aa");
                    String hourZ = format1.format(date1);
                    //Log.e("interer",inMin);
                    receivedTime.setText(hourZ);
                } else if(hour>24.0 && hour<48.0)
                {

                    receivedTime.setText("Yesterday");
                }
                else if (hour>48.0)
                {
                    SimpleDateFormat format1 = new SimpleDateFormat("M-dd");
                    String k = format1.format(date1);
                    receivedTime.setText(k);
                    Log.e("kkkk",k);

                }
                Log.e("diffrence", "dif:" + difference);
            }
            else {
                Log.e("diffrence", "dif is null");
            }



            // nameText.setText(message.getSender().getNickname());

        }
    }

        public class SentMessageHolder extends RecyclerView.ViewHolder {
           public TextView messageText, timeText, tv_notSend;

           public SentMessageHolder(View itemView) {
                super(itemView);

                messageText =  itemView.findViewById(R.id.text_message_body);
                timeText =  itemView.findViewById(R.id.text_message_timeE);
                tv_notSend = itemView.findViewById(R.id.send_or_notsend);
            }

            void bind(ListItemClass listItemClass) {
                messageText.setText(listItemClass.getMssg_body());

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                String time2 = format.format(Calendar.getInstance().getTime());

                Date date1 = null;
                Date date2 = null;

                try {
                    date1 = format.parse(listItemClass.getMssg_time());
                    Log.e("date1",date1.toString());

                    date2 =format.parse(time2);
                    Log.e("date2",date2.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e("helme", String.valueOf(e));
                }
                if (date1 != null && date2 != null) {

                    long difference = date2.getTime() - date1.getTime();

                    double second=difference/1000;
                    double min=second/60;
                    double hour=min/60;
                    double day=hour/24;
                    //   long checkerOneHour = 1000*60*60;

                    Log.e("time","day difference "+day);


                    if(20>second){ 

                        if(listItemClass.getStatus()==false){
                            timeText.setText("sending");
                        }else{
                            timeText.setText("send");
                        }
                    }
                    else if(60>second)
                    {
                        timeText.setText("Just now");

                    }
                    else if(hour<1.0)
                    {

                        int imin = (int) min;
                        String minutez = Integer.toString(imin).concat(" min ago");
                        //long inHr = difference/checkerOneHour;
                        //String x = Long.toString(inHr);
                        timeText.setText(minutez);



                    }
                    else if(hour<24.0){

                        SimpleDateFormat format1 = new SimpleDateFormat("h:mm aa");
                        String hourZ = format1.format(date1);
                        //Log.e("interer",inMin);
                        timeText.setText(hourZ);
                    } else if(hour>24.0 && hour<48.0)
                    {

                        timeText.setText("Yesterday");
                    }
                    else if (hour>48.0)
                    {
                        SimpleDateFormat format1 = new SimpleDateFormat("M-dd");
                        String k = format1.format(date1);
                        timeText.setText(k);
                        Log.e("kkkk",k);

                    }
                    Log.e("diffrence", "dif:" + difference);
                }
                else {
                    Log.e("diffrence", "dif is null");
                }


                if(listItemClass.getStatus()==false){
                    tv_notSend.setText("not send");
                }else{
                    tv_notSend.setText("    ");
                    }

                // Format the stored timestamp into a readable String using method.
            //    timeText.setText(Utils.formatDateTime(message.getCreatedAt()));
            }
        }




    }






