package util;

import com.holyrobot.common.ReceiverData;
import com.holyrobot.common.TripEntity;
import org.apache.spark.api.java.function.Function;

import java.util.List;

/**
 * Created by cuill on 2018/2/11.
 */
public class DataStandardFunction implements Function<ReceiverData, ReceiverData> {

    private SaveCleanDataToKafka toKafka;

    @Override
    public ReceiverData call(ReceiverData receiverData) throws Exception {
        if (null == toKafka) {
            toKafka = new SaveCleanDataToKafka();
        }

        if (null != receiverData && null != receiverData.getType() && receiverData.getType() == 1) {
            List<TripEntity> triplist = RouteStandard.standardRoute(receiverData.getData());

            receiverData.setData(triplist);
        }
        return receiverData;
    }


}