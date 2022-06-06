package com.example.advancedprojectandroidclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.entities.Contact;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    class ContactViewHolder extends RecyclerView.ViewHolder {

        private final TextView nicknameTv;
        private final TextView descriptionTv;
        private final TextView lastDateTv;
        private final ImageView imgIv;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nicknameTv = itemView.findViewById(R.id.contact_nickname_tv);
            this.descriptionTv = itemView.findViewById(R.id.contact_description_tv);
            this.lastDateTv = itemView.findViewById(R.id.contact_last_seen_tv);
            this.imgIv = itemView.findViewById(R.id.contact_img_iv);
        }
    }

    private final LayoutInflater layoutInflater;
    private List<Contact> contacts;

    public ContactListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContactListAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ContactViewHolder holder, int position) {
        if (contacts != null){
            final Contact current = contacts.get(position);
            holder.lastDateTv.setText(current.getLastdate());
            holder.nicknameTv.setText(current.getName());
            holder.descriptionTv.setText(current.getLast());
            holder.imgIv.setImageResource(R.drawable.ic_person_circle);
        }
    }

    @Override
    public int getItemCount() {
        if (contacts != null){
            return contacts.size();
        }
        return 0;
    }

    public void setContacts(List<Contact> contacts){
        this.contacts = contacts;
        notifyDataSetChanged();
    }
}
