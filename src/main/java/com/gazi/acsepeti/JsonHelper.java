package com.gazi.acsepeti;

//gerekli kütüphanelerin import edilmesi
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
    //restaurants.jsondan restorant ve yemek vetilerini çekmek için bir fonksiyon
    public void readDataFromRestaurants(ArrayList<Restaurant> restaurants) throws IOException {
        //utf8 kodlaması kullanarak restaurants.jsondaki verileri tutan bir JsonArray değişkeni
        JsonArray jsonArray = Json.createReader(new FileReader("src/main/java/com/gazi/acsepeti/data/restaurants.json", StandardCharsets.UTF_8)).readArray();
        //her bir rezsoranta ulaşmamızı sağlayan döngü
        for (JsonObject restaurantO : jsonArray.getValuesAs(JsonObject.class)) {
            // Restoranlardaki yemekleri ve özelliklerini tutmak için FoodModel tipinde bir liste
            ArrayList<FoodModel> foodModelList = new ArrayList<>();
            // jsondan aldığımız verilerle restorantın bilgilerini tutan değişkenler
            int id = restaurantO.getInt("id");
            String name = restaurantO.getString("name");
            String imgUrl = restaurantO.getString("imgUrl");
            JsonArray foods = restaurantO.getJsonArray("foods");

            //her bi yemeğe ulaşmamızı sağlayan döngü
            for (JsonObject food : foods.getValuesAs(JsonObject.class)) {
                //jsondan aldığımız verilerle yemeklerin bilgilerini tutan değişkenler
                int foodId = food.getInt("id");
                String foodName = food.getString("name");
                String description = food.getString("description");
                String foodImgUrl = food.getString("imgUrl");
                double price = food.getJsonNumber("price").doubleValue();
                //FoodModel tipindeki yemeği ve bilgilerini tutan değişken
                FoodModel foodModel = new FoodModel(foodId, foodName, description, foodImgUrl, price);
                //foodModelList listesine oluşturduğumuz foodModeli ekliyoruz
                foodModelList.add(foodModel);
            }
            //restorantı ve özelliklerini tutan bir RestourantModel tipindeki değişken
            RestaurantModel restaurantModel = new RestaurantModel(id, name, imgUrl, foodModelList);
            Restaurant restaurant = new Restaurant(restaurantModel);
            //restaurants listesne oluşturduğumuz restorantı ekliyoruz
            restaurants.add(restaurant);
        }
    }

    //signup işleminde kullanılan kullanıcı kaydetme fonksiyonu
    public void saveUser(UserModel userModel) {
        //kullanıcılaın ulunduğu users.jsondan var olan kullanıcıları tutan değişken
        JsonArray existingUsers = readJsonArrayFromFile("src/main/java/com/gazi/acsepeti/data/users.json");

        //kayıt olurken daha önceden girilmiş bir emailin girilmemesini kontrol ediyoruz
        if (isEmailAlreadyExists(existingUsers, userModel.mail)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("MAİL ADRESİ ZATEN KAYITLI");
            alert.show();
            return;
        }

        //userModel ile kullanıcının girmiş olduğu bilgileri jsonArray olarak tutuyoruz
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (JsonValue value : existingUsers) {
            builder.add(value);
        }
        JsonObject newUser = createJsonObject(userModel);
        builder.add(newUser);

        //jsona kullanıcıların bilgilerini içeren bu jsonArrayı kaydediyoruz
        writeJsonArrayToFile(builder.build(), "src/main/java/com/gazi/acsepeti/data/users.json");
        Main.userModel = userModel;
        Main.getProfile();

    }

    // Mail adresinin daha önceden var olup olmadığını kontrol eden fonksiyon
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

    // kayıt ve giriş işlemlerinde kullandığımız fonksiyonlarda kullanmak için oluşturduğumuz jsondaki verileri okumaya yarayan ardımcı fonksiyon
    public JsonArray readJsonArrayFromFile(String filePath) {
        try (JsonReader reader = Json.createReader(new FileReader(filePath))) {
            return reader.readArray();
        } catch (IOException e) {
            return Json.createArrayBuilder().build();
        }
    }

    // kayıt ve giriş işlemlerinde kullandığımız fonksiyonlarda kullanmak için oluşturduğumuz jsona verileri yazmaya yarayan yardımcı fonksiyon
    public void writeJsonArrayToFile(JsonArray jsonArray, String filePath) {
        try (JsonWriter writer = Json.createWriter(new FileWriter(filePath))) {
            writer.writeArray(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // kayıt ve giriş işlemlerinde kullandığımız fonksiyonlarda kullanmak için oluşturduğumuz kullanıcı modelini jsonObjeccte dönüştürmeye yarayan yardımcı fonksiyon
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
    //giriş yapma işleminde kullandığımız kullanıcının kaydının olup olmadığını kontol eden fonksiyon
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
    // kayıt ve giriş işlemlerinde kullandığımız fonksiyonlarda kullanmak için oluşturduğumuz jsonObjectini userModele dönüştürmeye yarayan yardımcı fonksiyon
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
    // kullanıcının sepetininin kaydedilmesi için kullandığımız jsonValueyu CartItemModele çeviren fonksiyon
    private CartItemModel convertJsonToCartItemModel(JsonValue cartItemValue) {
        JsonObject cartItemJson = (JsonObject) cartItemValue;
        int id = cartItemJson.getInt("id");
        String name = cartItemJson.getString("name");
        String description = cartItemJson.getString("description");
        String url = cartItemJson.getString("url");
        double price = cartItemJson.getJsonNumber("price").doubleValue();
        int amount = cartItemJson.getInt("amount");

        return new CartItemModel(new FoodModel(id, name, description, url, price), amount);
    }
   // kullanıcının verilerini güncelleyen fonksiyon
    public void updateUser(UserModel userModel) {
        try {
            JsonArray existingUsers = readJsonArrayFromFile("src/main/java/com/gazi/acsepeti/data/users.json");

            JsonArrayBuilder builder = Json.createArrayBuilder();

            //avr olan  kullanıcılardan adı o anki kullanıcın adına eşit olan kullanıcının bilgilerini kullanıcının girdiği yeni bilgilerle değiştiriyoruz
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
            //güncelenmiiş bilgileri tekrardan jsona kaydediyoruz
            writeJsonArrayToFile(builder.build(), "src/main/java/com/gazi/acsepeti/data/users.json");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
