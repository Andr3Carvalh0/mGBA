package io.mgba.Data.Search;

import android.os.Parcel;

public class GameSuggestion implements com.arlib.floatingsearchview.suggestions.model.SearchSuggestion{
    public static final Creator<GameSuggestion> CREATOR = new Creator<GameSuggestion>() {
        @Override
        public GameSuggestion createFromParcel(Parcel source) {
            return new GameSuggestion(source);
        }

        @Override
        public GameSuggestion[] newArray(int size) {
            return new GameSuggestion[size];
        }
    };
    private String gameName;

    public GameSuggestion(String gameName) {
        this.gameName = gameName;
    }

    public GameSuggestion() {
    }

    protected GameSuggestion(Parcel in) {
        this.gameName = in.readString();
    }

    @Override
    public String getBody() {
        return gameName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.gameName);
    }
}
