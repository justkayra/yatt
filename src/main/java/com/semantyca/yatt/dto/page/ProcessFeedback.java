package com.semantyca.yatt.dto.page;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.semantyca.yatt.dto.OutcomeType;
import com.semantyca.yatt.dto.PageOutcome;
import com.semantyca.yatt.dto.constant.MessageLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder({"identifier", "type", "title", "pageName", "payloads"})
public class ProcessFeedback extends PageOutcome {
    List<FeedbackEntry> entries = new ArrayList<>();

    public void addEntry(FeedbackEntry entry){
        entries.add(entry);
    }

    public void addEntry(String id, MessageLevel level, String descr){
        FeedbackEntry entry = new FeedbackEntry();
        entry.setId(id);
        entry.setLevel(level);
        entry.setDescription(descr);
        entries.add(entry);
    }

    @JsonGetter("title")
    public String getTitle() {
        return "server processing feedback";
    }

    @JsonGetter("type")
    public OutcomeType getType() {
        return OutcomeType.INFO;
    }

    public Map<String, List> getPayloads() {
        payloads.put("entries", entries);
        return payloads;
    }
}
