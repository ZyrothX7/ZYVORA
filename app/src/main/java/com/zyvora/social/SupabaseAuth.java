
package com.zyvora.social;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SupabaseAuth {

    private static final ExecutorService executor =
        Executors.newSingleThreadExecutor();

    private static final Handler mainHandler =
        new Handler(Looper.getMainLooper());

    private SupabaseAuth() {}

    public interface Callback {
        void onSuccess(Session session);
        void onFailure(String message);
    }

    public static final class Session {
        public final String accessToken;
        public final String refreshToken;
        public final String userId;

        private Session(
            String accessToken,
            String refreshToken,
            String userId
        ) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.userId = userId;
        }
    }

    public static void signUp(
        String fullName,
        String username,
        String email,
        String password,
        Callback callback
    ) {
        executor.execute(() -> {
            HttpURLConnection connection = null;

            try {
                String baseUrl = BuildConfig.SUPABASE_URL.trim();
                String apiKey = BuildConfig.SUPABASE_KEY.trim();

                if (baseUrl.isEmpty() || apiKey.isEmpty()) {
                    throw new Exception(
                        "Supabase configuration is missing."
                    );
                }

                baseUrl = baseUrl.replaceAll("/+$", "");

                URL url = new URL(
                    baseUrl + "/auth/v1/signup"
                );

                JSONObject metadata = new JSONObject();
                metadata.put("full_name", fullName);
                metadata.put("username", username);

                JSONObject body = new JSONObject();
                body.put("email", email);
                body.put("password", password);
                body.put("data", metadata);

                connection = (HttpURLConnection)
                    url.openConnection();

                connection.setRequestMethod("POST");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(20000);
                connection.setDoOutput(true);

                connection.setRequestProperty(
                    "apikey", apiKey
                );

                connection.setRequestProperty(
                    "Content-Type", "application/json"
                );

                connection.setRequestProperty(
                    "Accept", "application/json"
                );

                byte[] request = body.toString().getBytes(
                    StandardCharsets.UTF_8
                );

                try (OutputStream output =
                    connection.getOutputStream()) {
                    output.write(request);
                }

                int status = connection.getResponseCode();

                InputStream stream = status >= 200 && status < 300
                    ? connection.getInputStream()
                    : connection.getErrorStream();

                String response = readStream(stream);

                JSONObject result = response.isEmpty()
                    ? new JSONObject()
                    : new JSONObject(response);

                if (status < 200 || status >= 300) {
                    throw new Exception(
                        getErrorMessage(result, status)
                    );
                }

                String accessToken =
                    result.optString("access_token", "");

                String refreshToken =
                    result.optString("refresh_token", "");

                JSONObject user = result.optJSONObject("user");

                if (user == null) {
                    user = result;
                }

                String userId = user.optString("id", "");

                if (accessToken.isEmpty() ||
                    refreshToken.isEmpty()) {
                    throw new Exception(
                        "Account created, but no active session " +
                        "was returned. Check Supabase email " +
                        "confirmation settings."
                    );
                }

                Session session = new Session(
                    accessToken,
                    refreshToken,
                    userId
                );

                mainHandler.post(
                    () -> callback.onSuccess(session)
                );

            } catch (Exception e) {
                String message = e.getMessage();

                if (message == null || message.isEmpty()) {
                    message = "Registration failed. Please try again.";
                }

                final String error = message;

                mainHandler.post(
                    () -> callback.onFailure(error)
                );

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }

    private static String readStream(InputStream stream)
        throws Exception {

        if (stream == null) {
            return "";
        }

        try (InputStream input = stream;
             ByteArrayOutputStream output =
                 new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int length;

            while ((length = input.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }

            return output.toString("UTF-8");
        }
    }

    private static String getErrorMessage(
        JSONObject result,
        int status
    ) {
        String[] fields = {
            "msg",
            "message",
            "error_description",
            "error"
        };

        for (String field : fields) {
            String value = result.optString(field, "");

            if (!value.isEmpty() &&
                !"null".equals(value)) {
                return value;
            }
        }

        return "Registration failed (HTTP " + status + ").";
    }
}

