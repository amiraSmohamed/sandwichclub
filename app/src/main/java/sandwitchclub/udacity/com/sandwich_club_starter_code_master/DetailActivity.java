package sandwitchclub.udacity.com.sandwich_club_starter_code_master;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

import sandwitchclub.udacity.com.sandwich_club_starter_code_master.model.Sandwich;
import sandwitchclub.udacity.com.sandwich_club_starter_code_master.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    public static Sandwich sandwich = null;
    TextView mainName;
    TextView placeOfOrigin;
    TextView origin;
    TextView discription;
    ImageView ingredientsIv;
    TextView otherNamesDisplay;
    TextView ingredientDisplay;
    TextView knownAS;
    boolean IS_VISIBLE;
    ImageView sandwichImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mainName = findViewById(R.id.mainName_display);
        placeOfOrigin = findViewById(R.id.origin_display);
        origin = findViewById(R.id.origin_tv);
        discription = findViewById(R.id.description_display);
        ingredientsIv = findViewById(R.id.image_iv);
        ingredientDisplay = findViewById(R.id.ingredient_display_tv);
        otherNamesDisplay = findViewById(R.id.other_names_tv);
        knownAS = findViewById(R.id.knownAs_tv);
        sandwichImage = findViewById(R.id.image_iv);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
            Log.v("SandwitchMainName_log2", sandwich.getMainName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        boolean ifVisible = checkVisibilty(knownAS);
        boolean ifVisible2 = checkVisibilty(otherNamesDisplay);
        boolean ifVisible3 = checkVisibilty(placeOfOrigin);
        boolean ifVisible4 = checkVisibilty(origin);
        Picasso.with(this).load(sandwich.getImage()).placeholder(R.drawable.placeholder1).error(R.drawable.errorimage1).into((ingredientsIv));
        mainName.setText("- " + sandwich.getMainName());
        discription.setText(sandwich.getDescription());
        ArrayList ingredients = (ArrayList) sandwich.getIngredients();
        if (ingredients != null) {
            for (int i = 0; i < ingredients.size(); i++) {
                ingredientDisplay.append("- " + ingredients.get(i).toString());
                ingredientDisplay.append("\n" + '\n');
            }
        }
        ArrayList otherNames = (ArrayList) sandwich.getAlsoKnownAs();
        if (ifVisible && ifVisible2) {
            knownAS.setVisibility(View.GONE);
            otherNamesDisplay.setVisibility(View.GONE);
        } else {
            if (otherNames != null) {
                for (int i = 0; i < otherNames.size(); i++) {
                    knownAS.setVisibility(View.VISIBLE);
                    otherNamesDisplay.setVisibility(View.VISIBLE);
                    otherNamesDisplay.append("- " + otherNames.get(i).toString());
                    otherNamesDisplay.append("\n" + '\n');
                }
            }
        }
        String originPlace = sandwich.getPlaceOfOrigin();
        if (ifVisible3 && ifVisible4) {
            placeOfOrigin.setVisibility(View.GONE);
            origin.setVisibility(View.GONE);
        } else {
            if (!originPlace.isEmpty() && originPlace != null) {
                placeOfOrigin.setVisibility(View.VISIBLE);
                origin.setVisibility(View.VISIBLE);
                placeOfOrigin.setText(originPlace);
            }
        }
    }

    public boolean checkVisibilty(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            IS_VISIBLE = true;
        } else {
            IS_VISIBLE = false;
        }
        return IS_VISIBLE;
    }

}

