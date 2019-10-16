package com.serzh.play;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class HttpClassPlay {

//    @Scheduled
    public void check() throws IOException, InterruptedException {
        HttpClient client0 = HttpClient.newHttpClient();
        HttpRequest request0= HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/v1/items?page=0&size=3&keyword=Italian/"))
                .build();
        client0.sendAsync(request0, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(log::info)
                .join();



        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8081/api/v1/items?page=0&size=3&keyword=Italian"))
                .build();

//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("httpGetRequest: " + responseBody);
        System.out.println("httpGetRequest status code: " + responseStatusCode);

       /* CompletableFuture<HttpResponse<String>> future = client.sendAsync(request,
                HttpResponse.BodyHandlers.ofString());

        CompletableFuture<Void> voidCompletableFuture = future.thenApply(response -> {
//            printResponse(response);
            log.info(response.body().toString());
            return response;
        }).thenApply(HttpResponse::body)
                .exceptionally(e -> "Error: " + e.getMessage())
                .thenAccept(System.out::println);*/
    }

//    @PostConstruct
    public void httpCall() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/v1/items?page=0&size=3&keyword=Italian/"))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();

/*
        CompletableFuture<String> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println(response.statusCode());
                    return response;
                })
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println);


        var client = HttpClient.newBuilder()
//                .followRedirects(Redirect.SAME_PROTOCOL)
//                .authenticator(Authenticator.getDefault())
                .connectTimeout(Duration.ofSeconds(30))
                .priority(1)
                .version(HttpClient.Version.HTTP_2)
                .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("httpGetRequest: " + responseBody);
        System.out.println("httpGetRequest status code: " + responseStatusCode);
*/

       /* HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://postman-echo.com/post"))
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .POST(HttpRequest.BodyProcessor.fromByteArray(sampleData))
                .build();

        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/headers"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString("the body"))
                .build();*/

        /*HttpResponse<String> response2 = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandler.asString());*/
    }


    /*public static void httpGetRequest() throws URISyntaxException, IOException, InterruptedException {
//        client = HttpClient.newHttpClient();
        // equivalent
//        client = HttpClient.newBuilder().build();
        var request1 = HttpRequest.newBuilder(URI.create("https://localhost:8443/headers"))
                .build();

        var request2 = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/headers"))
                .build();

        var client = HttpClient.newBuilder()
                .authenticator(Authenticator.getDefault())
                .connectTimeout(Duration.ofSeconds(30))
                .cookieHandler(CookieHandler.getDefault())
                .executor(Executors.newFixedThreadPool(2))
                .followRedirects(Redirect.NEVER)
                .priority(1) //HTTP/2 priority
                .proxy(ProxySelector.getDefault())
                .sslContext(SSLContext.getDefault())
                .version(Version.HTTP_2)
                .sslParameters(new SSLParameters())
                .build();

        *//* create with default settings *//**//*
        var defaultClient = HttpClient.newHttpClient();

        *//**//* create a custom client *//**//*
        var customClient = HttpClient.newBuilder()
                .followRedirects(Redirect.SAME_PROTOCOL)
                .authenticator(Authenticator.getDefault())
                .connectTimeout(Duration.ofSeconds(30))
                .priority(1)
                .version(HttpClient.Version.HTTP_2)
                .build();


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create("http://jsonplaceholder.typicode.com/posts/1"))
                .headers("Accept-Enconding", "gzip, deflate")
                .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("httpGetRequest: " + responseBody);
        System.out.println("httpGetRequest status code: " + responseStatusCode);
    }*/
}
