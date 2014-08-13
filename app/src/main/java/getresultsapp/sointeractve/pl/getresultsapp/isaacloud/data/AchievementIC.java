package getresultsapp.sointeractve.pl.getresultsapp.isaacloud.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.LinkedList;

import getresultsapp.sointeractve.pl.getresultsapp.pebble.responses.AchievementDescriptionResponse;
import getresultsapp.sointeractve.pl.getresultsapp.pebble.responses.AchievementResponse;
import getresultsapp.sointeractve.pl.getresultsapp.pebble.responses.ResponseItem;

public class AchievementIC {
    private final int id;
    private final String name;
    private final String description;

    public AchievementIC(final JSONObject json) throws JSONException {
        id = json.getInt("id");
        name = json.getString("label");
        description = json.getString("description");
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof AchievementIC)) {
            return false;
        }

        final AchievementIC achievement = (AchievementIC) obj;
        return id == achievement.id;
    }

    public int hashCode() {
        return id;
    }

    public ResponseItem toAchievementResponse() {
        return new AchievementResponse(id, name, description);
    }

    public Collection<ResponseItem> toAchievementDescriptionResponse() {
        final Collection<ResponseItem> responseItems = new LinkedList<ResponseItem>();
        responseItems.add(new AchievementDescriptionResponse(id, description));
        return responseItems;
    }
}
