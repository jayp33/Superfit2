package com.japhdroid.superfit2;

/**
 * Created by User on 14.01.2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Map<Course, List<Lesson>> mDataset;
    private Context adapterContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public LinearLayout course;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.courseHeader);
            course = (LinearLayout) v.findViewById(R.id.lessonItem);
        }
    }

    public void add(int position, String item) {
//        mDataset.add(position, item);
//        notifyItemInserted(position);
    }

    public void remove(String item) {
//        int position = mDataset.indexOf(item);
//        mDataset.remove(position);
//        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context adapterContext, Map<Course, List<Lesson>> myDataset) {
        this.adapterContext = adapterContext;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Object key = mDataset.keySet().toArray()[position];
        List<Lesson> _list = mDataset.get(key);
        final String name = _list.get(0).getCourse().getTitle();
        holder.txtHeader.setText(name);
        holder.txtHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(name);
            }
        });

        LayoutInflater inflater = LayoutInflater.from(adapterContext);
        for (Lesson lesson : _list) {
            if (DateTimeParser.getDaysInTheFutureCount(lesson.getStarttimeExact()) > 2)
                continue;
            View inflatedLayout = inflater.inflate(R.layout.lesson_layout, holder.course, false);
            holder.course.addView(inflatedLayout);
            View capacity = inflatedLayout.findViewById(R.id.lessonCapacity);
            TextView starttime = (TextView) inflatedLayout.findViewById(R.id.lessonStarttime);
            TextView location = (TextView) inflatedLayout.findViewById(R.id.lessonLocation);
            TextView futureDays = (TextView) inflatedLayout.findViewById(R.id.lessonFutureDays);
            switch (lesson.getCapacity()) {
                case GREEN:
                    capacity.setBackgroundColor(Color.GREEN);
                    break;
                case YELLOW:
                    capacity.setBackgroundColor(Color.YELLOW);
                    break;
                case RED:
                    capacity.setBackgroundColor(Color.RED);
                    break;
            }
            starttime.setText(DateTimeParser.getTimeStringFromDate(lesson.getStarttime()));
            String strLocation = lesson.getStudio().getTitleShort();
            location.setText(strLocation);
            switch (strLocation) {
                case "FRI":
                    location.setBackgroundColor(Color.parseColor("#FFE29E"));
                    futureDays.setBackgroundColor(Color.parseColor("#FFE29E"));
                    break;
                case "MIT":
                    location.setBackgroundColor(Color.parseColor("#DECCB1"));
                    futureDays.setBackgroundColor(Color.parseColor("#DECCB1"));
                    break;
            }
            futureDays.setText(DateTimeParser.getDaysInTheFuture(lesson.getStarttimeExact()));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}