package msl.demo;

public class StickerInfo {
    private int COST;
    private String IMAGE_PATH;
    private String IMAGE_SERVER_PATH;
    private String IS_DOWNLOADED;
    private boolean IS_DOWNLOADING = false;
    private String IS_HOT;
    private String IS_UPDATED = "false";
    private String MAIN_CATEGORY;
    private int SEQUENCE;
    private long STICKER_ID;
    private String STICKER_NAME;
    private String SUB_CATEGORY;
    private String THUMB_PATH;
    private String THUMB_SERVER_PATH;

    public long getSTICKER_ID() {
        return this.STICKER_ID;
    }

    public void setSTICKER_ID(long j) {
        this.STICKER_ID = j;
    }

    public String getSTICKER_NAME() {
        return this.STICKER_NAME;
    }

    public void setSTICKER_NAME(String str) {
        this.STICKER_NAME = str;
    }

    public String getMAIN_CATEGORY() {
        return this.MAIN_CATEGORY;
    }

    public void setMAIN_CATEGORY(String str) {
        this.MAIN_CATEGORY = str;
    }

    public String getSUB_CATEGORY() {
        return this.SUB_CATEGORY;
    }

    public void setSUB_CATEGORY(String str) {
        this.SUB_CATEGORY = str;
    }

    public String IS_HOT() {
        return this.IS_HOT;
    }

    public void setIS_HOT(String str) {
        this.IS_HOT = str;
    }

    public int getCOST() {
        return this.COST;
    }

    public void setCOST(int i) {
        this.COST = i;
    }

    public String getTHUMB_PATH() {
        return this.THUMB_PATH;
    }

    public void setTHUMB_PATH(String str) {
        this.THUMB_PATH = str;
    }

    public String getIMAGE_PATH() {
        return this.IMAGE_PATH;
    }

    public void setIMAGE_PATH(String str) {
        this.IMAGE_PATH = str;
    }

    public String IS_DOWNLOADED() {
        return this.IS_DOWNLOADED;
    }

    public void setIS_DOWNLOADED(String str) {
        this.IS_DOWNLOADED = str;
    }

    public int getSEQUENCE() {
        return this.SEQUENCE;
    }

    public void setSEQUENCE(int i) {
        this.SEQUENCE = i;
    }

    public String getTHUMB_SERVER_PATH() {
        return this.THUMB_SERVER_PATH;
    }

    public void setTHUMB_SERVER_PATH(String str) {
        this.THUMB_SERVER_PATH = str;
    }

    public String getIMAGE_SERVER_PATH() {
        return this.IMAGE_SERVER_PATH;
    }

    public void setIMAGE_SERVER_PATH(String str) {
        this.IMAGE_SERVER_PATH = str;
    }

    public boolean isIS_DOWNLOADING() {
        return this.IS_DOWNLOADING;
    }

    public void setIS_DOWNLOADING(boolean z) {
        this.IS_DOWNLOADING = z;
    }

    public String getIS_UPDATED() {
        return this.IS_UPDATED;
    }

    public void setIS_UPDATED(String str) {
        this.IS_UPDATED = str;
    }
}
