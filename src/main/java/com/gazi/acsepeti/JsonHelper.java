package com.gazi.acsepeti;

import com.gazi.acsepeti.components.restaurant.Restaurant;
import com.gazi.acsepeti.models.CartItemModel;
import com.gazi.acsepeti.models.FoodModel;
import com.gazi.acsepeti.models.RestaurantModel;
import com.gazi.acsepeti.models.UserModel;
import javafx.scene.control.Alert;

import javax.json.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JsonHelper {
    public void readDataFromRestaurants(ArrayList<Restaurant> restaurants) throws IOException {
        JsonArray jsonArray = Json.createReader(new FileReader("src/main/java/com/gazi/acsepeti/data/restaurants.json", StandardCharsets.UTF_8)).readArray();
        for (JsonObject restaurantO : jsonArray.getValuesAs(JsonObject.class)) {
            ArrayList<FoodModel> foodModelList = new ArrayList<>();
            int id = restaurantO.getInt("id");
            String name = restaurantO.getString("name");
            String imgUrl = restaurantO.getString("imgUrl");

            JsonArray foods = restaurantO.getJsonArray("foods");
            for (JsonObject food : foods.getValuesAs(JsonObject.class)) {
                int foodId = food.getInt("id");
                String foodName = food.getString("name");
                String description = food.getString("description");
                String foodImgUrl = food.getString("imgUrl");
                double price = food.getJsonNumber("price").doubleValue();
                FoodModel foodModel = new FoodModel(foodId, foodName, description, foodImgUrl, price);
                foodModelList.add(foodModel);
            }
            RestaurantModel restaurantModel = new RestaurantModel(id, name, imgUrl, foodModelList);
            Restaurant restaurant = new Restaurant(restaurantModel);
            restaurants.add(restaurant);
        }
    }

    public void saveUser(UserModel userModel) {
        JsonArray existingUsers = readJsonArrayFromFile("src/main/java/com/gazi/acsepeti/data/users.json");

        if (isEmailAlreadyExists(existingUsers, userModel.mail)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("MAİL ADRESİ ZATEN KAYITLI");
            alert.show();
            return;
        }

        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (JsonValue value : existingUsers) {
            builder.add(value);
        }
        JsonObject newUser = createJsonObject(userModel);
        builder.add(newUser);

        writeJsonArrayToFile(builder.build(), "src/main/java/com/gazi/acsepeti/data/users.json");
        Main.userModel = userModel;
        Main.getProfile();

    }

    // Mail adresinin daha önceden var olup olmadığını kontrol eden metod
    private boolean isEmailAlreadyExists(JsonArray existingUsers, String newEmail) {
        for (JsonValue value : existingUsers) {
            if (value instanceof JsonObject) {
                JsonObject userObject = (JsonObject) value;
                String storedEmail = userObject.getString("mail");
                if (storedEmail.equals(newEmail)) {
                    return true;
                }
            }
        }
        return false;
    }

    public JsonArray readJsonArrayFromFile(String filePath) {
        try (JsonReader reader = Json.createReader(new FileReader(filePath))) {
            return reader.readArray();
        } catch (IOException e) {
            return Json.createArrayBuilder().build();
        }
    }

    public void writeJsonArrayToFile(JsonArray jsonArray, String filePath) {
        try (JsonWriter writer = Json.createWriter(new FileWriter(filePath))) {
            writer.writeArray(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject createJsonObject(UserModel userModel) {
        JsonArrayBuilder cartArrayBuilder = Json.createArrayBuilder();

        for (CartItemModel item : userModel.cart) {
            JsonObject itemJson = Json.createObjectBuilder()
                    .add("id", item.food.id)
                    .add("name", item.food.name)
                    .add("description", item.food.description)
                    .add("url", item.food.imageUrl)
                    .add("price", item.food.price)
                    .add("amount", item.count)
                    .build();
            cartArrayBuilder.add((JsonValue) itemJson);
        }

        JsonArray cartArray = cartArrayBuilder.build();
        return Json.createObjectBuilder()
                .add("id", userModel.id)
                .add("name", userModel.name)
                .add("surname", userModel.surname)
                .add("mail", userModel.mail)
                .add("password", userModel.password)
                .add("address", userModel.address)
                .add("tel", userModel.tel)
                .add("cart", cartArray)
                .build();
    }

    public boolean checkUserExistence(String email, String password) throws IOException {
        JsonArray usersArray = Json.createReader(new FileReader("src/main/java/com/gazi/acsepeti/data/users.json", StandardCharsets.UTF_8)).readArray();

        for (JsonObject userObject : usersArray.getValuesAs(JsonObject.class)) {
            String storedEmail = userObject.getString("mail");
            String storedPassword = userObject.getString("password");
            if (email.equals(storedEmail) && password.equals(storedPassword)) {
                Main.userModel = convertJsonObjectToUserModel(userObject);
                return true;
            }
        }
        return false;
    }

    public UserModel convertJsonObjectToUserModel(JsonObject userObject) {
        int id = userObject.getInt("id");
        String name = userObject.getString("name");
        String surname = userObject.getString("surname");
        String email = userObject.getString("mail");
        String password = userObject.getString("password");
        String address = userObject.getString("address");
        String tel = userObject.getString("tel");
        ArrayList<CartItemModel> cart = new ArrayList<>();

        JsonArray cartArray = userObject.getJsonArray("cart");
        for (JsonValue cartItem : cartArray) {
            CartItemModel itemModel = convertJsonToCartItemModel(cartItem);
            cart.add(itemModel);
        }

        return new UserModel(id, name, surname, email, password, address, tel, cart);
    }

    private CartItemModel convertJsonToCartItemModel(JsonValue cartItemValue) {
        JsonObject cartItemJson = (JsonObject) cartItemValue; // JsonValue'i JsonObject'e dönüştürün
        int id = cartItemJson.getInt("id");
        String name = cartItemJson.getString("name");
        String description = cartItemJson.getString("description");
        String url = cartItemJson.getString("url");
        double price = cartItemJson.getJsonNumber("price").doubleValue();
        int amount = cartItemJson.getInt("amount");

        return new CartItemModel(new FoodModel(id, name, description, url, price), amount);
    }

    public void updateUser(UserModel userModel) {
        try {
            JsonArray existingUsers = readJsonArrayFromFile("src/main/java/com/gazi/acsepeti/data/users.json");

            JsonArrayBuilder builder = Json.createArrayBuilder();

            for (JsonValue value : existingUsers) {
                if (value instanceof JsonObject) {
                    JsonObject userObject = (JsonObject) value;
                    String existingUserName = userObject.getString("name");

                    if (existingUserName.equals(Main.userModel.name)) {
                        JsonObject updatedUser = createJsonObject(userModel);
                        builder.add(updatedUser);
                    } else {
                        builder.add(userObject);
                    }
                } else {
                    builder.add(value);
                }
            }

            writeJsonArrayToFile(builder.build(), "src/main/java/com/gazi/acsepeti/data/users.json");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
