package com.akodiakson.pitchcounter.model;

/**
 * Created by ace0808 on 4/22/2016.
 */
public class SummaryItemTO {

    private SummaryItemType summaryItemType;
    private Object data;

    public SummaryItemTO(SummaryItemType summaryItemType){
        this.summaryItemType = summaryItemType;
    }

    public Object getData() {
        return data;
    }

    public SummaryItemType getSummaryItemType() {
        return summaryItemType;
    }



    public void setData(Object data) {
        this.data = data;
    }
}
