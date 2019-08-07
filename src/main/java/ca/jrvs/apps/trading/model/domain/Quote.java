package ca.jrvs.apps.trading.model.domain;

    import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "ticker",
            "lastPrice",
            "bidPrice",
            "bidSize",
            "askPrice",
            "askSize",
            "id"
    })
    public class Quote extends Entity<String> {

        @JsonProperty("ticker")
        private String ticker;
        @JsonProperty("lastPrice")
        private double lastPrice;
        @JsonProperty("bidPrice")
        private double bidPrice;
        @JsonProperty("bidSize")
        private Integer bidSize;
        @JsonProperty("askPrice")
        private double askPrice;
        @JsonProperty("askSize")
        private Integer askSize;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("ticker")
        public String getTicker() {
            return ticker;
        }

        @JsonProperty("ticker")
        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        @JsonProperty("lastPrice")
        public double getLastPrice() {
            return lastPrice;
        }

        @JsonProperty("lastPrice")
        public void setLastPrice(double lastPrice) {
            this.lastPrice = lastPrice;
        }

        @JsonProperty("bidPrice")
        public double getBidPrice() {
            return bidPrice;
        }

        @JsonProperty("bidPrice")
        public void setBidPrice(double bidPrice) {
            this.bidPrice = bidPrice;
        }

        @JsonProperty("bidSize")
        public Integer getBidSize() {
            return bidSize;
        }

        @JsonProperty("bidSize")
        public void setBidSize(Integer bidSize) {
            this.bidSize = bidSize;
        }

        @JsonProperty("askPrice")
        public double getAskPrice() {
            return askPrice;
        }

        @JsonProperty("askPrice")
        public void setAskPrice(double askPrice) {
            this.askPrice = askPrice;
        }

        @JsonProperty("askSize")
        public Integer getAskSize() {
            return askSize;
        }

        @JsonProperty("askSize")
        public void setAskSize(Integer askSize) {
            this.askSize = askSize;
        }


        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }


    @Override
    public String getId() {
        return ticker;
    }

    @Override
    public void setId(Number s) {

    }

   }