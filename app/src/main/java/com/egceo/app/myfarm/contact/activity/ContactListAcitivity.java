package com.egceo.app.myfarm.contact.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.Contact;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class ContactListAcitivity extends BaseActivity {
    private RecyclerView contactListView;
    private Button okBtn;
    private EditText contactName;
    private EditText contactPhone;
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
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.hideKeyBoard(activity);
                if ("".equals(contactName.getText().toString().trim()) || "".equals(contactPhone.getText().toString().trim())) {
                    Toast.makeText(ContactListAcitivity.this, "联系人或手机号输入为空", Toast.LENGTH_SHORT).show();
                } else {
                    addContact();
                }
            }
        });

    }

    private void addContact() {
        String url = API.URL + API.API_URL.ADD_CONTACT;
        TransferObject data = AppUtil.getHttpData(context);
        data.setName(contactName.getText().toString());
        data.setPhoneNumber(contactPhone.getText().toString());
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                Contact contact = data.getContact();
                contactList.add(contactList.size(), contact);
                adapter.notifyDataSetChanged();
            }
        }, data);
        request.execute();

    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.CONTACT_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                if (data.getContacts() != null && data.getContacts().size() > 0) {
                    contactList = data.getContacts();
                    adapter.notifyDataSetChanged();
                }
            }
        }, data);
        request.execute();
    }

    private void findViews() {
        okBtn = (Button) this.findViewById(R.id.ok);
        contactName = (EditText) this.findViewById(R.id.name);
        contactPhone = (EditText) this.findViewById(R.id.phone);
        contactListView = (RecyclerView) this.findViewById(R.id.contact_list);
    }

    class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {
        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_contact, null, false);
            view.setOnClickListener(onContactClick);
            view.setOnLongClickListener(onContactLongClick);
            return new ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactViewHolder holder, int position) {
            Contact contact = contactList.get(position);
            holder.itemView.setTag(contact);
            holder.phone.setText(contact.getConnectPhone());
            holder.name.setText(contact.getConnectName());
            holder.itemView.setTag(contact);
        }

        @Override
        public int getItemCount() {
            return contactList.size();
        }
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
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
            intent.putExtra("contact", contact);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    private View.OnLongClickListener onContactLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            final Contact contact = (Contact) v.getTag();
            Dialog alertDialog = new AlertDialog.Builder(ContactListAcitivity.this)
                    .setItems(new String[]{getString(R.string.contact_delete)}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteContact(contact);
                        }
                    })
                    .create();
            alertDialog.show();
            return false;
        }
    };

    private void deleteContact(final Contact contact) {
        String url = API.URL + API.API_URL.DELETE_CONTACT;
        TransferObject data = AppUtil.getHttpData(context);
        data.setContactId(contact.getContactId().toString());
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                if(data.getMessage().getStatus().equals("00000")) {
                    contactList.remove(contact);
                    adapter.notifyDataSetChanged();
                }
            }
        }, data);
        request.execute();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_choose_contact_list;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.choose);
    }

}
