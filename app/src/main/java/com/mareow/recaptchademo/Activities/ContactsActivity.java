package com.mareow.recaptchademo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.mareow.recaptchademo.Adapters.CustomContactAdapter;
import com.mareow.recaptchademo.DataModels.ContactModel;
import com.mareow.recaptchademo.R;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    List<ContactModel> contactModelList=new ArrayList<>();
    RecyclerView contactRecycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);


        contactModelList=getContacts(this);
        contactRecycle=(RecyclerView)findViewById(R.id.contact_recycleview);
        contactRecycle.setHasFixedSize(false);
        contactRecycle.setItemAnimator(new DefaultItemAnimator());
        contactRecycle.setLayoutManager(new LinearLayoutManager(this));

        CustomContactAdapter contactAdapter=new CustomContactAdapter(this,contactModelList);
        contactRecycle.setAdapter(contactAdapter);

    }



    public List<ContactModel> getContacts(Context ctx) {
        List<ContactModel> list = new ArrayList<>();

        final String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"+ ("1") + "'";
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME+ " ASC";

        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, projection, null, null, sortOrder);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
              //  String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                //if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    //Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                          //  ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    //InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(ctx.getContentResolver(),
                         //   ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                   // Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id));
                    //Uri pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                   // Bitmap photo = null;
                   // if (inputStream != null) {
                    //    photo = BitmapFactory.decodeStream(inputStream);
                    //}
                    while (cursor.moveToNext()) {
                        ContactModel info = new ContactModel();
                       // info.id = id;
                        info.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        info.mobileNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        info.email=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                       // info.photo = photo;
                        //info.photoURI= pURI;
                        list.add(info);
                    }

                    cursor.close();
                }
            //}
            cursor.close();
        }
        return list;
    }
}
