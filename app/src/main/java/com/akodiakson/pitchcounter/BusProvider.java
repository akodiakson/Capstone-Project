package com.akodiakson.pitchcounter;

import com.squareup.otto.Bus;

/**
 * Created by ace0808 on 4/27/2016.
 */
public class BusProvider {

    private static final Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }
}
