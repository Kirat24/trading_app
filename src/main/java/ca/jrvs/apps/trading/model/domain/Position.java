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
            "accountId",
            "position",
            "ticker"
    })
    public class Position {

        @JsonProperty("accountId")
        private String accountId;
        @JsonProperty("position")
        private String position;
        @JsonProperty("ticker")
        private String ticker;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("accountId")
        public String getAccountId() {
            return accountId;
        }

        @JsonProperty("accountId")
        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        @JsonProperty("position")
        public String getPosition() {
            return position;
        }

        @JsonProperty("position")
        public void setPosition(String position) {
            this.position = position;
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
