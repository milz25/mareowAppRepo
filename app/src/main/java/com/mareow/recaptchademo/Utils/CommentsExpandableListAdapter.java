package com.mareow.recaptchademo.Utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.mareow.recaptchademo.R;

import java.util.List;
import java.util.Map;

public class CommentsExpandableListAdapter extends BaseExpandableListAdapter {

    // 4 Child types
    private static final int CHILD_TYPE_1 = 0;
    private static final int CHILD_TYPE_2 = 1;
    private static final int CHILD_TYPE_3 = 2;
    private static final int CHILD_TYPE_4 = 3;

    // 3 Group types
    private static final int GROUP_TYPE_1 = 0;
    private static final int GROUP_TYPE_2 = 1;
    private static final int  GROUP_TYPE_3 = 2;
    private static final int  GROUP_TYPE_4 = 3;


    private Activity context;
    private Map<String, List<String>> comments_feed_collection;
    private List<String> group_list;

    public CommentsExpandableListAdapter(Activity context, List<String> group_list,Map<String, List<String>> comments_feed_collection) {
        this.context = context;
        this.comments_feed_collection = comments_feed_collection;
        this.group_list = group_list;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return comments_feed_collection.get(group_list.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String incoming_text = (String) getChild(groupPosition, childPosition);

        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            //first view
            if(childPosition==0 && groupPosition==0) {
                convertView = inflater.inflate(R.layout.general_details_plan_details_dialog, null);
             //   TextView description_child = (TextView) convertView.findViewById(R.id.description_of_ads_expandable_list_child_text_view);
             //   description_child.setText(incoming_text);
            }else if(childPosition==0 && groupPosition==1){
                //second view view
                convertView = inflater.inflate(R.layout.attachment_details_plan_details_dialog, null);
            }else if(childPosition>=1 && groupPosition>=1){
                //thrid view
                convertView = inflater.inflate(R.layout.operator_details_plan_details_dialog, null);
            }else if(childPosition>=1 && groupPosition>=1){
                //thrid view
                convertView = inflater.inflate(R.layout.mobility_details_plan_details_dialog, null);
            }
        }


        return convertView;
    }




    @Override
    public int getChildrenCount(int groupPosition) {
       return comments_feed_collection.get(group_list.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group_list.get(groupPosition);
    }
    @Override
    public int getGroupCount() {
        return group_list.size();
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }



    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final String incoming_text = (String) getGroup(groupPosition);

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.parent_layout_general, null);
        }
        AppCompatTextView groupText1=(AppCompatTextView) convertView.findViewById(R.id.parent_txt_general);
        groupText1.setText(incoming_text);

        return convertView;
    }


    public boolean hasStableIds() {
        return false;
    }


    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

   /* @Override
    public int getChildTypeCount() {
        return 4; // I defined 4 child types (CHILD_TYPE_1, CHILD_TYPE_2, CHILD_TYPE_3, CHILD_TYPE_UNDEFINED)
    }*/

   /* @Override
    public int getGroupTypeCount() {
        return 4; // I defined 3 groups types (GROUP_TYPE_1, GROUP_TYPE_2, GROUP_TYPE_3)
    }*/


   /* @Override
    public int getGroupType(int groupPosition) {
        switch (groupPosition) {
            case 0:
                return GROUP_TYPE_1;
            case 1:
                return GROUP_TYPE_2;
            case 2:
                return GROUP_TYPE_3;
            case 3:
                return GROUP_TYPE_4;
                default:
                return GROUP_TYPE_4;
        }
    }*/

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        switch (groupPosition) {
            case 0:
                switch (childPosition) {
                    case 0:
                        return CHILD_TYPE_1;
                    case 1:
                        return CHILD_TYPE_2;
                    case 2:
                        return CHILD_TYPE_3;
                    case 3:
                        return CHILD_TYPE_4;

                }
                break;
            case 1:
                switch (childPosition) {
                    case 0:
                        return CHILD_TYPE_1;
                    case 1:
                        return CHILD_TYPE_2;
                    case 2:
                        return CHILD_TYPE_3;
                    case 3:
                        return CHILD_TYPE_4;
                }
                break;
            case 2:
                switch (childPosition) {
                    case 0:
                        return CHILD_TYPE_1;
                    case 1:
                        return CHILD_TYPE_2;
                    case 2:
                        return CHILD_TYPE_3;
                    case 3:
                        return CHILD_TYPE_4;
                }
                break;
            case 3:
                switch (childPosition) {
                    case 0:
                        return CHILD_TYPE_1;
                    case 1:
                        return CHILD_TYPE_2;
                    case 2:
                        return CHILD_TYPE_3;
                    case 3:
                        return CHILD_TYPE_4;
                }
                break;

        }

        return CHILD_TYPE_4;
    }

   /* @Override
    public int getChildType(int groupPosition, int childPosition) {

        switch (groupPosition) {
            case 0:
                return CHILD_TYPE_1;
            case 1:
                return CHILD_TYPE_2;
            case 2:
                return CHILD_TYPE_3;
            case 3:
                return CHILD_TYPE_4;

        }

        return super.getChildType(groupPosition, childPosition);
    }*/
}
