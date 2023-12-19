package com.example.mariaproj.Class;




        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;

        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.recyclerview.widget.RecyclerView;

        import com.example.mariaproj.R;
        import com.example.mariaproj.User.ProductInfo;
        import com.example.mariaproj.User.Volunteer_info;


        import java.util.List;
        //fill the recycle view with volunteer(place) data

public class VolunteerPlacesAdapter extends RecyclerView.Adapter<VolunteerPlacesAdapter.ViewHolder> {

    List<Volunteer> volunteerList;
    Context context;

    public VolunteerPlacesAdapter(Context context, List<Volunteer> volunteerList) {
        this.context = context;
        this.volunteerList = volunteerList;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.each_veiw, parent, false);
        return new ViewHolder(view);

    }
    // fill thevolunteer(place) data in the recycle items

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {

        // here we will find the position and start setting the output on our views

        String nameofVolunteer= volunteerList.get(position).getPlace();
        double participantsNeeded= volunteerList.get(position).getRequiredNumOfVolunteers()-
                volunteerList.get(position).getNumOfRegisteredVolunteers();
        byte[] images = volunteerList.get(position).getImageByte();
        Bitmap bm = BitmapFactory.decodeByteArray(images, 0 ,images.length);

        holder.nameOfVolunteer.setText(nameofVolunteer);
        holder.ParticipantsNeeded.setText("Places Volunteer Left : "+participantsNeeded);
        holder. imageOfVolunteer.setImageBitmap(bm);

    }

    @Override
    public int getItemCount() {
        return volunteerList.size();
    }

    // in order to make our views responsive we can implement onclicklistener on our recyclerview
    //get the recycle
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // here we will find the views on which we will inflate our data

        TextView nameOfVolunteer, ParticipantsNeeded ;
        ImageView imageOfVolunteer;

        public ViewHolder(View itemView) {
            super(itemView);

            nameOfVolunteer = itemView.findViewById(R.id.place_name);
            ParticipantsNeeded = itemView.findViewById(R.id.requiredNumOfVolunteers);
            imageOfVolunteer = itemView.findViewById(R.id.place_image);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ProductInfo.class);
            intent.putExtra("id",volunteerList.get(getLayoutPosition()).getPid()+"");
            v.getContext().startActivity(intent);
        }
    }
}