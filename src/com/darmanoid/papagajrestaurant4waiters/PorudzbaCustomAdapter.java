package com.darmanoid.papagajrestaurant4waiters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class PorudzbaCustomAdapter extends BaseAdapter   implements OnClickListener {
    
   private Activity activity;
   private ArrayList data;
   private static LayoutInflater inflater=null;
   public Resources res;
   PorudzbaListModel tempValues=null;
   int i=0;
   //konstruktor
   public PorudzbaCustomAdapter(Activity a, ArrayList d,Resources resLocal) {
           activity=a;
           data=d;
           res=resLocal;
           inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
   }

   //Velicina predate array liste
   public int getCount() {
        
       if(data.size()<=0)
           return 1;
       return data.size();
   }

   public Object getItem(int position) {
       return position;
   }

   public long getItemId(int position) {
       return position;
   }
    
   //Povezivanje sa XML-om
   public static class ViewHolder{
        
       public TextView kolicina;
       public TextView naziv;
       public TextView cijena;

   }

   public View getView(int position, View convertView, ViewGroup parent) {
        
       View vi = convertView;
       ViewHolder holder;
        
       if(convertView==null){

           vi = inflater.inflate(R.layout.porudzba_elem, null);
            
           holder = new ViewHolder();
           holder.kolicina = (TextView) vi.findViewById(R.id.textKolicina);
           holder.naziv=(TextView)vi.findViewById(R.id.textNaziv);
           holder.cijena=(TextView)vi.findViewById(R.id.textCijena);

           vi.setTag( holder );
       }
       else 
           holder=(ViewHolder)vi.getTag();
        
       if(data.size()<=0)
       {
           holder.naziv.setText("Lista je prazna");
            
       }
       else
       {
           //dohvata svaki element iz array liste
           tempValues=null;
           tempValues = ( PorudzbaListModel ) data.get( position );
            
           
            holder.naziv.setText(tempValues.getNaziv());
            holder.cijena.setText(tempValues.getCijena());
            holder.kolicina.setText(tempValues.getKolicina());
            vi.setOnClickListener(new OnItemClickListener(position));
       }
       return vi;
   }
    
   @Override
   public void onClick(View v) {
           //Log.v("CustomAdapter", "Klik na stavku");
           //Ovo mijenjati kasnije, tj da predje na activity, a mogu i u Activity iz kojeg zovem
   }
    
   private class OnItemClickListener  implements OnClickListener{           
       private int mPosition;
       OnItemClickListener(int position){
            mPosition = position;
       }
        
   @Override
   public void onClick(View arg0) {
      	 
  	 PorudzbaActivity sct = (PorudzbaActivity)activity;
       sct.onItemClick(mPosition);
   }               
}   
}
