package getresultsapp.sointeractve.pl.getresultsapp.isaacloud.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import getresultsapp.sointeractve.pl.getresultsapp.config.IsaaCloudSettings;
import getresultsapp.sointeractve.pl.getresultsapp.data.App;
import getresultsapp.sointeractve.pl.getresultsapp.pebble.responses.LoginResponse;
import getresultsapp.sointeractve.pl.getresultsapp.pebble.responses.ResponseItem;


public class UserData implements Serializable {
    private static final String TAG = UserData.class.getSimpleName();

    private String name, email, firstName;
    private int userId, locationVisits;
    private Location userLocation;

    private int points = 0;
    private int rank = 0;

    public Location getUserLocation() {
        return this.userLocation;
    }

    public void setUserLocation(int id) {
        for (Location l : App.getLocations()) {
            if (l.getId() == id) {
                this.userLocation = l;
                break;
            }
        }
    }

    public void setUserLocation(Location newLocation) {
        this.userLocation = newLocation;
    }

    public int getUserLocationId() {
        if (userLocation == null) {
            return -1;
        } else {
            return this.userLocation.getId();
        }
    }

    public int getLocationVisits() {
        return locationVisits;
    }

    public void setLocationVisits(int locationVisits) {
        this.locationVisits = locationVisits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLeaderboardData(JSONObject json) {
        try {
            final JSONObject leaderboard = getLeaderboard(json.getJSONArray("leaderboards"));
            if (leaderboard != null) {
                points = leaderboard.getInt("score");
                rank = leaderboard.getInt("position");
            }
        } catch (final JSONException e) {
            Log.e(TAG, "Error: Cannot get leaderboard");
        }
    }

    private JSONObject getLeaderboard(final JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            if (!jsonArray.isNull(i)) {
                final JSONObject leaderboard = jsonArray.getJSONObject(i);
                if (leaderboard.getInt("id") == IsaaCloudSettings.LEADERBOARD_ID) {
                    return leaderboard;
                }
            }
        }
        Log.e(TAG, "Error: No leaderboard with id: " + IsaaCloudSettings.LEADERBOARD_ID);
        return null;
    }

    public ResponseItem toLoginResponse(final String roomName, final int roomsNumber, final int achievementsNumber) {
        return new LoginResponse(getName(), points, rank, roomName, roomsNumber, achievementsNumber);
    }
}