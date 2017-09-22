package cl.wower.postfire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity{
    private ImageButton mSelectImage;
    private static final int Gallery_Request =1;
    private EditText mPostTitle;
    private EditText mPostDesc;
    Uri imgUri;
    private StorageReference mStorageRef;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        load();

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_Request);

            }
        });

    }

    private void load() {
        mProgress=new ProgressDialog(this);
        mSelectImage= (ImageButton) findViewById(R.id.imgSelect);
        mPostDesc= (EditText) findViewById(R.id.DescField);
        mPostTitle = (EditText) findViewById(R.id.titleField);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        mStorageRef= FirebaseStorage.getInstance().getReference();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gallery_Request) {
            imgUri = data.getData();
            mSelectImage.setImageURI(imgUri);

        }

    }

    public void post(View view) {

        mProgress.setMessage("Compartiendo...");
        mProgress.show();
        final String title_val = mPostTitle.getText().toString().trim();
        final String desc_val = mPostDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(title_val)) {
            StorageReference filepath = mStorageRef.child("Blog_images").child(imgUri.getLastPathSegment());


            filepath.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri dowloadUri = taskSnapshot.getDownloadUrl();
                    DatabaseReference newPost = mDatabase.push();

                    newPost.child("title").setValue(title_val);
                    newPost.child("desc").setValue(desc_val);
                    newPost.child("image").setValue(dowloadUri.toString());

                    mProgress.dismiss();
                    startActivity(new Intent(PostActivity.this,MainActivity.class));
                }
            });
        }
    }


}
