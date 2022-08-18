import com.azure.identity.DeviceCodeCredential;
import com.azure.identity.DeviceCodeCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.httpcore.HttpClients;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class DeviceCodeFlowMain {

    //Replace CLIENT_ID with your own client id from an app that is configured according to the requirements below
    //for requirements visit:
    //https://github.com/Azure/azure-sdk-for-java/wiki/Set-up-Your-Environment-for-Authentication#enable-applications-for-device-code-flow
    private final static String CLIENT_ID = "<your client/app id>";

    //Set the scopes for your ms-graph request
    private final static List<String> SCOPES = Arrays.asList("User.ReadBasic.All", "User.Read");

    public static void main(String[] args) throws Exception {
        final DeviceCodeCredential deviceCodeCred = new DeviceCodeCredentialBuilder()
                .clientId(CLIENT_ID)
                .challengeConsumer(challenge -> System.out.println(challenge.getMessage()))
                .build();

        final TokenCredentialAuthProvider tokenCredAuthProvider = new TokenCredentialAuthProvider(SCOPES, deviceCodeCred);
        final OkHttpClient httpClient = HttpClients.createDefault(tokenCredAuthProvider);

        final Request request = new Request.Builder().url("https://graph.microsoft.com/v1.0/me/").build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}
