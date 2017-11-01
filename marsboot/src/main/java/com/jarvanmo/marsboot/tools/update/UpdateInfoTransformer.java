package com.jarvanmo.marsboot.tools.update;

/**
 * Created by mo on 17-9-14.
 *
 * @author mo
 */

public interface UpdateInfoTransformer {

    /**
     *@param source the message from remote server
     *@return the information to build a dialog
     **/
    UpdateInfo transform(String source);
}
