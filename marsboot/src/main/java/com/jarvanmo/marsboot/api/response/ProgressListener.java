package com.jarvanmo.marsboot.api.response;

/**
 * Created by mo on 16-10-11.
 *
 * @author mo
 */

public interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
