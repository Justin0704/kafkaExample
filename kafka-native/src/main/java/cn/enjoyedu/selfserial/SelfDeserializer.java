package cn.enjoyedu.selfserial;

import cn.enjoyedu.vo.UserVO;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * 自定义反序列化器
 */
public class SelfDeserializer implements Deserializer<UserVO> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public UserVO deserialize(String topic, byte[] data) {
        try {
            if(data==null){
                return null;
            }
            if(data.length<8){
                throw new SerializationException("Error data size.");
            }
            ByteBuffer buffer = ByteBuffer.wrap(data);
            int id;
            String name;
            int nameSize;
            id = buffer.getInt();
            nameSize = buffer.getInt();
            byte[] nameByte = new byte[nameSize];
            buffer.get(nameByte);
            name = new String(nameByte,"UTF-8");
            return new UserVO(id,name);
        } catch (Exception e) {
            throw new SerializationException("Error Deserializer DemoUser."+e);
        }
    }

    @Override
    public void close() {

    }
}
