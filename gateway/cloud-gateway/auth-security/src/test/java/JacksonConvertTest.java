import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.sun.authorize.entity.IDepartment;
import com.sun.authorize.entity.impl.Auth;
import com.sun.authorize.entity.impl.BaseDepartment;
import org.junit.Test;

import java.io.IOException;

public class JacksonConvertTest {

    @Test
    public void xx() throws IOException {

        String s = "{\"user\":{\"id\":1,\"username\":\"admin\",\"password\":\"------\",\"name\":\"系统管理员\"," +
                "\"department\":{\"id\":1,\"name\":null}," +
                "\"resources\":null},\"roles\":null," +
                "\"resources\":[{\"id\":null,\"code\":\"CLUE\",\"type\":null},{\"id\":null,\"code\":\"STATISTICS\",\"type\":null},{\"id\":null,\"code\":\"PAYMENT\",\"type\":null},{\"id\":null,\"code\":\"SALEINDEX\",\"type\":null},{\"id\":null,\"code\":\"SETTINGS\",\"type\":null},{\"id\":null,\"code\":\"CLUE_PUBLICSEA\",\"type\":null},{\"id\":null,\"code\":\"CLUE_CUSTOMER\",\"type\":null},{\"id\":null,\"code\":\"CLUE_CUSTOMER\",\"type\":null},{\"id\":null,\"code\":\"CLUE_AGREEMENT\",\"type\":null},{\"id\":null,\"code\":\"CLUE_PAYMENTRECORD\",\"type\":null},{\"id\":null,\"code\":\"STATISTICS_AGREEMENT\",\"type\":null},{\"id\":null,\"code\":\"PAYMENT_SERVICERECORD\",\"type\":null},{\"id\":null,\"code\":\"SETTING_ORGNIZATION\",\"type\":null},{\"id\":null,\"code\":\"SETTING_POSTION\",\"type\":null},{\"id\":null,\"code\":\"SETTING_PRODUCTSETTING\",\"type\":null},{\"id\":null,\"code\":\"CLUE_PUBLICSEA_ADD\",\"type\":null},{\"id\":null,\"code\":\"CLUE_PUBLICSEA_DELETE\",\"type\":null},{\"id\":null,\"code\":\"CLUE_PUBLICSEA_IMPORT\",\"type\":null},{\"id\":null,\"code\":\"CLUE_PUBLICSEA_EXPORT\",\"type\":null},{\"id\":null,\"code\":\"CLUE_PUBLICSEA_ASSIGN\",\"type\":null},{\"id\":null,\"code\":\"CLUE_PUBLICSEA_SHARE\",\"type\":null},{\"id\":null,\"code\":\"CLUE_CUSTOMER_UNDISTRIBUTE\",\"type\":null},{\"id\":null,\"code\":\"CLUE_CUSTOMER_UPDATE\",\"type\":null},{\"id\":null,\"code\":\"CLUE_CUSTOMER_SHARE\",\"type\":null},{\"id\":null,\"code\":\"CLUE_CUSTOMER_ADD\",\"type\":null},{\"id\":null,\"code\":\"CLUE_CUSTOMER_UPDATE\",\"type\":null},{\"id\":null,\"code\":\"CLUE_CUSTOMER_DELETE\",\"type\":null},{\"id\":null,\"code\":\"CLUE_CUSTOMER_VIEW\",\"type\":null},{\"id\":null,\"code\":\"CLUE_AGREEMENT_ADD\",\"type\":null},{\"id\":null,\"code\":\"CLUE_AGREEMENT_UPDATE\",\"type\":null},{\"id\":null,\"code\":\"CLUE_AGREEMENT_DELETE\",\"type\":null},{\"id\":null,\"code\":\"CLUE_AGREEMENT_VIEW\",\"type\":null},{\"id\":null,\"code\":\"PAYMENT_VIEW\",\"type\":null},{\"id\":null,\"code\":\"PAYMENT_ASSIGNCUSTOMERSERVICE\",\"type\":null},{\"id\":null,\"code\":\"PAYMENT_DISABLE\",\"type\":null},{\"id\":null,\"code\":\"PAYMENT_EXPORT\",\"type\":null},{\"id\":null,\"code\":\"PAYMENT_SERVICERECORD_ADD\",\"type\":null},{\"id\":null,\"code\":\"SETTING_ORGNIZATION_ADD\",\"type\":null},{\"id\":null,\"code\":\"SETTING_ORGNIZATION_UPDATE\",\"type\":null},{\"id\":null,\"code\":\"SETTING_ORGNIZATION_DELETE\",\"type\":null},{\"id\":null,\"code\":\"SETTING_ORGNIZATION_VIEW\",\"type\":null},{\"id\":null,\"code\":\"SETTING_ORGNIZATION_DISABLE\",\"type\":null},{\"id\":null,\"code\":\"SETTING_POSTION_ADD\",\"type\":null},{\"id\":null,\"code\":\"SETTING_POSTION_UPDATE\",\"type\":null},{\"id\":null,\"code\":\"SETTING_POSTION_DELETE\",\"type\":null},{\"id\":null,\"code\":\"SETTING_POSTION_VIEW\",\"type\":null},{\"id\":null,\"code\":\"SETTING_PRODUCTSETTING_ADD\",\"type\":null},{\"id\":null,\"code\":\"SETTING_PRODUCTSETTING_UPDATE\",\"type\":null},{\"id\":null,\"code\":\"SETTING_PRODUCTSETTING_DELETE\",\"type\":null},{\"id\":null,\"code\":\"SETTING_PRODUCTSETTING_VIEW\",\"type\":null}]}";
        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerSubtypes(BaseDepartment.class);


        mapper.registerSubtypes(IDepartment.class);
        mapper.registerSubtypes(new NamedType(BaseDepartment.class, "department"));


        Auth auth = mapper.readValue(s, Auth.class);
        System.out.println(auth.getName());
        System.out.println(auth.getUser().getDepartment());
        System.out.println(auth.getResources());
    }
}
