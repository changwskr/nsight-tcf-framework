package com.nh.nsight.tcf.core.context;

import com.nh.nsight.tcf.core.message.StandardHeader;
import java.util.HashMap;
import java.util.Map;

public class TransactionContext {
    private final StandardHeader header;
    private final long startTimeMillis;
    private final Map<String, Object> attributes = new HashMap<>();

    public TransactionContext(StandardHeader header) {
        this.header = header;
        this.startTimeMillis = System.currentTimeMillis();
    }

    public StandardHeader getHeader() { return header; }
    public long getStartTimeMillis() { return startTimeMillis; }
    public long elapsedMillis() { return System.currentTimeMillis() - startTimeMillis; }
    public Map<String, Object> getAttributes() { return attributes; }
    public void put(String key, Object value) { attributes.put(key, value); }
    public Object get(String key) { return attributes.get(key); }
}
