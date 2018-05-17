package com.example.artem.firstappwithnotification.fcm;

import com.example.artem.firstappwithnotification.database.Order;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class MessagesClientFCMServer {

    private static final Logger log = Logger.getLogger(MessagesClientFCMServer.class.getName());

    private static String SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static String FCM_ORDERS_ENDPOINT = "https://fcm.googleapis.com/v1/projects/firstappwithnotification/messages:send";

    public static void main(String args[]){
        MessagesClientFCMServer fcmClient = new MessagesClientFCMServer();

        fcmClient.sendNotification();
        fcmClient.sendData();
    }

    private void sendNotification(){
        String notificationTitle = "Latest order";
        String notificationBody = "View latest order from FSS";

        sendMessageToFcm(getFcmMessageJSONNotification(notificationTitle,notificationBody));
    }

    private void sendData(){
        sendMessageToFcm(getFcmMessageJSONData());
    }

    private void sendDataNotification(){
        String notificationTitle = "Latest order";
        String notificationBody = "View latest orders from the FSS";
        sendMessageToFcm(getFcmMessageJSONDataAndNotification(notificationTitle,notificationBody));

    }

    private void sendMessageToFcm(String postData){
        try {
                HttpURLConnection httpCon = getConnection();
                httpCon.setDoOutput(true);
                httpCon.setUseCaches(false);
                httpCon.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream());

                wr.writeBytes(postData);
                wr.flush();
                wr.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
                String inputLine;

                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                log.info(response.toString());


            }catch(Exception e){
                e.printStackTrace();
            }
        }

        private static String getAccessToken() throws IOException{
            GoogleCredential googleCredential = GoogleCredential
                    .fromStream(new FileInputStream("app/firebase-admin-key.json"))
                    .createScoped(Arrays.asList(SCOPE));
            googleCredential.refreshToken();
            String token = googleCredential.getAccessToken();
            return token;
        }

        private HttpURLConnection getConnection() throws IOException{
            URL url = new URL(FCM_ORDERS_ENDPOINT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Authorization","Bearer " + getAccessToken());
            httpURLConnection.setRequestProperty("Content-Type","application/json; UTF-8");
            return httpURLConnection;
        }

        private JsonElement getOrderInJsonFormat(){
            Order ordersList = prepareLatestOrderData();
            Gson gson = new Gson();
            Type type = new TypeToken<Order>(){}.getType();

            JsonElement jsonElement = gson.toJsonTree(ordersList,type);
            return jsonElement;
        }

        private String getFcmMessageJSONData(){
            JsonElement ordersJson = getOrderInJsonFormat();

            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("topic", "Orders");
            jsonObj.add("data",ordersJson);

            JsonObject msgObj = new JsonObject();
            msgObj.add("message",jsonObj);

            log.info("json message" + msgObj.toString());

            return msgObj.toString();
        }

        private String getFcmMessageJSONNotification(String title, String msg){
            JsonObject notifiDetails = new JsonObject();
            notifiDetails.addProperty("body", msg);
            notifiDetails.addProperty("title", title);

            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("topic", "orders");
            jsonObj.add("notification", notifiDetails);

            JsonObject msgObject = new JsonObject();
            msgObject.add("message", jsonObj);

            log.info("json message" + msgObject.toString());

            return msgObject.toString();
        }

        private String getFcmMessageJSONDataAndNotification(String title, String message){

            JsonElement ordersJson = getOrderInJsonFormat();

            JsonObject notifiDetails = new JsonObject();
            notifiDetails.addProperty("body",message);
            notifiDetails.addProperty("title",title);

            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("topic", "orders");
            jsonObj.add("data", ordersJson);
            jsonObj.add("notification", notifiDetails);

            JsonObject msgObject = new JsonObject();
            msgObject.add("message", jsonObj);

            log.info("json message" + msgObject.toString());

            return msgObject.toString();
        }

       private Order prepareLatestOrderData() {
           List<Order> orderList = new ArrayList<Order>();
           Order order = new Order();
           order.setModel("Acer Extensa EX2519-C08K");
           order.setTypeOfWork("diagnostic");
           order.setDescription("Battery of laptop is not charged");
           order.setDate("16.05.18");
           order.setContactPerson("Petr");
           order.setPhone("+7 999 888 77 66");
           order.setAddress("Nevsky prospect 17");
           orderList.add(order);

           return order;
       }
}

