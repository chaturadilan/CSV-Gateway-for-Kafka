package com.klaver.kafkaproducer;

import java.util.Map;

class KafkaMessage {

    private Source source;
    private Map<String, String> content;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Map<String, String> getContent() {
        return content;
    }

    public void setContent(Map<String, String> content) {
        this.content = content;
    }
}
