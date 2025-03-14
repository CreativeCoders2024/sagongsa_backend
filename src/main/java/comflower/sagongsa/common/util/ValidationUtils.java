package comflower.sagongsa.common.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ValidationUtils {
    private static final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

    public static boolean isBlank(String url) {
        return url == null || url.isBlank();
    }

    public static boolean isURL(String url) {
        try {
            var httpRequest = HttpRequest.newBuilder(URI.create(url)).build();
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.discarding());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isImageURL(String url) {
        try {
            var httpRequest = HttpRequest.newBuilder(URI.create(url)).HEAD().build();
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.discarding());
            var contentType = response.headers().firstValue("Content-Type").orElse("");
            return response.statusCode() == 200 && contentType.startsWith("image/");
        } catch (Exception e) {
            return false;
        }
    }
}
