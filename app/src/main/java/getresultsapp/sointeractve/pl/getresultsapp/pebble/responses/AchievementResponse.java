package getresultsapp.sointeractve.pl.getresultsapp.pebble.responses;

import com.sointeractive.android.kit.util.PebbleDictionary;

import java.util.List;

import getresultsapp.sointeractve.pl.getresultsapp.config.Settings;

public class AchievementResponse implements ResponseItem {
    private static final int RESPONSE_ID = 4;

    private final int id;
    private final String name;
    private final String description;

    public AchievementResponse(final int id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public List<PebbleDictionary> getData() {
        int descriptionMaxLength = Settings.MAX_ACHIEVEMENTS_DESCRIPTION_STR_LEN;
        descriptionMaxLength -= name.length();

        return new DictionaryBuilder(RESPONSE_ID)
                .addInt(id)
                .addString(name)
                .addInt(getDescriptionPartsNumber())
                .pack();
    }

    private int getDescriptionPartsNumber() {
        final double stringLength = description.length();
        final double partSize = Settings.MAX_ACHIEVEMENTS_DESCRIPTION_STR_LEN;
        return (int) Math.ceil(stringLength / partSize);
    }
}