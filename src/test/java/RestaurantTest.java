import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeEach
    void setup(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }



    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        LocalTime currentTime = LocalTime.parse("11:30:00");
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(currentTime);

        boolean isOpen = mockRestaurant.isRestaurantOpen();
        assertTrue(isOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime currentTime = LocalTime.parse("09:30:00");
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(currentTime);

        boolean isOpen = mockRestaurant.isRestaurantOpen();
        assertFalse(isOpen);
    }



    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



    @Test
    public void calculate_order_total_for_empty_item_list() {
        List<String> itemNames = new ArrayList<>();
        int total = restaurant.calculateOrderTotal(itemNames);
        assertEquals(0, total);
    }

    @Test
    public void calculate_order_total_for_single_item() {
        List<String> itemNames = Arrays.asList("Sweet corn soup");
        int total = restaurant.calculateOrderTotal(itemNames);
        assertEquals(119, total);
    }

    @Test
    public void calculate_order_total_for_multiple_items() {
        List<String> itemNames = Arrays.asList("Sweet corn soup", "Vegetable lasagne");
        int total = restaurant.calculateOrderTotal(itemNames);
        assertEquals(388, total); // 119 (Sweet corn soup) + 269 (Vegetable lasagne)
    }
}