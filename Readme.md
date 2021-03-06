## YahooFinance Java

##### java program to get information of Yahoo quotes which works with authorized page.

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
public class YahooFinance {
    static final Charset UTF_8 = Charset.forName("UTF-8");

    static String forceUtf8Coding(String input) {
        return new String(input.getBytes(UTF_8), UTF_8);
    }

    public static double getPrice(final String ticker) throws Exception {

        URLConnection con = new URL("https://finance.yahoo.com/quote/"+ticker).openConnection();
        con.setDoOutput(true);
        List<String> cookies = con.getHeaderFields().get("Set-Cookie");

        BufferedReader rsv = new BufferedReader(new InputStreamReader(con.getInputStream()));
        Pattern crumbPattern = Pattern.compile(".*\"CrumbStore\":\\{\"crumb\":\"([^\"]+)\"\\}.*");
        String crumbRow = rsv.lines().filter(n -> crumbPattern.matcher(n).matches()).map(n -> {
            Matcher matcher = crumbPattern.matcher(n);
            return matcher.matches() ? matcher.group(1) : "";
        }).findAny().orElse(null);

        rsv.close();

        String crumb = StringUtil.unescapeJavaString(crumbRow);
        
        //String crumb = forceUtf8Coding(crumbRow);
        //force encoding is not working
        //crumbRow : bZ\u002FiB6.VoDr
        //crumb : bZ\u002FiB6.VoDr

        String quoteUrl = "https://query1.finance.yahoo.com/v7/finance/download/"+ticker+"?period1="+1493425217+"&period2="+1496017217+"&interval=1d&events=history&crumb="
                + crumb;

        con.connect();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(quoteUrl))
                .setHeader("Cookie", cookies.get(1).substring(0, 26)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        String flag = "2017-";
        List<String> responseArrList = Arrays.asList(responseBody.split(flag));

        List<HistoricalQuote> list = responseArrList.stream()
                .filter( line -> {
                    String[] split = line.split(",");
                    return !split[0].equals("Date") && !split[1].equals("Open") && !split[2].equals("High")
                            && !split[3].equals("Low") && !split[4].equals("Close")
                            && !split[5].equals("Adj Close") && !split[6].equals("Volume");
                } )

                .map(line -> {
                    String[] split = line.split(",");
                    LocalDate date = LocalDate.parse(flag+split[0]);
                    double open = Double.parseDouble(split[1]);
                    double high = Double.parseDouble(split[2]);
                    double low = Double.parseDouble(split[3]);
                    double close = Double.parseDouble(split[4]);
                    double adjClose = Double.parseDouble(split[5]);
                    double volume = Double.parseDouble(split[6]);
                    return new HistoricalQuote(date, open,  high, low, close, adjClose, volume);
                }).collect(Collectors.toList());

        HistoricalQuote historicalQuote = list.stream().findFirst().orElseThrow();
        System.out.println("ticker : "+ ticker+ " "+ historicalQuote.toString());
        return historicalQuote.getClose();
    }
}
```

[inspired by bradlucas](http://blog.bradlucas.com/posts/2017-06-02-new-yahoo-finance-quote-download-url/)
