package com.japhdroid.superfit2;

/**
 * Created by User on 14.01.2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Map<Course, List<Lesson>> mDataset;
    private List<Course> sorting;
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
    public MyAdapter(Context adapterContext, Map<Course, List<Lesson>> myDataset, List<Course> sorting) {
        this.adapterContext = adapterContext;
        mDataset = myDataset;
        this.sorting = sorting;
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
        List<Lesson> _list = mDataset.get(sorting.get(position));
        final String name = _list.get(0).getCourse().getTitle();
        String duration = String.valueOf(_list.get(0).getCourse().getDuration());
        holder.txtHeader.setText(name + " (" + duration + "m)");
        holder.txtHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(name);
            }
        });

        boolean firstItem = true;
        int i = 0;
        LayoutInflater inflater = LayoutInflater.from(adapterContext);
        for (final Lesson lesson : _list) {
            if (lesson.lessonIsOver())
                continue;
            if (firstItem)
                holder.course.removeAllViews();
            firstItem = false;
            if (i > 4)
                break;
            View inflatedLayout = inflater.inflate(R.layout.lesson_layout, holder.course, false);
            holder.course.addView(inflatedLayout);
            TextView lessonId = (TextView) inflatedLayout.findViewById(R.id.lessonId_name);
            lessonId.setText(String.valueOf(lesson.getId()));
            inflatedLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // UI code here
                            TextView location = (TextView) v.findViewById(R.id.lessonLocation);
                            TextView starttime = (TextView) v.findViewById(R.id.lessonStarttime);
                            TextView lessonId = (TextView) v.findViewById(R.id.lessonId_name);
                            String message = location.getText() + " @ " + starttime.getText() + " [id:" + lessonId.getText() + "]";
                            Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                            new RelatedLessons(v.getContext(), lesson);
                        }
                    });
                }
            });
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
            setBackgroundAndTimeForRunningLesson(lesson, starttime);
            setTextColorForEnglishLesson(lesson, starttime);
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
            i++;
        }
    }

    private void setBackgroundAndTimeForRunningLesson(Lesson lesson, TextView starttime) {
        if (new Date().getTime() >= lesson.getStarttimeExact().getTime()) {
            starttime.setBackgroundColor(Color.parseColor("#B1DEB6"));
            starttime.setText("-" + DateTimeParser.getTimeStringFromDate(lesson.getEndtime()));
        }
    }

    private void setTextColorForEnglishLesson(Lesson lesson, TextView starttime) {
        if (lesson.isEnglish()) {
            starttime.setTextColor(Color.RED);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}