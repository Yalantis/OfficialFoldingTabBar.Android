package client.yalantis.com.foldingtabbarandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by andrewkhristyan on 12/9/16.
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {

    private List<ChatModel> mChatModels = DataManager.getChatModels();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.chatName.setText(mChatModels.get(position).getUserName());
        holder.lastMessage.setText(mChatModels.get(position).getLastMessage());
        holder.time.setText(mChatModels.get(position).getTime());
        holder.userImage.setImageResource(mChatModels.get(position).getChatImageRes());
    }

    @Override
    public int getItemCount() {
        return mChatModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView chatName;
        private TextView lastMessage;
        private ImageView userImage;
        private TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            chatName = (TextView) itemView.findViewById(R.id.text_view_chat_name);
            lastMessage = (TextView) itemView.findViewById(R.id.text_view_last_message);
            time = (TextView) itemView.findViewById(R.id.text_view_time);
            userImage = (ImageView) itemView.findViewById(R.id.image_view_chat);
        }


    }
}
