package com.example.hp.beginnerproject2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MssgAdapter extends RecyclerView.Adapter<MssgAdapter.MyViewHolder> {

    private List<ListItemClass> fromList;
    private Context mContext;
    RelativeLayout linearLayout;
    private MyDATABase myDATABase;
    View divider;
               public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
                public TextView tv_from;
                public TextView mssg;
                public TextView time;
                Context cContext;
                List<ListItemClass> lListItemClasses;

                public MyViewHolder(View view, Context context, List<ListItemClass> listItemClasses) {
                    super(view);
                    this.lListItemClasses=listItemClasses;
                    this.cContext=context;
                    view.setOnClickListener(this);
                    //view.setOnLongClickListener(this);
                    tv_from = view.findViewById(R.id.textView);
                    mssg = view.findViewById(R.id.textView2);
                    linearLayout = view.findViewById(R.id.linear_layout);
                    divider = view.findViewById(R.id.divider);
                    time = view.findViewById(R.id.text_time);

                }

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ListItemClass listItemClass = this.lListItemClasses.get(position);
                    String phonenumber = listItemClass.getMssg_from();
                    Intent intent = new Intent(this.cContext,Main2Activity.class);
                    myDATABase=new MyDATABase(mContext);
                    int k = myDATABase.upDateStatus(phonenumber,1);
                    tv_from.setTypeface(null,Typeface.NORMAL);
                    tv_from.setText(phonenumber);
                    intent.putExtra("key", phonenumber);
                    cContext.startActivity(intent);




                }



               }

    public MssgAdapter(List<ListItemClass> listItemClasses, Context context) {
        this.fromList = listItemClasses;
        this.mContext = context;

    }


    @NonNull
    @Override
    public MssgAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mssg_list_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView,mContext,fromList);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MssgAdapter.MyViewHolder holder, final int position) {
        ListItemClass listItemClass = fromList.get(position);


        String Mssg_from = listItemClass.getMssg_from();
        holder.mssg.setText(listItemClass.getMssg_body());
        myDATABase=new MyDATABase(mContext);

        int statusCount = myDATABase.getStatus(listItemClass.getMssg_from());
        if(statusCount>0){
            holder.tv_from.setText(Mssg_from+"("+statusCount+")");
          holder.tv_from.setTypeface(null, Typeface.BOLD);
          }else{
            holder.tv_from.setText(Mssg_from);
            holder.tv_from.setTypeface(null,Typeface.NORMAL);
        }

        Log.e("TIMEPPPPPPPPP", listItemClass.getMssg_time());

        Log.e("tv_from",listItemClass.getMssg_from());


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
                holder.time.setText("Just now");

            }
            else if(hour<1.0)
            {

                             int imin = (int) min;
                           String minutez = Integer.toString(imin).concat(" min ago");
                         //long inHr = difference/checkerOneHour;
                            //String x = Long.toString(inHr);
                holder.time.setText(minutez);



            }
            else if(hour<24.0){

                SimpleDateFormat format1 = new SimpleDateFormat("h:mm aa");
               String hourZ = format1.format(date1);
                //Log.e("interer",inMin);
                holder.time.setText(hourZ);
            } else if(hour>24.0 && hour<48.0)
            {

                holder.time.setText("Yesterday");
            }
            else if (hour>48.0)
            {
                SimpleDateFormat format1 = new SimpleDateFormat("M-dd");
                String k = format1.format(date1);
                holder.time.setText(k);
                Log.e("kkkk",k);

            }
            Log.e("diffrence", "dif:" + difference);
        }
        else {
            Log.e("diffrence", "dif is null");
        }
        //holder.time.setText(listItemClass.getMssg_time());
        if (position == getItemCount()) {//check if this is the last child, if yes then hide the divider
            divider.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return fromList.size();


    }

    public void setFilter(List<ListItemClass> newList){

                   fromList = new ArrayList<>();
                   fromList.addAll(newList);
                   notifyDataSetChanged();

    }



}