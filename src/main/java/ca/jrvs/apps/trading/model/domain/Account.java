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
            "id",
            "traderId",
            "amount"
    })
    public class Account extends Entity {

        @JsonProperty("id")
        private Integer id;
        @JsonProperty("traderId")
        private Integer traderId;
        @JsonProperty("amount")
        private Integer amount;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("id")
        public Integer getId() {
            return id;
        }

        @Override
        public void setId(Number s) {

        }

        @JsonProperty("id")
        public void setId(Integer id) {
            this.id = id;
        }

        @JsonProperty("traderId")
        public Integer getTraderId() {
            return traderId;
        }

        @JsonProperty("traderId")
        public void setTraderId(Integer traderId) {
            this.traderId = traderId;
        }

        @JsonProperty("amount")
        public Integer getAmount() {
            return amount;
        }

        @JsonProperty("amount")
        public void setAmount(Integer amount) {
            this.amount = amount;
        }


    }
