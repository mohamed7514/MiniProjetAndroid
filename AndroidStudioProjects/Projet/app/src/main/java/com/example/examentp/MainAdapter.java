package com.example.examentp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

 public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {


     /**
      * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
      * {@link FirebaseRecyclerOptions} for configuration options.
      *
      * @param options
      */
     public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
         super(options);
     }

     @Override
     protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.Nom.setText(model.getNom());
         holder.Cinema.setText(model.getCinema());
         holder.Date.setText(model.getDate());


         Glide.with(holder.img.getContext())
                 .load(model.getImage())
                 .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                 .circleCrop()
                 .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                 .into(holder.img);


         holder.btnEdit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                         .setContentHolder(new ViewHolder(R.layout.update_popup))
                         .setExpanded(true,1200)
                         .create();
                 //dialogPlus.show();
                 View view = dialogPlus.getHolderView();
                 EditText Nom= view.findViewById(R.id.txtName);
                 EditText cinema= view.findViewById(R.id.cinemaName);
                 EditText date= view.findViewById(R.id.txtdate);
                 EditText img = view.findViewById(R.id.imageurl);

                 Button btnUpdate = view.findViewById(R.id.btnupdate);

                 Nom.setText(model.getNom());
                 cinema.setText(model.getCinema());
                 date.setText(model.getDate());
                 img.setText(model.getImage());

                 dialogPlus.show();

                 btnUpdate.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Map<String,Object> map = new HashMap<>();
                         map.put("Nom",Nom.getText().toString());
                         map.put("Cinema",cinema.getText().toString());
                         map.put("Date",date.getText().toString());
                         map.put("Image",img.getText().toString());

                         FirebaseDatabase.getInstance().getReference().child("Films")
                                 .child(getRef(position).getKey()).updateChildren(map)
                                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void unused) {
                                         Toast.makeText(holder.Nom.getContext() ,"Data Updated Succesfully", Toast.LENGTH_SHORT).show();
                                         dialogPlus.dismiss();
                                     }
                                 })
                                 .addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(@NonNull Exception e) {
                                         Toast.makeText(holder.Nom.getContext() ,"Error while updating ", Toast.LENGTH_SHORT).show();
                                         dialogPlus.dismiss();
                                     }
                                 });
                     }
                 });

             }
         });
         holder.BtnDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AlertDialog.Builder builder= new AlertDialog.Builder(holder.Nom.getContext());
                 builder.setTitle("Are you Sure ?");
                 builder.setMessage("Delted data can't be Undo .");
                 builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         FirebaseDatabase.getInstance().getReference().child("Films")
                                 .child(getRef(position).getKey()).removeValue();

                     }
                 });
                 builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         Toast.makeText(holder.Nom.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();

                     }
                 });
                 builder.show();
             }
         });
     }

     @NonNull
     @Override
     public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
         return new myViewHolder(view);
     }

     class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img ;
        TextView Nom,Cinema,Date;

        Button btnEdit,BtnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img=(CircleImageView)itemView.findViewById(R.id.img1);
            Nom = (TextView) itemView.findViewById(R.id.nametext);
            Cinema=(TextView)itemView.findViewById(R.id.cinematext);
            Date = (TextView) itemView.findViewById(R.id.datetext);

            btnEdit=(Button) itemView.findViewById(R.id.btnEdit);
            BtnDelete= (Button) itemView.findViewById(R.id.btnDelete);
        }
    }
}
