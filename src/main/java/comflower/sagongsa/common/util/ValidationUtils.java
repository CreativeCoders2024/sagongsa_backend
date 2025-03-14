package comflower.sagongsa.common.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ValidationUtils {
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static boolean isBlank(String url) {
        return url == null || url.isBlank();
    }

    public static boolean isURL(String url) {
        try {
            var httpRequest = HttpRequest.newBuilder()
                    .HEAD()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(1))
                    .build();
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
