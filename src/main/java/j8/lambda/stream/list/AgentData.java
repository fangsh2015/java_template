package j8.lambda.stream.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 渠道数据，其中有每个渠道的用户数，以及交易的金额数
 * Created by Niki on 2018/10/22 17:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentData {
    private String agentType;

    private int userNumber;

    private float amount;

    private Date date;

    public AgentData(String agentType, int userNumber, float amount) {
        this.agentType = agentType;
        this.userNumber = userNumber;
        this.amount = amount;
    }

    public AgentData(String agentType, int userNumber) {
        this.agentType = agentType;
        this.userNumber = userNumber;
    }
}
