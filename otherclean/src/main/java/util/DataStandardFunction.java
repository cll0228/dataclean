package util;

import com.holyrobot.common.Commentinfo;
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

        if (null != receiverData && null != receiverData.getType() && receiverData.getType() == 11) {
            Commentinfo commoninfo = OtherStandard.standard(receiverData.getData());

            receiverData.setData(commoninfo);
        }
        return receiverData;
    }


}