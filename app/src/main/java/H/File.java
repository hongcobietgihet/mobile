package H;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import  java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class File {

    private static final String FILE_NAME = "users.json";
    public static List<User> readUsers(Context ct){
        List<User> users = new ArrayList<>();
        try{
            FileInputStream fis;
            try {
                fis = ct.openFileInput(FILE_NAME);
            }
            catch (Exception e){
                return users;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null){
                builder.append(line);
            }

            reader.close();
            if (builder.length() == 0) return users;

            JSONArray arr = new JSONArray(builder.toString());

            for(int i = 0; i < arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);

                User user = new User(obj.getString("name"), obj.getString("email"), obj.getString("phone"),
                        obj.getString("pw"),obj.getString("address"), obj.getString("avt"), obj.getString("desc"), obj.getBoolean("isFriend"));
                users.add(user);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public static void saveUsers(Context ct, List<User> users){
        try {
            JSONArray arr = new JSONArray();
            for(User user : users){
                JSONObject obj = new JSONObject();
                obj.put("name", user.getName());
                obj.put("email", user.getEmail());
                obj.put("phone", user.getPhone());
                obj.put("pw", user.getPassword());
                obj.put("address",user.getAddress());
                obj.put("avt", user.getAvatar());
                obj.put("desc", user.getDescription());
                obj.put("isFriend", user.getIsFriend());

                arr.put(obj);
            }

            FileOutputStream fos = ct.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(arr.toString().getBytes());
            fos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
