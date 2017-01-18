package jhon.tabares.com.grabilitytest.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jhon.tabares.com.grabilitytest.ListApps;
import jhon.tabares.com.grabilitytest.Models.Categorys;
import jhon.tabares.com.grabilitytest.R;

/**
 * Created by jhon on 14/01/2017.
 */

public class ListCategoryAdapter extends ArrayAdapter<Categorys> {

    private Activity activity;
    private List<Categorys> categoryData;

    public ListCategoryAdapter(Activity context, int resource, List<Categorys> objects){
        super(context, resource, objects);

        this.activity = context;
        this.categoryData = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderEx viewHolderEx;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_abslistview, parent, false);

            viewHolderEx = new ViewHolderEx(convertView);

            convertView.setTag(viewHolderEx);

        } else {
            viewHolderEx = (ViewHolderEx) convertView.getTag();
        }

        viewHolderEx.txtCategory.setText(getItem(position).getCategorys());

        convertView.setOnClickListener(onClickListener(getItem(position), String.valueOf(position + 1)));

        return convertView;

    }

    private View.OnClickListener onClickListener(final Categorys categorys, final String pos) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.layout_dialog);
                dialog.setTitle("Selected Category");

                TextView name = (TextView) dialog.findViewById(R.id.name);
                final TextView position = (TextView) dialog.findViewById(R.id.pos);

                name.setText(categorys.getCategorys());
                
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(activity, ListApps.class);
                        activity.startActivity(intent);
                        position.setText(pos);
                    }
                });

                dialog.show();
            }
        };
    }

    private static class ViewHolderEx {
        private TextView txtCategory;

        public ViewHolderEx(View v) {

            txtCategory = (TextView) v.findViewById(R.id.txtCategory);
        }
    }

}
