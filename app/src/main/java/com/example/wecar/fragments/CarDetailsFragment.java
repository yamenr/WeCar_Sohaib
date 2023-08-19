package com.example.wecar.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wecar.R;
import com.example.wecar.data.Car;
import com.example.wecar.data.FirebaseServices;
import com.example.wecar.utilities.Utils;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarDetailsFragment extends Fragment {
    private static final int PERMISSION_SEND_SMS = 1;
    private static final int REQUEST_CALL_PERMISSION = 2;
    private FirebaseServices fbs;
    private TextView tvnameCar,tvhorse_power,tvOwners,tvColor,tvCar_num,
            tvManufacturer,tvYear,tvCar_model,tvTest,tvkilometre,
            tvEngine_capacity,tvGear_shifting_model,tvPrice, tvPhone;
    private ImageView ivCarPhoto;
    private Car myCar;
    private Button sendSMSButton, btnWhatsapp, btnCall;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarDetailsFragment newInstance(String param1, String param2) {
        CarDetailsFragment fragment = new CarDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_details, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    public void init()
    {
/*Car(String nameCar, String horse_power, String owners, String phone, String color,
               String car_num, String manufacturer, String year, String car_model, String test,
               String kilometre, String engine_capacity, String gear_shifting_model, String price, String photo)
* */

        fbs= FirebaseServices.getInstance();
        tvnameCar=getView().findViewById(R.id.tvNameCarDetailsFragment);
        tvhorse_power=getView().findViewById(R.id.tvHorsePowerDetailsFragment);
        tvOwners=getView().findViewById(R.id.tvOwnersDetailsFragment);
        tvPhone=getView().findViewById(R.id.tvPhoneDetailsFragment);
        tvCar_num=getView().findViewById(R.id.tvCarNumberDetailsFragment);
        tvManufacturer=getView().findViewById(R.id.tvManufacturerDetailsFragment);
        tvYear = getView().findViewById(R.id.tvYearDetailsFragment);
        tvColor = getView().findViewById(R.id.tvColorDetailsFragment);
        tvCar_model=getView().findViewById(R.id.tvCarModelDetailsFragment);
        tvTest=getView().findViewById(R.id.tvTestDetailsFragment);
        tvkilometre=getView().findViewById(R.id.tvKilometreDetailsFragment);
        tvEngine_capacity=getView().findViewById(R.id.tvEngineCapacityDetailsFragment);
        tvGear_shifting_model=getView().findViewById(R.id.tvGearShiftDetailsFragment);
        tvPrice=getView().findViewById(R.id.tvPriceDetailsFragment);
        ivCarPhoto = getView().findViewById(R.id.ivCarDetailsFragment);

        Bundle args = getArguments();
        if (args != null) {
            myCar = args.getParcelable("car");
            if (myCar != null) {
                //String data = myObject.getData();
                // Now you can use 'data' as needed in FragmentB
                tvnameCar.setText(myCar.getNameCar());
                tvhorse_power.setText(myCar.getHorse_power());
                tvOwners.setText(myCar.getOwners());
                tvPhone.setText(myCar.getPhone());
                tvCar_num.setText(myCar.getCar_num());
                tvManufacturer.setText(myCar.getManufacturer());
                tvYear.setText(myCar.getYear());
                tvColor.setText(myCar.getColor());
                tvCar_model.setText(myCar.getCar_model());
                tvTest.setText(myCar.getTest());
                tvkilometre.setText(myCar.getKilometre());
                tvEngine_capacity.setText(myCar.getEngine_capacity());
                tvGear_shifting_model.setText(myCar.getGear_shifting_model());
                tvPrice.setText(myCar.getPrice());
                if (myCar.getPhoto() == null || myCar.getPhoto().isEmpty())
                {
                    Picasso.get().load(R.drawable.ic_fav).into(ivCarPhoto);
                }
                else {
                    Picasso.get().load(myCar.getPhoto()).into(ivCarPhoto);
                }
            }
        }
        sendSMSButton = getView().findViewById(R.id.btnSMS);
        sendSMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSendSMS();            }
        });

        btnWhatsapp = getView().findViewById(R.id.btnWhatsApp);
        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendWhatsAppMessage(v);
            }
        });

        btnCall = getView().findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
    }

    private void checkAndSendSMS() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.SEND_SMS}, PERMISSION_SEND_SMS);
        } else {
            sendSMS();
        }
    }

    private void sendSMS() {
        String phoneNumber = myCar.getPhone();
        String message = "Interested in your car: " + myCar.getCar_num();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getActivity(), "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "SMS sending failed.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSMS();
            } else {
                Toast.makeText(requireContext(), "Permission denied. Cannot send SMS.", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCall();
            }
        }
    }

    public void sendWhatsAppMessage(View view) {
        String phoneNumber = myCar.getPhone(); // Replace with the recipient's phone number
        String message = "Hello, this is a WhatsApp message!"; // Replace with your message

        // Create an intent with the ACTION_SENDTO action and the WhatsApp URI
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + phoneNumber));
        intent.putExtra("sms_body", message);

        // Verify if WhatsApp is installed
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // WhatsApp is not installed
            // You can handle this case as per your app's requirement
        }
    }

    private void makePhoneCall() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        } else {
            startCall();
        }
    }

    private void startCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(myCar.getPhone()));

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent);
        }
    }

}