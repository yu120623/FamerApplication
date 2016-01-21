package com.project.farmer.famerapplication.contact.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.Contact;
import com.project.farmer.famerapplication.entity.TransferObject;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;

import java.util.ArrayList;
import java.util.List;

public class ContactListAcitivity extends BaseActivity{
    private RecyclerView contactListView;
    private ContactAdapter adapter;
    private List<Contact> contactList = new ArrayList<>();
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
        adapter = new ContactAdapter();
        contactListView.setLayoutManager(new LinearLayoutManager(context));
        contactListView.setAdapter(adapter);
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.CONTACT_LIST;
        TransferObject data = new TransferObject();
        data.setUserAliasId("aaa");
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                if(data.getContacts() != null && data.getContacts().size() > 0){
                    contactList = data.getContacts();
                    adapter.notifyDataSetChanged();
                }
            }
        },data);
        request.execute();
    }

    private void findViews() {
        contactListView = (RecyclerView) this.findViewById(R.id.contact_list);
    }

    class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder>{
        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_contact,null,false);
            view.setOnClickListener(onContactClick);
            return new ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactViewHolder holder, int position) {
            Contact contact = contactList.get(position);
            holder.phone.setText(contact.getConnectPhone());
            holder.name.setText(contact.getConnectName());
            holder.itemView.setTag(contact);
        }

        @Override
        public int getItemCount() {
            return contactList.size();
        }
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView phone;
        public ContactViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.contact_name);
            phone = (TextView) itemView.findViewById(R.id.contact_phone);
        }
    }

    private View.OnClickListener onContactClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Contact contact = (Contact) view.getTag();
            Intent intent = new Intent();
            intent.putExtra("contact",contact);
            setResult(RESULT_OK,intent);
            finish();
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_choose_contact_list;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.choose);
    }
}
