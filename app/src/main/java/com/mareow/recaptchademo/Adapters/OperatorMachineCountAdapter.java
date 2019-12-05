package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.OperatorMachineCount;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.ViewHolders.OperatorMachineCountViewHolder;

import java.util.List;

public class OperatorMachineCountAdapter extends RecyclerView.Adapter<OperatorMachineCountViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    Context context;
    List<OperatorMachineCount> operatorMachineCountList;
    //List<String> dataList;
    public OperatorMachineCountAdapter(Context context, List<OperatorMachineCount> operatorMachineCountList) {
        this.context=context;
        //this.operatorMachineCountList=operatorMachineCountList;
        this.operatorMachineCountList=operatorMachineCountList;


    }

    @NonNull
    @Override
    public OperatorMachineCountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());

      /*  if (viewType == TYPE_HEADER) {
            View v = inflater.inflate(R.layout.dash_list_header,parent,false);
            return  new VHHeader(v);
        } else {
            View v = inflater.inflate(R.layout.dash_board_adapter, parent, false);
            return new VHItem(v);
        }*/

        View itemView=inflater.inflate(R.layout.dash_board_adapter,parent,false);
        return new OperatorMachineCountViewHolder(itemView);
    }

   /* @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        *//*if (holder instanceof VHHeader) {
            // VHHeader VHheader = (VHHeader)holder;
            Header currentItem = (Header) dataList.get(position);
            VHHeader VHheader = (VHHeader)holder;
            VHheader.txtTitle.setText(currentItem.getHeader());
        } else if (holder instanceof VHItem){
        DashContentItem dashContentItem=(DashContentItem)dataList.get(position);
        VHItem VHitem = (VHItem)holder;
        VHitem.txtName.setText(dashContentItem.getName());
         }*//*


    }*/

    @Override
    public void onBindViewHolder(@NonNull OperatorMachineCountViewHolder holder, int position) {

        String count=String.valueOf(operatorMachineCountList.get(position).getCount());
        SpannableString ss1=  new SpannableString(count+" "+operatorMachineCountList.get(position).getMachineFullName());
        ss1.setSpan(new RelativeSizeSpan(2f), 0,count.length(), 0); // set size
        ss1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.theme_orange)), 0, count.length(), 0);// set color
        holder.txtMachineName.setText(ss1);
       // holder.txtMachineName.setText(operatorMachineCountList.get(position).getCount()+" "+operatorMachineCountList.get(position).getMachineFullName());
        //holder.txtMachineName.setText(operatorMachineCountList.get(position));
    }

   /* @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return dataList.get(position) instanceof Header;
    }
*/
    @Override
    public int getItemCount() {
        return operatorMachineCountList.size();
    }


   /* class VHHeader extends RecyclerView.ViewHolder{
        AppCompatTextView txtTitle;
        public VHHeader(View itemView) {
            super(itemView);
            this.txtTitle = (AppCompatTextView) itemView.findViewById(R.id.dash_list_item_header);
        }
    }
    class VHItem extends RecyclerView.ViewHolder{
        AppCompatTextView txtName;
        public VHItem(View itemView) {
            super(itemView);
            this.txtName = (AppCompatTextView) itemView.findViewById(R.id.dashboard_adapter_machine_name);
        }
    }*/
}
