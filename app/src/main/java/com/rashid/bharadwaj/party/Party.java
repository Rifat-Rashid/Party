package com.rashid.bharadwaj.party;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by DevWork on 10/22/17.
 */

@IgnoreExtraProperties
public class Party {

    public double lat;
    public double lng;
    public String partyName;

    public Party(){

    }

    // create party
    public Party(String partyName, double lat, double lng){
        this.partyName = partyName;
        this.lat = lat;
        this.lng = lng;
    }

}
