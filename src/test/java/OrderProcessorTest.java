import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderProcessorTest {

    @Test
    void testCreateOrderJsonValid() {
        String json = OrderProcessor.createOrderJson("ord-01", "  Cao   Phu Vinh ", 250000.0);
        assertTrue(json.contains("\"orderId\":\"ORD-01\""));
        assertTrue(json.contains("\"customerName\":\"Cao Phu Vinh\""));
    }

    @Test
    void testCreateOrderJsonInvalidInput() {
        assertThrows(IllegalArgumentException.class,
                () -> OrderProcessor.createOrderJson("", "Nguyen Van A", 100000.0));
        assertThrows(IllegalArgumentException.class,
                () ->  OrderProcessor.createOrderJson("ORD-02", "Nguyen Van A", -500.0));
    }
}