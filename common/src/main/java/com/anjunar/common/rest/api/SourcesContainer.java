package com.anjunar.common.rest.api;

import java.util.Set;

public interface SourcesContainer {

    Set<Link> getSources();

    boolean addSource(Link link);

}
