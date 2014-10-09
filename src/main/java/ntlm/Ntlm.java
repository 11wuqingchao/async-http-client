package ntlm;

import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Slf4JLoggerFactory;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Realm;
import com.ning.http.client.Realm.AuthScheme;
import com.ning.http.client.Response;
import com.ning.http.client.providers.jdk.JDKAsyncHttpProvider;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Ntlm {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {

        InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
        
        Realm realm = new Realm.RealmBuilder()//
                .setPrincipal("corpsignon")//
                .setPassword("Tester$1234")//
                .setUsePreemptiveAuth(true)//
                .setNtlmDomain("signontester.corp.org")
                .setNtlmHost("ntlmtester")//
                .setScheme(AuthScheme.NTLM).build();

        AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder().setRealm(realm);

        AsyncHttpClient client = new AsyncHttpClient(builder.build());

        try {
            Response response = client.prepareGet("http://localhost:8080").execute().get();

            System.out.println(response.getStatusCode());
        } finally {
            client.close();
        }
    }
}
