package me.chasertw123.minigames.core.user.data.reports;

import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.core.user.User;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Chase on 6/28/2017.
 */
public class Report {

    private UUID reported, reportee, staff = null;
    private String message, date;

    private ReportState reportState;
    private ReportResult reportResult = null;

    /**
     * Creates a new report instance of a saved report. This does
     * send notifications to staff if the {@link ReportState} is
     * OPEN upon creation of the instance. This constructor should
     * only be used when loading old reports that had been saved to
     * the players info in the database
     *
     * @param data String extracted from database save
     */
    public Report(String data) {

        String[] parts = data.split("!---!");

        this.reported = UUID.fromString(parts[0]);
        this.reportee = UUID.fromString(parts[1]);
        this.message = parts[2];
        this.date = parts[3];
        this.reportState = ReportState.valueOf(parts[4]);
        this.staff = (parts[5].equals("NULL") ? null : UUID.fromString(parts[5]));
        this.reportResult = parts[6].equals("NULL") ? null : ReportResult.valueOf(parts[6]);

        if (reportState == ReportState.OPEN) {
            // TODO: Send notification to staff
            // TODO: Update all report gui
        }
    }

    /**
     * Creates a new report on a player and also sends notifications
     * to online staff members. This should only be used when a new
     * report is submitted and not to re-instance saved reports.
     *
     * @param reported {@link User} instance of reported player
     * @param reportee {@link User} instance of player who submitted report
     * @param message {@link String} reportee send with report
     */
    public Report(User reported, User reportee, String message) {
        this.reported = reported.getUUID();
        this.reportee = reportee.getUUID();
        this.message = message;
        this.date = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date());
        this.reportState = ReportState.OPEN;

        // TODO: Send notification to staff
        // TODO: Update all report gui
    }

    /**
     * To String method [override]
     * Returns a string data version of this class for being stored in database(s).
     */
    @Override
    public String toString(){
        return reported.toString() + "!---!" + reportee.toString() + "!---!" + message.replaceAll("!---!", "")
                + "!---!" + date + "!---!" + reportState.toString()+ "!---!" + (staff == null ? "NULL" : staff.toString())
                + "!---!" + (reportResult == null ? "NULL" : reportResult.toString());
    }

    /**
     * Gets the UUID of the reported player
     * @return the reported player's {@link UUID}
     */
    public UUID getReported() {
        return reported;
    }

    /**
     * Gets the UUID of the reportee player
     * @return the reportee player's {@link UUID}
     */
    public UUID getReportee() {
        return reportee;
    }

    /**
     * Gets the message to go along with the report
     * @return the message attached to the report
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the date when the report was submitted
     * @return date when report was submitted
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the date from the date string
     * @return Date of when report was submitted
     */
    public Date getDateFromString() {
        try {
            return new SimpleDateFormat("dd-MM-yy HH:mm").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Gets the current {@link ReportState} of report
     * @return ReportState of this report
     */
    public ReportState getReportState() {
        return reportState;
    }

    /**
     * Sets the current {@link ReportState} of report
     * @param reportState State to set the report too
     */
    public void setReportState(ReportState reportState) {
        this.reportState = reportState;
    }

    /**
     * Gets the staff member that is handling/handled the report
     * @return UUID of the staff member who managed this report
     */
    public UUID getStaff() {
        return staff;
    }

    /**
     * Sets the staff member who is handling this report
     * @param staff {@link UUID} of the staff handling this report
     */
    public void setStaff(UUID staff) {
        this.staff = staff;
    }

    /**
     * Gets the result of the staff member looking into the report
     * @return ReportResult staff deemed needed
     */
    public ReportResult getReportResult() {
        return reportResult;
    }

    /**
     * Sets the result of this report
     * @param reportResult {@link ReportResult} staff deemed needed
     */
    public void setReportResult(ReportResult reportResult) {
        this.reportResult = reportResult;
    }

    /**
     * Crafts the cItemStack to display all the information of the report
     * @return cItemStack with all information of the report
     */
    public cItemStack toItemStack() {

        cItemStack i = new cItemStack(Material.WOOL);

        i.addLore(ChatColor.GRAY + "Filed on: " + ChatColor.GOLD  + this.getDate(), "", ChatColor.GRAY + "Filed against: " + ChatColor.GOLD  + this.getReported(),
                ChatColor.GRAY + "Filed by: " + ChatColor.GOLD  + this.getReportee(), "", ChatColor.WHITE + "Reportee Messages:");

        i.addFancyLore(this.getMessage(), ChatColor.DARK_GRAY.toString());

        switch (this.getReportState()) {
            case CLOSED:
                i.setDisplayName(ChatColor.RED + "Closed Report");
                i.addLore("", ChatColor.WHITE + "Handled by: " + ChatColor.DARK_GRAY + this.getStaff(), ChatColor.WHITE + "Report Result: " + ChatColor.DARK_GRAY + this.getReportResult().getFancyString());
                i.setDurability((short) 14);
                break;

            case INPROGRESS:
                i.setDisplayName(ChatColor.YELLOW + "In-Progress Report");
                i.addLore("", ChatColor.WHITE + "In-Progress by: " + ChatColor.DARK_GRAY + this.getStaff(), "", ChatColor.RED + "Shift-Click to close Report");
                i.setDurability((short) 4);
                break;

            case OPEN:
                i.setDisplayName(ChatColor.GREEN + "Open Report");
                i.addLore("", ChatColor.YELLOW + "Shift-Click to set In-Progress");
                i.setDurability((short) 5);
                break;
        }

        return i;
    }
}