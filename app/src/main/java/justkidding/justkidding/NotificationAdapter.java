package justkidding.justkidding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends ArrayAdapter<NotificationClass> {

    private Context mContext;
    private List<NotificationClass> notificationsList = new ArrayList<>();


    public NotificationAdapter(Context context, ArrayList<NotificationClass> list) {
        super(context, 0, list);
        this.mContext = context;
        this.notificationsList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.layout_image_text, parent,false);

        NotificationClass currentNotification = notificationsList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.iv_notif_icon);
        if(currentNotification.getTheme().equals("Chevaliers"))
        {
            image.setImageResource(R.drawable.icon_chevaliers);
        } else {
            image.setImageResource(R.drawable.icon_animaux);
        }

        TextView date = (TextView) listItem.findViewById(R.id.tv_notif_date);
        date.setText(currentNotification.getDate());

        TextView text = (TextView) listItem.findViewById(R.id.tv_notif_text);
        text.setText(currentNotification.getText());


        return listItem;
    }

}
