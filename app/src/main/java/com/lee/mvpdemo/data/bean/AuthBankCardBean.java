package com.lee.mvpdemo.data.bean;

import java.util.List;

/**
 * Author ：le
 * Date ：2019-11-18 13:53
 * Description ：测试接口使用的
 */
public class AuthBankCardBean {
    /**
     * validated : false
     * key : 621700126000768783
     * stat : ok
     * messages : [{"errorCodes":"CARD_BIN_NOT_MATCH","name":"cardNo"}]
     */

    private boolean validated;
    private String key;
    private String stat;
    private String bank;
    private String cardType;

    public AuthBankCardBean(String key, String bank) {
        this.key = key;
        this.bank = bank;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    private List<MessagesBean> messages;

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<MessagesBean> getMessages() {
        return messages;
    }

    public void setMessages(List<MessagesBean> messages) {
        this.messages = messages;
    }

    public static class MessagesBean {
        /**
         * errorCodes : CARD_BIN_NOT_MATCH
         * name : cardNo
         */

        private String errorCodes;
        private String name;

        public String getErrorCodes() {
            return errorCodes;
        }

        public void setErrorCodes(String errorCodes) {
            this.errorCodes = errorCodes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "MessagesBean{" +
                    "errorCodes='" + errorCodes + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AuthBankCardBean{" +
                "validated=" + validated +
                ", key='" + key + '\'' +
                ", stat='" + stat + '\'' +
                ", bank='" + bank + '\'' +
                ", cardType='" + cardType + '\'' +
                ", messages=" + messages +
                '}';
    }
}
