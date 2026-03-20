import org.apache.kafka.common.serialization.Serializer;
import org.example.models.UserInfoDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserInfoSerializer implements Serializer<UserInfoDTO> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String arg0, UserInfoDTO arg1) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper<>();
        try {
            retVal = objectMapper.writeValueAsString(arg1).getBytes();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retVal;
    }

    @Override 
    public void close(){}
}

// do we need to override evry method