package client.yalantis.com.foldingtabbarandroid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrewkhristyan on 12/9/16.
 */

public class DataManager {

    private static List<ChatModel> sChatModels = new ArrayList<>();

    static {
        sChatModels.add(new ChatModel("Ann Drewer", "Hey, why didn't you call me?", R.drawable.ic_img_chat_one, "5 min ago"));
        sChatModels.add(new ChatModel("Ann Drewer", "Hey, why didn't you call me?", R.drawable.ic_img_chat_one, "5 min ago"));
        sChatModels.add(new ChatModel("Ann Drewer", "Hey, why didn't you call me?", R.drawable.ic_img_chat_one, "5 min ago"));
    }

    public static List<ChatModel> getChatModels() {
        return sChatModels;
    }
}
