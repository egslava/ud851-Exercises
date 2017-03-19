package com.example.android.waitlist;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.waitlist.data.WaitlistContract;


public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {

    private static final String TAG = "GuestListAdapter";
    private final int columnIndexGuestName;
    private final int columnIndexPartySize;
    private final int columnIndexTimeStamp;
    private Context mContext;
    private Cursor mCursor;

    /**
     * Constructor using the context and the db cursor
     * @param context the calling context/activity
     * @param cursor
     */
    public GuestListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;

        columnIndexGuestName = cursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME);
        columnIndexPartySize = cursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE);
        columnIndexTimeStamp = cursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP);

    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            Log.d(TAG, "moveToPosition returned false");
            return;
        }

        final String stringGuestName = mCursor.getString(columnIndexGuestName);
        final int intPartySize = mCursor.getInt(columnIndexPartySize);

        holder.nameTextView.setText(stringGuestName);
        holder.partySizeTextView.setText(String.valueOf(intPartySize));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class GuestViewHolder extends RecyclerView.ViewHolder {

        // Will display the guest name
        TextView nameTextView;
        // Will display the party size number
        TextView partySizeTextView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                 {@link GuestListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public GuestViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            partySizeTextView = (TextView) itemView.findViewById(R.id.party_size_text_view);
        }

    }
}