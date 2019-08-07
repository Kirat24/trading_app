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
            "id",
            "notes",
            "price",
            "size",
            "status",
            "ticker"
    })
    public  abstract class SecurityOrder extends Entity {


        @JsonProperty("accountId")
        private Integer accountId;
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("notes")
        private String notes;
        @JsonProperty("price")
        private Integer price;
        @JsonProperty("size")
        private Integer size;
        @JsonProperty("ticker")
        private String ticker;
        @JsonProperty("orderStatus")
        private OrderStatus orderStatus;

        @JsonProperty("accountId")
        public Integer getAccountId() {
            return accountId;
        }

        @JsonProperty("accountId")
        public void setAccountId(Integer accountId) {
            this.accountId = accountId;
        }

        @JsonProperty("id")
        public Integer getId() {
            return id;
        }

        @JsonProperty("id")
        public void setId(Integer id) {
            this.id = id;
        }
        @JsonProperty("orderStatus")
        public OrderStatus getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
        }


        @JsonProperty("notes")
        public String getNotes() {
            return notes;
        }

        @JsonProperty("notes")
        public void setNotes(String notes) {
            this.notes = notes;
        }

        @JsonProperty("price")
        public Integer getPrice() {
            return price;
        }

        @JsonProperty("price")
        public void setPrice(Integer price) {
            this.price = price;
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
