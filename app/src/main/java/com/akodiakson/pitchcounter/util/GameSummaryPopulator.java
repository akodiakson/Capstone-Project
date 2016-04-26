package com.akodiakson.pitchcounter.util;

import com.akodiakson.pitchcounter.model.Game;
import com.akodiakson.pitchcounter.model.SeasonStatsTO;
import com.akodiakson.pitchcounter.model.SummaryItemTO;
import com.akodiakson.pitchcounter.model.SummaryItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ace0808 on 4/22/2016.
 */
public class GameSummaryPopulator {

    public static List<SummaryItemTO> populate(List<Game> gameSummaries, SeasonStatsTO seasonStats){
        List<SummaryItemTO> summaryViewItems = new ArrayList<>();

        SummaryItemTO averagesItem = new SummaryItemTO(SummaryItemType.AVERAGE);
        averagesItem.setData(seasonStats);
        summaryViewItems.add(averagesItem);

        for (Game gameSummary : gameSummaries) {
            SummaryItemTO object = new SummaryItemTO(SummaryItemType.GAME);
            object.setData(gameSummary);
            summaryViewItems.add(object);
        }

        return summaryViewItems;
    }
}
