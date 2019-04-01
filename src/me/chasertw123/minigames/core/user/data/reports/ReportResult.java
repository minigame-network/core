package me.chasertw123.minigames.core.user.data.reports;

/**
 * Created by Chase on 6/29/2017.
 */
public enum ReportResult {

    BAN("Permanent Ban"),
    TEMPBAN("Temporary Ban"),
    MUTE("Permanent Mute"),
    TEMPMUTE("Temporary Mute"),
    WARNING("Was Verbally Warned"),
    NOTENOUGH("Not Enough Evidence"),
    INNOCENT("Without a doubt innocent");

    private String result;

    ReportResult(String result) {
        this.result = result;
    }

    public String getFancyString() {
        return result;
    }
}
