package com.anjunar.blomst.social.chat.messages;

import com.anjunar.common.rest.search.AbstractRestSearch;
import com.anjunar.common.rest.search.RestPredicate;
import com.anjunar.common.rest.search.RestSort;
import com.anjunar.common.rest.search.provider.GenericSortProvider;
import jakarta.ws.rs.QueryParam;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ChatMessagesSearch extends AbstractRestSearch {

    @QueryParam("sort")
    @RestSort(GenericSortProvider.class)
    private List<String> sort;

    @QueryParam("participants")
    @RestPredicate(ParticipantsProvider.class)
    private Set<UUID> participants;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public Set<UUID> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<UUID> participants) {
        this.participants = participants;
    }
}
