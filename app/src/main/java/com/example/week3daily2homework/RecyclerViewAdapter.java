package com.example.week3daily2homework;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    ArrayList<Hero> heroArrayList;

    public RecyclerViewAdapter(ArrayList<Hero> heroArrayList) {
        this.heroArrayList = heroArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        Hero hero = heroArrayList.get(position);

        if(hero != null){
            String name = hero.getName();
            String power = hero.getPower();
            String team = hero.getTeam();
            int image = hero.getImage();

            viewHolder.setItemHero(hero);
            viewHolder.tvName.setText(name);
            viewHolder.tvPower.setText(power);
            viewHolder.tvTeam.setText(team);
            viewHolder.imgImage.setImageResource(image);
        }
    }

    public void addHero(Hero hero){
        heroArrayList.add(hero);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return heroArrayList != null ? heroArrayList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgImage;
        TextView tvName;
        TextView tvPower;
        TextView tvTeam;
        Hero itemHero;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            imgImage = itemView.findViewById(R.id.imgViewImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvPower = itemView.findViewById(R.id.tvPower);
            tvTeam = itemView.findViewById(R.id.tvTeam);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), itemHero.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setItemHero(Hero itemHero){
            this.itemHero = itemHero;
        }
    }
}
