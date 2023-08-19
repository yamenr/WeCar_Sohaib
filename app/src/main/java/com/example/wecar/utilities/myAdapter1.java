package com.example.wecar.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wecar.R;
import com.example.wecar.data.Car;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class myAdapter1 extends RecyclerView.Adapter<myAdapter1.MyViewHolder> {


    Context context;
    ArrayList<Car>carsList;
    private OnItemClickListener itemClickListener;

    public myAdapter1(Context context, ArrayList<Car> carsList) {
        this.context = context;
        this.carsList = carsList;
    }

    @NonNull
    @Override
    public  MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
       View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
       return  new MyViewHolder(v);
    }

   @Override
   public void onBindViewHolder(@NonNull MyViewHolder holder,int position){
        Car car= carsList.get(position);

        holder.carName.setText(car.getNameCar());
        holder.Price.setText(car.getPrice());
        holder.Year.setText(car.getYear());
        holder.location.setText("Nazareth");
        holder.GearShift.setText(car.getGear_shifting_model());
        holder.kilometre.setText(car.getKilometre());
        holder.carName.setOnClickListener(v -> {
           if (itemClickListener != null) {
               itemClickListener.onItemClick(position);
           }
        });
        if (car.getPhoto() == null || car.getPhoto().isEmpty())
        {
            Picasso.get().load(R.drawable.ic_fav).into(holder.ivCar);
        }
       else {
            Picasso.get().load(car.getPhoto()).into(holder.ivCar);
        }
   }
    @Override
    public int getItemCount(){
        return carsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView  carName,Price,Year,location,GearShift,kilometre;
        ImageView ivCar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            carName=itemView.findViewById(R.id.tvNameCar_carListFragment);
            Price=itemView.findViewById(R.id.tvPrice_carListFragment);
            Year=itemView.findViewById(R.id.tvYear_carListFragment);
            location=itemView.findViewById(R.id.tvlocation_carListFragment);
            GearShift=itemView.findViewById(R.id.tvGearShift_carListFragment);
            kilometre=itemView.findViewById(R.id.tvKelometer_carListFragment);
            ivCar = itemView.findViewById(R.id.ivCarPhotoItem);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
