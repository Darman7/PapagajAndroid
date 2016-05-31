package com.darmanoid.papagajrestaurant4waiters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
  
public class ArtikliCustomAdapter extends BaseAdapter   implements OnClickListener {
          
         private Activity activity;
         private ArrayList data;
         private static LayoutInflater inflater=null;
         public Resources res;
         ArtikliListModel tempValues=null;
         int i=0;
          
         //konstruktor
         public ArtikliCustomAdapter(Activity a, ArrayList d,Resources resLocal) {
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
              
             public TextView naziv;
             public TextView cijena;
             public TextView textWide;
             public ImageView image;
      
         }

         public View getView(int position, View convertView, ViewGroup parent) {
              
             View vi = convertView;
             ViewHolder holder;
              
             if(convertView==null){

                 vi = inflater.inflate(R.layout.artikal, null);
                  
                 holder = new ViewHolder();
                 holder.naziv = (TextView) vi.findViewById(R.id.textNaziv);
                 holder.cijena=(TextView)vi.findViewById(R.id.textCijena);
                 //holder.image=(ImageView)vi.findViewById(R.id.image);

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
                 tempValues = ( ArtikliListModel ) data.get( position );
                  
                 
                  holder.naziv.setText(tempValues.getNaziv());
                  holder.cijena.setText(tempValues.getCijena());
                  /* holder.image.setImageResource(
                               res.getIdentifier(
                               "com.androidexample.customlistview:drawable/"+tempValues.getImage()
                               ,null,null));
                   */
                  
                  vi.setOnClickListener(new OnItemClickListener(position));
             }
             return vi;
         }
          
         @Override
         public void onClick(View v) {
                 Log.v("CustomAdapter", "Klik na stavku");
                 //Ovo mijenjati kasnije, tj da predje na activity, a mogu i u Activity iz kojeg zovem
         }
          
         private class OnItemClickListener  implements OnClickListener{           
             private int mPosition;
             OnItemClickListener(int position){
                  mPosition = position;
             }
              
         @Override
         public void onClick(View arg0) {
            	 
        	 MenuArtikliActivity sct = (MenuArtikliActivity)activity;
             sct.onItemClick(mPosition);
         }               
     }   
}
