import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProcessor.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String createOrderJson(String orderId, String customerName, double totalAmount) {
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(customerName)) {
            LOGGER.error("Thiếu thông tin mã đơn hàng hoặc tên khách hàng");
            throw new IllegalArgumentException("Mã đơn hàng và tên khách hàng không được trống");
        }
        if (totalAmount < 0) {
            LOGGER.warn("Tổng tiền âm không hợp lệ: {}", totalAmount);
            throw new IllegalArgumentException("Tổng tiền không được âm");
        }

        Map<String, Object> orderData = new LinkedHashMap<>();
        orderData.put("orderId", StringUtils.trim(orderId).toUpperCase());
        orderData.put("customerName", StringUtils.normalizeSpace(customerName));
        orderData.put("totalAmount", totalAmount);

        try {
            String json = MAPPER.writeValueAsString(orderData);
            LOGGER.info("Tạo dữ liệu đơn hàng JSON thành công: {}", json);
            return json;
        } catch (JsonProcessingException e) {
            LOGGER.error("Lỗi chuyển đổi JSON cho đơn hàng {}", orderId, e);
            throw new RuntimeException("Lỗi xử lý JSON", e);
        }
    }
}