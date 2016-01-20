package com.project.farmer.famerapplication.contact.activity;

import com.baseandroid.BaseActivity;
import com.project.farmer.famerapplication.R;

public class ContactListAcitivity extends BaseActivity{
    @Override
    protected void initViews() {

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
