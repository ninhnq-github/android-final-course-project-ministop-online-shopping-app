package hcmute.edu.vn.mssv18110332.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.edu.vn.mssv18110332.DAO.AddressDAO;
import hcmute.edu.vn.mssv18110332.DAO.UserAccountDAO;
import hcmute.edu.vn.mssv18110332.R;
import hcmute.edu.vn.mssv18110332.adapter.address.AddressAdapter;
import hcmute.edu.vn.mssv18110332.databinding.ActivityAddressBookBinding;
import hcmute.edu.vn.mssv18110332.databinding.ActivityChangePasswordBinding;
import hcmute.edu.vn.mssv18110332.helper.AppUtils;
import hcmute.edu.vn.mssv18110332.helper.DataValidate;
import hcmute.edu.vn.mssv18110332.helper.FireBaseUtils;
import hcmute.edu.vn.mssv18110332.helper.ProgressDialog;
import hcmute.edu.vn.mssv18110332.model.Address;
import hcmute.edu.vn.mssv18110332.model.Useraccount;


public class ChangePasswordActivity extends AppCompatActivity {

    ActivityChangePasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnChangePasswordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CHANG PASSWORD","button click");

                String old_pass = binding.txtPasswordChange.getText().toString();
                String new_pass = binding.txtNewPasswordChange.getText().toString();
                String con_pass = binding.txtNewPasswordConfirmChange.getText().toString();

                if (old_pass.isEmpty() || new_pass.isEmpty() || con_pass.isEmpty())
                {
                    Toast.makeText(getContext(),"Password kh??ng ???????c ????? tr???ng",Toast.LENGTH_SHORT).show();
                    Log.d("CHANG PASSWORD","Password kh??ng ???????c ????? tr???ng");
                    return;
                }

                Useraccount user = AppUtils.getCurrentUser();

                if (!old_pass.equals(user.getPass()))
                {
                    Toast.makeText(getContext(),"Password c???a b???n nh???p kh??ng ????ng",Toast.LENGTH_SHORT).show();
                    Log.d("CHANG PASSWORD","Password c???a b???n nh???p kh??ng ????ng");
                    return;
                }

                if (!new_pass.equals(con_pass))
                {
                    Toast.makeText(getContext(),"Password m???i b???n nh???p kh??ng kh???p nhau",Toast.LENGTH_SHORT).show();
                    Log.d("CHANG PASSWORD","Password m???i b???n nh???p kh??ng kh???p nhau");
                    return;
                }
                if (!DataValidate.validatePassword(new_pass).equals("OK"))
                {
                    Toast.makeText(getContext(),"Password ph???i c?? ??t nh???t 6 k?? t??? g???m S??? v?? CH??? C??I",Toast.LENGTH_SHORT).show();
                    Log.d("CHANG PASSWORD","Password ph???i c?? ??t nh???t 6 k?? t??? g???m S??? v?? CH??? C??I");
                    return;
                }
                ProgressDialog pd = new ProgressDialog();
                pd.show_progress_dialog(getContext());
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FireBaseUtils.change_password(getContext(),auth.getCurrentUser(),new_pass);
                Toast.makeText(ChangePasswordActivity.this, "M???t kh???u c???a b???n ???? ???????c thay ?????i, ????ng nh???p l???i nh??!", Toast.LENGTH_SHORT).show();
                auth.signOut();
                Intent i = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                startActivity(i);
                pd.hide_progress_dialog();
                finish();
            }
        });
    }

    Context getContext()
    {return ChangePasswordActivity.this;}
}