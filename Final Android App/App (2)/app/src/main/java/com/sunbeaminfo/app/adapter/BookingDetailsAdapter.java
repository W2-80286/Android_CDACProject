package com.sunbeaminfo.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sunbeaminfo.app.Entity.BookingDetail;
import com.sunbeaminfo.app.R;

import java.util.List;

public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.ViewHolder> {

    private List<BookingDetail> bookingDetailsList;

    public BookingDetailsAdapter(List<BookingDetail> bookingDetailsList) {
        this.bookingDetailsList = bookingDetailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingDetail bookingDetail = bookingDetailsList.get(position);
        holder.bind(bookingDetail);
    }

    @Override
    public int getItemCount() {
        return bookingDetailsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView eventNameTextView;
        private TextView venueNameTextView;
        private TextView attendeesTextView;
        private TextView totalPriceTextView;
        private TextView bookingDateTextView;
        private TextView bookingTimestampTextView;
        private TextView paymentStatusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.eventNameTextView);
            venueNameTextView = itemView.findViewById(R.id.venueNameTextView);
            attendeesTextView = itemView.findViewById(R.id.attendeesTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);
            bookingDateTextView = itemView.findViewById(R.id.bookingDateTextView);
            bookingTimestampTextView = itemView.findViewById(R.id.bookingTimestampTextView);
            paymentStatusTextView = itemView.findViewById(R.id.paymentStatusTextView);
        }

        public void bind(BookingDetail bookingDetail) {
            eventNameTextView.setText(bookingDetail.getEventName());
            venueNameTextView.setText(bookingDetail.getVenueName());
            attendeesTextView.setText(String.valueOf(bookingDetail.getAttendees()));
            totalPriceTextView.setText(String.valueOf(bookingDetail.getTotalPrice()));
            bookingDateTextView.setText(bookingDetail.getBookingDate());
            bookingTimestampTextView.setText(bookingDetail.getBookingTimestamp());
            paymentStatusTextView.setText(bookingDetail.getPaymentStatus());
        }
    }
}
