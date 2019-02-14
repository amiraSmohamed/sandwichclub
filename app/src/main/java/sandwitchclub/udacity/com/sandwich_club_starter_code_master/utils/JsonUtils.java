package sandwitchclub.udacity.com.sandwich_club_starter_code_master.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sandwitchclub.udacity.com.sandwich_club_starter_code_master.model.Sandwich;

public class JsonUtils {
    public static final String OBJECT_NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String INGREDIENTS = "ingredients";
    public static ArrayList<String> knownAsList;
    public static ArrayList<String> ingerdientsList;
    public static Sandwich sandwich;
    public static Sandwich parseSandwichJson(String json) throws JSONException {

//        JSONArray jsonArray = new JSONArray(json);
        try {
            knownAsList = new ArrayList<String>();
            ingerdientsList = new ArrayList<String>();
//            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject mainObject = jsonObject.getJSONObject(OBJECT_NAME);
                String main_name = mainObject.getString(MAIN_NAME);
                JSONArray alsoKnownAs = (JSONArray) mainObject.get(ALSO_KNOWN_AS);
                String placeOForigin = jsonObject.getString(PLACE_OF_ORIGIN);
                String description = jsonObject.getString(DESCRIPTION);
                String image =  jsonObject.getString(IMAGE);
                JSONArray ingerdients = (JSONArray) jsonObject.get(INGREDIENTS);
                getList(alsoKnownAs, knownAsList);
                getList(ingerdients,ingerdientsList);
                sandwich = new Sandwich(main_name, knownAsList, placeOForigin, description, image, ingerdientsList);

            //}
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }


    public  static ArrayList getList(JSONArray jsonArray,ArrayList list) throws JSONException {
        if (jsonArray != null) {
            for (int j = 0; j < jsonArray.length(); j++) {
                list.add(jsonArray.getString(j));
            }
        }
        return list;
    }
}