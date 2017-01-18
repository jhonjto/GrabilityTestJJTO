package jhon.tabares.com.grabilitytest.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jhon.tabares.com.grabilitytest.Models.Apps;
import jhon.tabares.com.grabilitytest.R;

import static android.R.attr.country;

/**
 * Created by jhon on 16/01/2017.
 */

public class AppCategoryAdapter extends ArrayAdapter<Apps> {

    private Activity activity;
    private List<Apps> appData;

    public AppCategoryAdapter(Activity context, int resource, List<Apps> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.appData = objects;
    }

    @Override
    public Apps getItem(int position){

        return appData.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_ablistview_smartphone, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        Apps item = getItem(position);

        Picasso.with(activity).load(item.getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).
        into(holder.image);
        //holder.image.setImageResource(Integer.parseInt(tempImage));
        holder.name.setText(getItem(position).getName());

        convertView.setOnClickListener(onClickListener(getItem(position), String.valueOf(position + 1)));

        return convertView;
    }

    private View.OnClickListener onClickListener(final Apps apps, final String pos) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.layout_dialog_app);
                dialog.setTitle("Selected Country");

                TextView imSummaryApp = (TextView) dialog.findViewById(R.id.imSummaryApp);
                TextView position = (TextView) dialog.findViewById(R.id.pos);
                ImageView image = (ImageView) dialog.findViewById(R.id.imImageApp);

                imSummaryApp.setText(apps.getSummary());
                position.setText(pos);
                String appImage = apps.getImage();
                //image.setImageResource(Integer.parseInt(apps.getImage()));
                Picasso.with(activity).load(appImage).into(image);

                dialog.show();
            }
        };
    }

    private static class ViewHolder {
        private ImageView image;
        private TextView name;

        public ViewHolder(View v) {
            image = (ImageView) v.findViewById(R.id.imImage);
            name = (TextView) v.findViewById(R.id.imName);
        }
    }
}
