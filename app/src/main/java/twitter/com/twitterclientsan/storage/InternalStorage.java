package twitter.com.twitterclientsan.storage;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twitter.sdk.android.core.models.Tweet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by Sangram Mohanty on 6/27/2018.
 * Internal Storage for read and write the object
 */

public class InternalStorage {

    /*
     * Method to write the object to file
     * Store fresh 10 objects
     *@param context, Key, Object
     */

    public static void writeObject(Context context, String key, ArrayList<Tweet> object) throws IOException {
        Type listType = new TypeToken<ArrayList<Tweet>>() {
        }.getType();
        Gson gson = new Gson();
        String json = gson.toJson(object, listType);
        Log.e("Jason Array", json);
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(json);
        oos.close();
        fos.close();
    }

    /*
     * get Object from file
     *@param context, Key
     * @return store tweet object
     */
    public static ArrayList<Tweet> readObject(Context context, String key) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        String object = (String) ois.readObject();
        Type listType = new TypeToken<ArrayList<Tweet>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<Tweet> valueTweet = gson.fromJson(object, listType);
        return valueTweet;
    }

}
