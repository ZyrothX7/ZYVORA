
package com.zyvora.social;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public final class SessionManager {

    private static final String PREFS = "zyvora_secure_session";
    private static final String ALIAS = "ZYVORA_SESSION_KEY";

    private static final String ENCRYPTED_DATA = "encrypted_session";
    private static final String IV_DATA = "session_iv";

    private SessionManager() {}

    public static final class StoredSession {
        public final String accessToken;
        public final String refreshToken;
        public final String userId;

        private StoredSession(
            String accessToken,
            String refreshToken,
            String userId
        ) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.userId = userId;
        }
    }

    private static SecretKey getOrCreateKey() throws Exception {

        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        if (keyStore.containsAlias(ALIAS)) {
            return (SecretKey) keyStore.getKey(ALIAS, null);
        }

        KeyGenerator generator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        );

        generator.init(
            new KeyGenParameterSpec.Builder(
                ALIAS,
                KeyProperties.PURPOSE_ENCRYPT |
                KeyProperties.PURPOSE_DECRYPT
            )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(
                KeyProperties.ENCRYPTION_PADDING_NONE
            )
            .setKeySize(256)
            .setRandomizedEncryptionRequired(true)
            .build()
        );

        return generator.generateKey();
    }

    public static boolean save(
        Context context,
        SupabaseAuth.Session session
    ) {
        try {
            JSONObject json = new JSONObject();
            json.put("access_token", session.accessToken);
            json.put("refresh_token", session.refreshToken);
            json.put("user_id", session.userId);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            cipher.init(
                Cipher.ENCRYPT_MODE,
                getOrCreateKey()
            );

            byte[] encrypted = cipher.doFinal(
                json.toString().getBytes(StandardCharsets.UTF_8)
            );

            String encryptedText = Base64.encodeToString(
                encrypted,
                Base64.NO_WRAP
            );

            String ivText = Base64.encodeToString(
                cipher.getIV(),
                Base64.NO_WRAP
            );

            return context.getApplicationContext()
                .getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(ENCRYPTED_DATA, encryptedText)
                .putString(IV_DATA, ivText)
                .commit();

        } catch (Exception e) {
            clear(context);
            return false;
        }
    }

    public static StoredSession load(Context context) {

        try {
            SharedPreferences prefs =
                context.getApplicationContext()
                    .getSharedPreferences(
                        PREFS,
                        Context.MODE_PRIVATE
                    );

            String encryptedText =
                prefs.getString(ENCRYPTED_DATA, null);

            String ivText =
                prefs.getString(IV_DATA, null);

            if (encryptedText == null || ivText == null) {
                return null;
            }

            byte[] encrypted = Base64.decode(
                encryptedText,
                Base64.NO_WRAP
            );

            byte[] iv = Base64.decode(
                ivText,
                Base64.NO_WRAP
            );

            Cipher cipher = Cipher.getInstance(
                "AES/GCM/NoPadding"
            );

            cipher.init(
                Cipher.DECRYPT_MODE,
                getOrCreateKey(),
                new GCMParameterSpec(128, iv)
            );

            byte[] decrypted = cipher.doFinal(encrypted);

            JSONObject json = new JSONObject(
                new String(decrypted, StandardCharsets.UTF_8)
            );

            String accessToken =
                json.getString("access_token");

            String refreshToken =
                json.getString("refresh_token");

            String userId =
                json.optString("user_id", "");

            return new StoredSession(
                accessToken,
                refreshToken,
                userId
            );

        } catch (Exception e) {
            clear(context);
            return null;
        }
    }

    public static void clear(Context context) {

        try {
            context.getApplicationContext()
                .getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();

            KeyStore keyStore = KeyStore.getInstance(
                "AndroidKeyStore"
            );

            keyStore.load(null);

            if (keyStore.containsAlias(ALIAS)) {
                keyStore.deleteEntry(ALIAS);
            }

        } catch (Exception ignored) {
            // Session cleanup is best-effort.
        }
    }
}
