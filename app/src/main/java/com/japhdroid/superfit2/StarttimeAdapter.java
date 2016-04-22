package com.japhdroid.superfit2;

/**
 * Created by User on 17.04.2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class StarttimeAdapter extends RecyclerView.Adapter<StarttimeAdapter.ViewHolder> {
    private TreeMap<Date, List<Lesson>> mDataset;
    private Context adapterContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public LinearLayout starttimeGroup;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.starttimeHeader);
            starttimeGroup = (LinearLayout) v.findViewById(R.id.starttimeGroup);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public StarttimeAdapter(Context adapterContext, TreeMap<Date, List<Lesson>> myDataset) {
        this.adapterContext = adapterContext;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StarttimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.starttime_group_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Map.Entry<Date, List<Lesson>> entry = getMapElementAtPosition(position);
        Date time = entry.getKey();
        List<Lesson> _list = entry.getValue();
        final String timeTitle = DateTimeParser.getTimeAndDayStringFromDateAsOfToday(time);
        holder.txtHeader.setText(timeTitle);

        boolean firstItem = true;
        LayoutInflater inflater = LayoutInflater.from(adapterContext);
        for (Lesson lesson : _list) {
            if (lesson.lessonIsOver())
                continue;
            if (firstItem)
                holder.starttimeGroup.removeAllViews();
            firstItem = false;
            View inflatedLayout = inflater.inflate(R.layout.starttime_layout, holder.starttimeGroup, false);
            holder.starttimeGroup.addView(inflatedLayout);
            View capacity = inflatedLayout.findViewById(R.id.lessonCapacity);
            TextView courseName = (TextView) inflatedLayout.findViewById(R.id.courseName);
            TextView courseType = (TextView) inflatedLayout.findViewById(R.id.courseType);
            TextView location = (TextView) inflatedLayout.findViewById(R.id.lessonLocation);
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
            setTextColorForEnglishLesson(lesson, courseName);
            courseType.setText(lesson.getCourse().getFloor().name().substring(0, 4));
            String strLocation = lesson.getStudio().getTitleShort();
            location.setText(strLocation);
            switch (strLocation) {
                case "FRI":
                    courseName.setBackgroundColor(Color.parseColor("#FFE29E"));
                    break;
                case "MIT":
                    courseName.setBackgroundColor(Color.parseColor("#DECCB1"));
                    break;
            }
            courseName.setText(lesson.getCourse().getTitle());
        }
    }

    private Map.Entry<Date, List<Lesson>> getMapElementAtPosition(int position) {
        int i = 0;
        for (Map.Entry<Date, List<Lesson>> entry : mDataset.entrySet()) {
            if (position == i) {
                return entry;
            }
            i++;
        }
        throw new NoSuchElementException();
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