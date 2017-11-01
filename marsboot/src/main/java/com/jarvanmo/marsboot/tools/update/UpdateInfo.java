package com.jarvanmo.marsboot.tools.update;

/**
 * Created by mo on 17-9-14.
 *
 * @author mo
 */

public class UpdateInfo {


    private boolean autoCompare = true;

    private boolean hasUpdate = false;


    private boolean isSilent = false;
    private boolean mustUpdate = false;
    private boolean installDirectly = true;
    private boolean isIgnorable = true;

    private int versionCode = Integer.MAX_VALUE;
    private String versionName;
    private String updateContent;

    private String url;
    private String md5;
    private long size;



    public UpdateInfo(String url) {
        this.url = url;
    }

    public boolean isHasUpdate() {
        return hasUpdate;
    }

    /**
     * @see #setAutoCompare(boolean) (int)
     * */
    public void setHasUpdate(boolean hasUpdate) {
        this.hasUpdate = hasUpdate;
    }

    public boolean isSilent() {
        return isSilent;
    }

    public void setSilent(boolean silent) {
        isSilent = silent;
    }

    public boolean isMustUpdate() {
        return mustUpdate;
    }

    public void setMustUpdate(boolean mustUpdate) {
        this.mustUpdate = mustUpdate;
    }

    public boolean isInstallDirectly() {
        return installDirectly;
    }

    public void setInstallDirectly(boolean installDirectly) {
        this.installDirectly = installDirectly;
    }

    public boolean isIgnorable() {
        return isIgnorable;
    }

    public void setIgnorable(boolean ignorable) {
        isIgnorable = ignorable;
    }

    public int getVersionCode() {
        return versionCode;
    }


    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUrl() {
        return url;
    }


    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isAutoCompare() {
        return autoCompare;
    }

    /**
     * @param autoCompare its priority is higher than hasUpdate
     *                    once it was true,the hasUpdate no longer works
     * **/
    public void setAutoCompare(boolean autoCompare) {
        this.autoCompare = autoCompare;
    }
}
