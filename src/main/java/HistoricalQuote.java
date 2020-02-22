import java.time.LocalDate;

public class HistoricalQuote {

    //private String symbol;
    private LocalDate date;
    private double open;
    private double low;
    private double high;
    private double close;
    private double adjClose;
    private double volume;

    public HistoricalQuote(LocalDate date, double open, double low, double high, double close, double adjClose,
                           double volume) {
        super();
        this.date = date;
        this.open = open;
        this.low = low;
        this.high = high;
        this.close = close;
        this.adjClose = adjClose;
        this.volume = volume;
    }


    public LocalDate getDate() {
        return date;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }


    public double getOpen() {
        return open;
    }


    public void setOpen(double open) {
        this.open = open;
    }


    public double getLow() {
        return low;
    }


    public void setLow(double low) {
        this.low = low;
    }


    public double getHigh() {
        return high;
    }


    public void setHigh(double high) {
        this.high = high;
    }


    public double getClose() {
        return close;
    }


    public void setClose(double close) {
        this.close = close;
    }


    public double getAdjClose() {
        return adjClose;
    }


    public void setAdjClose(double adjClose) {
        this.adjClose = adjClose;
    }


    public double getVolume() {
        return volume;
    }


    public void setVolume(double volume) {
        this.volume = volume;
    }


    @Override
    public String toString() {
        return "HistoricalQuote [date=" + date + ", open=" + open + ", low=" + low + ", high=" + high + ", close="
                + close + ", adjClose=" + adjClose + ", volume=" + volume + "]";
    }

}
