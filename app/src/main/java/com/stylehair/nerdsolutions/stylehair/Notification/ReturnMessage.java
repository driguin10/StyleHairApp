package com.stylehair.nerdsolutions.stylehair.Notification;



import java.util.List;

/**
 * Created by Rodrigo on 20/02/2018.
 */

public class ReturnMessage {
    public long multicast_id;
    public int success ;
    public int failure ;
    public int canonical_ids;
    public List<ResultKey> results ;

    public long getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(long multicast_id) {
        this.multicast_id = multicast_id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(int canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

    public List<ResultKey> getResults() {
        return results;
    }

    public void setResults(List<ResultKey> results) {
        this.results = results;
    }
}
