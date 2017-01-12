package com.example.jaison.hasura_todo;

/**
 * Created by jaison on 12/01/17.
 */
import android.util.Log;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HasuraTokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        Log.d("{{HASURA INTERCEPTOR", request.headers().toString());
        String session = Hasura.getCurrentSessionId();
        String role = Hasura.getCurrentRole();

        if (session == null) {
            response = chain.proceed(request);
        } else {
            Request newRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + session)
                    .addHeader("X-Hasura-Role", role)
                    .build();
            Log.d("{{HASURA INTERCEPTOR", newRequest.headers().toString());
            response = chain.proceed(newRequest);
        }

        return response;
    }
}
