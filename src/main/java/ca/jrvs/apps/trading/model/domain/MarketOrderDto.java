package ca.jrvs.apps.trading.model.domain;

    import java.util.HashMap;
import java.util.Map;

    import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "accountId",
            "size",
            "ticker"
    })
    public class MarketOrderDto {

        @JsonProperty("accountId")
        private Integer accountId;
        @JsonProperty("size")
        private Integer size;
        @JsonProperty("ticker")
        private String ticker;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("accountId")
        public Integer getAccountId() {
            return accountId;
        }

        @JsonProperty("accountId")
        public void setAccountId(Integer accountId) {
            this.accountId = accountId;
        }

        @JsonProperty("size")
        public Integer getSize() {
            return size;
        }

        @JsonProperty("size")
        public void setSize(Integer size) {
            this.size = size;
        }

        @JsonProperty("ticker")
        public String getTicker() {
            return ticker;
        }

        @JsonProperty("ticker")
        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

    }
